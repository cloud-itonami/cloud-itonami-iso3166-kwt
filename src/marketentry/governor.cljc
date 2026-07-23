(ns marketentry.governor
  "Market-Entry Compliance Governor -- the independent compliance layer
  that earns the MarketEntry-LLM the right to commit. The LLM has no
  notion of jurisdictional procurement law, whether a Kuwait-resident
  commercial registration is actually on file, whether a claimed
  engagement fee actually equals base + months x rate, whether a
  foreign contractor's declared local-content commitment actually
  clears Kuwait's statutory floor, or when a draft stops being a draft
  and becomes a real-world portal submission, so this MUST be a
  separate system able to *reject* a proposal and fall back to HOLD.

  `:itonami.blueprint/governor` is `:market-entry-compliance-governor`
  (shared family keyword on blueprints; this is the KWT running
  implementation of that governor).

  This blueprint's own text (docs/business-model.md Trust Controls:
  'any actual portal registration or filing submission requires
  Market-Entry Compliance Governor clearance and always escalates to
  human sign-off'; 'a false or fabricated regulatory-requirement claim
  is a HARD hold') names exactly the checks below.

  Seven checks, in priority order, ALL HARD violations: a human
  approver CANNOT override them. The confidence/actuation gate is
  SOFT: it asks a human to look (low confidence / actuation), and the
  human may approve -- but see `marketentry.phase`: for `:stake
  :actuation/draft-filing`/`:actuation/submit-filing` NO phase ever
  allows auto-commit either. Two independent layers agree that
  actuation is always a human call.

    1. Spec-basis                  -- did the jurisdiction proposal cite
                                       an OFFICIAL source
                                       (`marketentry.facts`), or invent
                                       one?
    2. Evidence incomplete         -- for `:filing/draft`/
                                       `:filing/submit`, has the
                                       jurisdiction actually been
                                       assessed with a full evidence
                                       checklist on file?
    3. Kuwait-resident-entity      -- for `:filing/submit`, when the
       missing                        engagement declares
                                       `:requires-kw-entity? true`
                                       (Article 31 of the General
                                       Tenders Law, Law No. 49/2016,
                                       requires a bidder to by default
                                       be Kuwaiti, Commercial-Register
                                       -registered, AND registered in
                                       CAPT's own supplier/contractor
                                       register -- see
                                       `marketentry.facts`),
                                       INDEPENDENTLY verify
                                       `:has-kw-entity?` is true.
    4. Local-content commitment    -- for `:filing/submit`, when the
       shortfall (FLAGSHIP)           engagement declares
                                       `:requires-local-content-
                                       commitment? true` (Article 87
                                       of the General Tenders Law --
                                       'أفضلية الصناعة الوطنية والمقاول
                                       المحلي', the National-Industry
                                       and Local-Contractor Preference
                                       that applies to a FOREIGN
                                       contractor awarded a Kuwaiti
                                       public tender), INDEPENDENTLY
                                       verify BOTH
                                       `:national-industry-purchase-
                                       percent` >= 30.0 AND
                                       `:local-subcontract-percent` >=
                                       30.0 -- Article 87's own text
                                       states EACH commitment
                                       separately as 'ما لا يقل عن 30%'
                                       (not less than 30%). FLAGSHIP
                                       genuinely new check for the
                                       iso3166 family (grep-verified
                                       absent as a governor check
                                       function/rule name fleet-wide at
                                       build time -- every sibling
                                       studied, UAE/Saudi/Oman, only
                                       ever checks a single BOOLEAN
                                       local-entity/registration
                                       presence flag or a single
                                       recomputed NUMBER; this is the
                                       first check in the family that
                                       independently re-verifies an
                                       ONGOING PERFORMANCE QUOTA against
                                       TWO separate statutory floors
                                       drawn from the same article).
                                       CONDITIONAL on the engagement's
                                       own `:requires-local-content-
                                       commitment?` ground truth --
                                       Article 87 only applies to
                                       FOREIGN contractors, so a
                                       Kuwaiti-path engagement (checked
                                       #3 above) is exempt from #4, and
                                       vice versa.
    5. Engagement fee mismatch     -- for `:filing/submit`,
                                       INDEPENDENTLY recompute whether
                                       the engagement's own `:claimed-
                                       fee` equals `base-fee +
                                       monthly-rate x monitoring-
                                       months` -- honest reapplication
                                       of the ground-truth-recompute
                                       discipline sibling actors use.
    6. Confidence floor / actuation
       gate                          -- LLM confidence below threshold,
                                       OR the op is `:filing/draft`/
                                       `:filing/submit` (REAL acts)
                                       -> escalate.

  Two more guards, double-draft/double-submit prevention, are enforced
  off dedicated `:drafted?`/`:submitted?` facts (never a `:status`
  value)."
  (:require [marketentry.facts :as facts]
            [marketentry.registry :as registry]
            [marketentry.store :as store]))

(def confidence-floor 0.6)

(def local-content-floor-percent
  "Article 87's own stated baseline -- 'ما لا يقل عن 30%' (not less
  than 30%) for EACH of the two commitments (national-industry
  purchase, local-contractor subcontract). The article notes this
  figure may be adjusted by a Council of Ministers decision on CAPT's
  own proposal; no such adjustment decision was independently fetched
  this session, so this constant is the law's own baseline, not a
  possibly-superseded number."
  30.0)

(def high-stakes
  "Stakes grave enough to always require a human, even when clean.
  Drafting a real portal package and submitting a real portal
  registration are the two real-world actuation events this actor
  performs."
  #{:actuation/draft-filing :actuation/submit-filing})

;; ----------------------------- checks -----------------------------

(defn- spec-basis-violations
  "A `:jurisdiction/assess` (or `:filing/draft`/`:filing/submit`)
  proposal with no spec-basis citation is a HARD violation -- never
  invent a jurisdiction's market-entry requirements."
  [{:keys [op]} proposal]
  (when (contains? #{:jurisdiction/assess :filing/draft :filing/submit} op)
    (let [value (:value proposal)]
      (when (or (empty? (:cites proposal))
                (and (contains? value :spec-basis) (nil? (:spec-basis value))))
        [{:rule :no-spec-basis
          :detail "公式spec-basisの引用が無い提案は法域要件として扱えない"}]))))

(defn- evidence-incomplete-violations
  "For `:filing/draft`/`:filing/submit`, the jurisdiction's required
  registration evidence must actually be satisfied."
  [{:keys [op subject]} st]
  (when (contains? #{:filing/draft :filing/submit} op)
    (let [e (store/engagement st subject)
          assessment (store/assessment-of st subject)]
      (when-not (and assessment
                     (facts/required-evidence-satisfied?
                      (:jurisdiction e) (:checklist assessment)))
        [{:rule :evidence-incomplete
          :detail "法域の必要書類(CR/CAPT登録/分類証明/代理人確認等)が充足していない状態での提案"}]))))

(defn- kw-entity-missing-violations
  "For `:filing/submit`, when the engagement declares
  `:requires-kw-entity? true`, INDEPENDENTLY verify
  `:has-kw-entity?` is true -- grounded in Article 31 of the General
  Tenders Law (Law No. 49/2016): a bidder must by default be Kuwaiti,
  Commercial-Register-registered (MOCI/KBC), AND registered in CAPT's
  own supplier/contractor register. CONDITIONAL on the engagement's
  own `:requires-kw-entity?` ground truth (a foreign-bidder engagement
  under Article 31's own exception is instead gated by check #4,
  `local-content-shortfall-violations`)."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (and (true? (:requires-kw-entity? e))
                 (not (true? (:has-kw-entity? e))))
        [{:rule :kw-entity-missing
          :detail (str subject " はクウェートの商業登記(CR/MOCI-KBC)およびCAPT登録を要するが未確認 -- 提出提案は進められない")}]))))

(defn- local-content-shortfall-violations
  "FLAGSHIP genuinely new check for the iso3166 family. For
  `:filing/submit`, when the engagement declares
  `:requires-local-content-commitment? true` (Article 87 of the
  General Tenders Law applies -- a FOREIGN contractor awarded a
  Kuwaiti public tender), INDEPENDENTLY verify BOTH
  `:national-industry-purchase-percent` and `:local-subcontract-
  percent` clear `local-content-floor-percent` (30.0, Article 87's own
  stated baseline for EACH commitment separately). Unlike every other
  check in this governor (and every sibling jurisdiction's flagship
  check), this re-verifies an ONGOING PERFORMANCE QUOTA against TWO
  independent numeric floors, not a single boolean presence flag or a
  single recomputed total. CONDITIONAL on the engagement's own
  `:requires-local-content-commitment?` ground truth."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when (true? (:requires-local-content-commitment? e))
        (let [nip (:national-industry-purchase-percent e)
              lsp (:local-subcontract-percent e)
              nip-short? (or (nil? nip) (< nip local-content-floor-percent))
              lsp-short? (or (nil? lsp) (< lsp local-content-floor-percent))]
          (when (or nip-short? lsp-short?)
            [{:rule :local-content-shortfall
              :detail (str subject " の現地調達コミットメント(国内産業調達=" nip
                          "%, 現地下請=" lsp "%)が法定下限"
                          local-content-floor-percent "%(第87条)を下回る -- 提出提案は進められない")}]))))))

(defn- engagement-fee-mismatch-violations
  "For `:filing/submit`, INDEPENDENTLY recompute whether the
  engagement's own claimed fee equals base + months x rate."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (let [e (store/engagement st subject)]
      (when-not (registry/engagement-fee-matches-claim? e)
        [{:rule :engagement-fee-mismatch
          :detail (str subject " の申告手数料(" (:claimed-fee e)
                      ")が独立再計算値(" (registry/compute-engagement-fee e) ")と一致しない")}]))))

(defn- already-drafted-violations
  "For `:filing/draft`, refuses to draft the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/draft)
    (when (store/engagement-already-drafted? st subject)
      [{:rule :already-drafted
        :detail (str subject " は既にドラフト済み")}])))

(defn- already-submitted-violations
  "For `:filing/submit`, refuses to submit the SAME engagement twice."
  [{:keys [op subject]} st]
  (when (= op :filing/submit)
    (when (store/engagement-already-submitted? st subject)
      [{:rule :already-submitted
        :detail (str subject " は既に提出済み")}])))

(defn check
  "Censors a MarketEntry-LLM proposal against the governor rules.
  Returns {:ok? bool :violations [..] :confidence c :escalate? bool
  :high-stakes? bool :hard? bool}."
  [request _context proposal st]
  (let [hard (into []
                   (concat (spec-basis-violations request proposal)
                           (evidence-incomplete-violations request st)
                           (kw-entity-missing-violations request st)
                           (local-content-shortfall-violations request st)
                           (engagement-fee-mismatch-violations request st)
                           (already-drafted-violations request st)
                           (already-submitted-violations request st)))
        conf (:confidence proposal 0.0)
        low? (< conf confidence-floor)
        stakes? (boolean (high-stakes (:stake proposal)))
        hard? (boolean (seq hard))]
    {:ok?          (and (not hard?) (not low?) (not stakes?))
     :violations   hard
     :confidence   conf
     :hard?        hard?
     :escalate?    (and (not hard?) (or low? stakes?))
     :high-stakes? stakes?}))

(defn hold-fact
  "The audit fact written when a proposal is rejected (HOLD)."
  [request context verdict]
  {:t          :governor-hold
   :op         (:op request)
   :actor      (:actor-id context)
   :subject    (:subject request)
   :disposition :hold
   :basis      (mapv :rule (:violations verdict))
   :violations (:violations verdict)
   :confidence (:confidence verdict)})
