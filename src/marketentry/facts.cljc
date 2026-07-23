(ns marketentry.facts
  "Kuwait market-entry catalog.

  Owner authority, e-tendering/registration system and legal basis
  confirmed this session by directly fetching and reading:

    - https://www.capt.gov.kw/ (Central Agency for Public Tenders /
      الجهاز المركزي للمناقصات العامة, 'CAPT') -- the LIVE site returns a
      genuine Cloudflare bot-detection challenge (HTML <title>Just a
      moment...</title>, CSP referencing challenges.cloudflare.com).
      Per this workspace's hard rule, that challenge was NOT bypassed;
      the Internet Archive Wayback Machine was used instead:
      http://web.archive.org/web/20260327051917/https://www.capt.gov.kw/ar/
      (a 2026-03-27 snapshot -- recent, and reachable because Wayback
      re-serves the archived bytes rather than replaying CAPT's live
      Cloudflare challenge). That snapshot's own nav confirms CAPT's
      registration flow (تسجيل المناقصين -> تسجيل مقاولين/موردين/مقدم
      خدمة), a formal contractor/supplier classification system
      (التصنيف -> الشركات المصنفة / نماذج التصنيف / طلبات التصنيف
      الالكترونية), separate 'local company' vs 'foreign company'
      registration-inquiry pages (local-company-inquiry/,
      foreign-company-inquiry/), and a laws page (القوانين واللوائح ->
      قانون المناقصات) linking to CAPT's own PDF of the law.
    - That PDF -- fetched via the same Wayback snapshot family
      (http://web.archive.org/web/20230919204034/https://capt.gov.kw/media/filer_public/fa/5f/fa5fe27c-a27a-43ef-b92d-bbce8c1fe83d/law-ar.pdf)
      and run through `pdftotext` -- is the Kuwait Al-Yawm official
      gazette, Issue No. 1299 (26 Shawwal 1437H / 31 July 2016),
      publishing 'Law No. 49 of 2016 regarding General Tenders'
      (قانون رقم 49 لسنة 2016 بشأن المناقصات العامة). This is CAPT's
      own founding/governing statute -- see `statute.facts` for the
      full citation and Article 31 / Article 87 text this catalog and
      `marketentry.governor` are grounded in.
    - https://www.moci.gov.kw/en/ (Ministry of Commerce and Industry,
      MOCI) -- LIVE, fetched directly (HTTP 200, no block). Confirmed
      the Kuwait Business Center (KBC) at https://kbc.moci.gov.kw/
      (LIVE, HTTP 200; e-service login at
      https://e-kbc.moci.gov.kw/e-kbc/WebPages/login.aspx) as the
      company-formation / Commercial Registration one-stop-shop
      ('Establishing companies - Kuwait Business Center, Ministry of
      Commerce and Industry'), and the electronic commercial-registry
      service at https://ereg.moci.gov.kw/ereg/WebPages/login.aspx
      ('Electronic services for managing the commercial registry') --
      that login page itself returned a CloudFront 'Request blocked'
      when fetched directly, so only its name/URL (sourced from
      MOCI's own successfully-fetched page) is cited here, not any
      content behind its login.

  `capt.gov.kw` (bare, no `www.`) DOES resolve and shares the same
  Cloudflare challenge as `www.capt.gov.kw` -- both are cited via the
  Wayback fallback above, never bypassed live."
  )

(def catalog
  {"KWT" {:name "State of Kuwait"
          :owner-authority "Central Agency for Public Tenders (CAPT) -- الجهاز المركزي للمناقصات العامة"
          :legal-basis "General Tenders Law, Law No. 49 of 2016, as amended by Law No. 74 of 2019"
          :national-spec "CAPT's own registration + classification + e-tendering system (capt.gov.kw) -- no separately-branded portal name found (unlike e.g. Oman's 'Esnad'); CAPT's own site is the portal"
          :provenance "https://www.capt.gov.kw/ar/laws/"
          :required-evidence ["Commercial Registration (CR) record (MOCI/KBC)" "CAPT tenderer/contractor/supplier/service-provider registration" "CAPT classification (تصنيف) certificate" "Authorized-representative record"]
          :rep-owner-authority "Ministry of Commerce and Industry (MOCI) / Kuwait Business Center (KBC)"
          :rep-legal-basis "Commercial Registration via MOCI/KBC is the Article 31 precondition for CAPT tenderer/contractor/supplier registration"
          :rep-provenance "https://kbc.moci.gov.kw/"
          :corporate-number-owner-authority "MOCI"
          :corporate-number-legal-basis "Commercial Registration (CR) number, administered through MOCI's electronic commercial-registry service"
          :corporate-number-provenance "https://ereg.moci.gov.kw/ereg/WebPages/login.aspx"}
   ;; Carried-over R0 seed entries, identical across every cloud-itonami-iso3166-*
   ;; sibling (not Kuwait-specific research) -- kept for coverage/cross-jurisdiction tests.
   "USA" {:name "United States" :owner-authority "GSA/SAM.gov" :legal-basis "FAR" :national-spec "SAM.gov" :provenance "https://sam.gov/"
          :required-evidence ["EIN record" "SAM.gov registration record" "State business registration record" "SAM UEI verification record"]}
   "ARE" {:name "UAE" :owner-authority "Federal e-procurement" :legal-basis "Federal procurement" :national-spec "e-procurement" :provenance "https://www.mof.gov.ae/"
          :required-evidence ["Trade license" "e-procurement registration" "License extract" "Authorized-representative record"]}
   "SAU" {:name "Saudi Arabia" :owner-authority "Etimad" :legal-basis "GTPL" :national-spec "Etimad" :provenance "https://tenders.etimad.sa/"
          :required-evidence ["CR number" "Etimad registration" "CR extract" "Authorized-representative record"]}
   "QAT" {:name "Qatar" :owner-authority "Ministry of Finance / Government Procurement Portal" :legal-basis "Tenders and Auctions Law" :national-spec "e-procurement + commercial registration" :provenance "https://www.mof.gov.qa/"
          :required-evidence ["CR number record" "e-procurement registration record" "CR extract" "Authorized-representative record"]}})

(defn spec-basis [iso3] (get catalog iso3))
(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s) missing (remove catalog iso3s)]
     {:requested (count iso3s) :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note "R0 catalog seed"})))
(defn required-evidence-satisfied? [iso3 submitted]
  (when-let [{:keys [required-evidence]} (spec-basis iso3)]
    (= (count required-evidence) (count (filter (set submitted) required-evidence)))))
(defn evidence-checklist [iso3] (:required-evidence (spec-basis iso3) []))
(defn rep-spec-basis [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:rep-owner-authority sb)
      (select-keys sb [:rep-owner-authority :rep-legal-basis :rep-provenance]))))
(defn corporate-number-spec-basis [iso3]
  (when-let [sb (spec-basis iso3)]
    (when (:corporate-number-owner-authority sb)
      (select-keys sb [:corporate-number-owner-authority :corporate-number-legal-basis :corporate-number-provenance]))))
