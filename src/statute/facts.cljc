(ns statute.facts
  "General-law compliance catalog for Kuwait (KWT) per ADR-2607141700
  (cloud-itonami-compliance-fact-federation), sibling namespace to
  `marketentry.facts` / `culture.facts`.

  Every entry below was confirmed this session against an official
  Kuwaiti government source that was directly fetched and read (live
  where reachable, Internet Archive Wayback Machine where the live
  site returned a genuine bot-detection challenge or was otherwise
  unreachable -- never bypassed):

    - General Tenders Law, Law No. 49 of 2016 (as amended by Law No.
      74 of 2019): CAPT's live site (capt.gov.kw) returns a genuine
      Cloudflare 'Just a moment...' challenge (confirmed from the
      response HTML itself: <title>Just a moment...</title>, CSP
      referencing challenges.cloudflare.com) -- NOT bypassed. Fetched
      instead via the Wayback Machine:
      http://web.archive.org/web/20260327051917/https://www.capt.gov.kw/ar/laws/
      (2026-03-27 snapshot of CAPT's own laws page), which links to
      CAPT's own PDF of the law, in turn fetched via
      http://web.archive.org/web/20230919204034/https://capt.gov.kw/media/filer_public/fa/5f/fa5fe27c-a27a-43ef-b92d-bbce8c1fe83d/law-ar.pdf
      and read with `pdftotext`. That PDF is the Kuwait Al-Yawm
      official gazette, Issue No. 1299 (26 Shawwal 1437H / 31 July
      2016), and its own text (read directly this session) contains:
        * Article 31 (المادة 31), Chapter 2 'الشروط العامة في
          المتعاقد' (General Conditions for the Contracting Party):
          '...يشترط فيمن يتقدم بعطاء ... أولاً: أن يكون كويتياً - فرداً
          أو شركة - مقيداً في السجل التجاري. ثانياً: أن يكون مسجلاً في
          سجل الموردين أو المقاولين... ويجوز أن يكون مقدم العطاء
          أجنبياً...' -- i.e. a bidder must, by default, be Kuwaiti and
          registered in the Commercial Register AND in CAPT's own
          supplier/contractor register, referencing Law No. (1) of
          2016 [Commercial Companies] and Law No. (116) of 2013 [KDIPA
          Direct Investment Promotion Law]; a FOREIGN bidder is
          explicitly permitted as a named exception.
        * Article 87 (المادة 87), Chapter 11 'أفضلية الصناعة الوطنية
          والمقاول المحلي' (National-Industry and Local-Contractor
          Preference): '...تراقب الجهة العامة التزام المقاول الأجنبي
          بشراء ما لا يقل عن 30% من الصناعات الوطنية... كما تراقب
          التزام المقاول الأجنبي بأن يسند ما لا يقل عن 30% من أعمال
          المقاولة التي ترسى عليه إلى مقاولين محليين من المسجلين في
          قوائم تصنيف المقاولين بالجهاز...' -- a FOREIGN contractor
          awarded a Kuwaiti public tender must be monitored by the
          contracting public entity for TWO independent commitments,
          each with a statutory floor of 'not less than 30%': (a)
          purchasing national-industry products/services, and (b)
          subcontracting to LOCAL, CAPT-classification-registered
          contractors. The law itself notes both percentages may be
          adjusted by a Council of Ministers decision on CAPT's own
          proposal -- no such adjustment decision was independently
          fetched this session, so the 30% floors below are the LAW'S
          OWN stated baseline, not a possibly-superseded figure. This
          is the article `marketentry.governor`'s flagship
          `local-content-shortfall` check enforces.
      A companion PDF, Law No. 74 of 2019 (amending Law 49/2016),
      fetched via
      http://web.archive.org/web/20230919204034/https://capt.gov.kw/static/pdf/law74_2019.pdf,
      is a lower-quality scan whose OCR text was too garbled to quote
      article-by-article this session -- its existence, number and
      year are independently corroborated by KDIPA's own catalog (next
      paragraph), so the amendment is cited, but no specific amended
      article text from THIS PDF is asserted as read.
    - Commercial Companies Law (Law No. 1 of 2016, as amended by Law
      No. 15 of 2017 and Law No. 79 of 2019), and the Law for the
      Promotion of Direct Investment in the State of Kuwait (Law No.
      116 of 2013, plus its Executive Regulations): KDIPA's live site
      (kdipa.gov.kw) returned HTTP 503 on repeated direct attempts
      (an Akamai edge error, not a bot-detection challenge) -- fetched
      instead via a very recent Wayback snapshot,
      http://web.archive.org/web/20260510203427/https://kdipa.gov.kw/invest-in-kuwait/economic-laws/
      and
      http://web.archive.org/web/20260510203427/https://kdipa.gov.kw/about-kdipa/law-decisions/
      (both 2026-05-10), KDIPA's OWN 'Economic Laws' and 'Law &
      Decisions' pages, which list Kuwait's economic-law catalog by
      number/year/title, including Kuwaiti Income Tax (Amiri Decree
      No. 3 of 1955, amended by Law No. 2 of 2008) and Decree-Law No.
      157 of 2024 promulgating the Tax Law on Multinational Enterprise
      Groups. Only the LAW NUMBER, YEAR and TITLE are confirmed from
      these listing pages -- exact promulgation day/month were not
      independently read for these four entries (no `pdftotext` of
      their own gazette text this session, unlike Law 49/2016 above),
      so `:statute/enacted-date` is deliberately OMITTED for them
      rather than asserting an unconfirmed day/month. The
      Decree-Law No. 157/2024 multinational-enterprise tax is
      independently corroborated by the Ministry of Finance's own
      LIVE-then-Wayback-mirrored homepage (mof.gov.kw direct fetch
      timed out; Wayback snapshot
      http://web.archive.org/web/20260523214250/https://www.mof.gov.kw/,
      2026-05-23) listing 'الضريبة التكميلية على مجموعة الكيانات
      متعددة الجنسيات' (Supplementary Tax on Multinational Enterprise
      Groups) as a current MOF service topic.
    - Private Sector Labour Law (Law No. 6 of 2010, with its amending
      laws): the Public Authority for Manpower's own site
      (manpower.gov.kw) timed out on direct connection (DNS resolves;
      not a bot-detection challenge, a plain connection timeout) --
      fetched via Wayback,
      http://web.archive.org/web/20260121001740/https://manpower.gov.kw/LaborLaw.aspx
      (2026-01-21), PAM's OWN 'Labor Law' listing page, whose row #44
      reads verbatim 'KUWAIT LABOUR LAW NO.6 OF 2010 [English Copy] :
      concerning Labour in the private sector with its amendments.'
      The same page (row #46) also lists 'The Domestic Worker Law Law
      No.68 of 2015' -- a related but separate law, not asserted as a
      `catalog` entry here since it governs a different worker
      category from the general private-sector Labour Law.

  Explicitly NOT asserted (disclosed gaps, not fabrications): a
  specific Kuwaiti corporate income-tax RATE (commonly reported
  elsewhere as a flat 15% figure applicable to foreign-owned bodies
  corporate, but NOT independently confirmed against the Decree No.
  3/1955 / Law No. 2/2008 text itself this session -- only the law's
  own number/year/title were read, via KDIPA's catalog page, not the
  decree's own article text); any Zakat or National Labor Support Tax
  law number (repeated attempts to reach a dedicated Kuwait Tax
  Authority domain failed at the DNS/connection level, and neither
  KDIPA's nor MOF's own fetched pages surfaced a citable Zakat/NLST
  law number this session); the Council-of-Ministers-adjusted current
  percentage (if any) for Article 87's two 30% floors, beyond the
  law's own stated baseline; and Law No. 116/2013's own article text
  (only KDIPA's page ABOUT the law, plus its cross-reference inside
  Article 31 of Law 49/2016, were read -- not the 2013 law's own
  promulgated text).

  An entry not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url/date.")

(def catalog
  "ISO3166 alpha-3 -> vector of statute entries."
  {"KWT"
   [{:statute/id "kwt.law-49-2016-general-tenders-law"
     :statute/title "General Tenders Law"
     :statute/jurisdiction "KWT"
     :statute/kind :law
     :statute/law-number "Law No. 49 of 2016 (as amended by Law No. 74 of 2019)"
     :statute/url "https://www.capt.gov.kw/ar/laws/"
     :statute/url-provenance :capt-gov-kw-official-tender-authority
     :statute/enacted-date "2016-07-31"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:public-procurement :market-entry}}
    {:statute/id "kwt.law-1-2016-commercial-companies-law"
     :statute/title "Commercial Companies Law"
     :statute/jurisdiction "KWT"
     :statute/kind :law
     :statute/law-number "Law No. 1 of 2016 (as amended by Law No. 15 of 2017 and Law No. 79 of 2019)"
     :statute/url "https://kdipa.gov.kw/invest-in-kuwait/economic-laws/"
     :statute/url-provenance :kdipa-gov-kw-official-investment-authority
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "kwt.law-116-2013-direct-investment-promotion-law"
     :statute/title "Law for the Promotion of Direct Investment in the State of Kuwait"
     :statute/jurisdiction "KWT"
     :statute/kind :law
     :statute/law-number "Law No. 116 of 2013"
     :statute/url "https://kdipa.gov.kw/about-kdipa/law-decisions/"
     :statute/url-provenance :kdipa-gov-kw-official-investment-authority
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:foreign-investment :market-entry}}
    {:statute/id "kwt.decree-3-1955-income-tax-law"
     :statute/title "Kuwaiti Income Tax"
     :statute/jurisdiction "KWT"
     :statute/kind :law
     :statute/law-number "Amiri Decree No. 3 of 1955 (as amended by Law No. 2 of 2008)"
     :statute/url "https://kdipa.gov.kw/invest-in-kuwait/economic-laws/"
     :statute/url-provenance :kdipa-gov-kw-official-investment-authority
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:tax}}
    {:statute/id "kwt.decree-law-157-2024-multinational-enterprise-tax-law"
     :statute/title "Tax Law on Multinational Enterprise Groups"
     :statute/jurisdiction "KWT"
     :statute/kind :law
     :statute/law-number "Decree-Law No. 157 of 2024"
     :statute/url "https://www.mof.gov.kw/"
     :statute/url-provenance :mof-gov-kw-official-finance-ministry
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:tax}}
    {:statute/id "kwt.law-6-2010-private-sector-labour-law"
     :statute/title "Private Sector Labour Law"
     :statute/jurisdiction "KWT"
     :statute/kind :law
     :statute/law-number "Law No. 6 of 2010"
     :statute/url "https://manpower.gov.kw/LaborLaw.aspx"
     :statute/url-provenance :pam-gov-kw-official-manpower-authority
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:labor}}]})

(defn spec-basis [jurisdiction] (get catalog jurisdiction))

(defn coverage
  ([] (coverage (keys catalog)))
  ([jurisdictions]
   (let [have (filter catalog jurisdictions)
         missing (remove catalog jurisdictions)]
     {:requested (count jurisdictions)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-kwt statute.facts (ADR-2607141700): "
                 (count (get catalog "KWT")) " Kuwait entries seeded "
                 "with capt.gov.kw / kdipa.gov.kw / mof.gov.kw / manpower.gov.kw "
                 "citations (several via Wayback Machine where the live site "
                 "blocked or timed out -- never bypassed). "
                 "Extend `statute.facts/catalog`, never fabricate an id/url.")})))

(defn by-topic [jurisdiction topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis jurisdiction)))
