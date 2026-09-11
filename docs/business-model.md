# Business Model: Independent Public-Sector Market-Entry & Procurement Compliance Service — Kuwait

## Classification

- Repository: `cloud-itonami-iso3166-kwt`
- ISO 3166: `KWT` (Kuwait)
- Activity: public-procurement market-entry and ongoing regulatory-
  compliance navigation for an already-incorporated operator
- Social impact: [:kuwaiti-sme-market-access :public-spend-transparency :cross-border-friction-reduction]

## Customer

- an already-incorporated `cloud-itonami-cofog-{code}` /
  `cloud-itonami-isco-{code}` / `cloud-itonami-unspsc-{segment}` /
  `cloud-itonami-{ISIC}` operator wanting to bid on a Kuwaiti
  public contract
- a foreign SME or civic-tech vendor entering the public sector in
  Kuwait for the first time
- a `cloud-itonami-M6910` client that has just completed incorporation and
  now needs public-sector market access

## Offer

- registration walkthrough for the Central Agency for Public Tenders
  (CAPT, الجهاز المركزي للمناقصات العامة) -- registration/renewal as a
  tenderer, contractor, supplier or service provider, and the CAPT
  contractor/supplier classification (تصنيف) process
  (https://www.capt.gov.kw/)
- business/tax registration checklist: Commercial Registration (CR)
  via the Kuwait Business Center (KBC, https://kbc.moci.gov.kw/) under
  the Ministry of Commerce and Industry (MOCI); CR verification via
  MOCI's electronic commercial-registry service
  (https://ereg.moci.gov.kw/)
- foreign-ownership navigation under the Law for the Promotion of
  Direct Investment in the State of Kuwait (Law No. 116 of 2013,
  administered by the Kuwait Direct Investment Promotion Authority,
  KDIPA) -- the client's actual licensed-activity classification and
  any implementing-regulation detail beyond the law's own title/number
  is NOT asserted here and routes to Kuwaiti-licensed counsel
- Article-31 bidder-eligibility navigation: by default a CAPT bidder
  must be Kuwaiti, Commercial-Register-registered and CAPT-registered
  (General Tenders Law, Law No. 49 of 2016); a FOREIGN bidder is an
  explicit statutory exception, and if awarded, becomes subject to
  Article 87's National-Industry and Local-Contractor Preference (see
  Trust Controls below -- this actor's flagship compliance check)
- ongoing regulatory-change monitoring subscription
- compliance-audit export package for the client's own records

## Revenue

- per-engagement market-entry fee (one-time registration + checklist
  completion)
- recurring regulatory-change monitoring subscription
- compliance-audit export package

## Trust Controls

- any actual portal registration or filing submission requires
  Market-Entry Compliance Governor clearance and always escalates to
  human sign-off (`:filing/submit` is never automated at any phase)
- a false or fabricated regulatory-requirement claim is a HARD hold that
  cannot be overridden by human approval alone — it must be corrected
  against a cited official source first
- **flagship check**: a FOREIGN contractor's engagement declaring
  Article 87 applies (`:requires-local-content-commitment? true`) is
  independently re-verified against BOTH of the article's own stated
  30% floors -- national-industry purchase commitment AND
  local-contractor subcontract commitment -- before a filing/submit
  proposal may proceed; either commitment below 30% is an
  unoverridable HARD hold (`:local-content-shortfall`)
- this service does **not** provide legal or tax advice; characterization
  and filing on the client's behalf beyond checklist/draft assistance
  routes to Kuwaiti-licensed counsel or a registered agent
- every requirement cites the official portal or regulation, never
  invented — see `src/marketentry/facts.cljk` and
  `src/statute/facts.cljk` for the exact URLs fetched and read this
  session (several via the Internet Archive Wayback Machine where the
  live government site returned a genuine Cloudflare bot-detection
  challenge or otherwise failed to connect -- never bypassed), and
  their docstrings for gaps disclosed rather than filled in with an
  invented figure (e.g. no specific corporate income-tax RATE is
  asserted, no Zakat/National-Labor-Support-Tax law number is
  asserted, and no Council-of-Ministers-adjusted local-content
  percentage beyond Article 87's own stated 30% baseline is asserted)

## Boundary with adjacent actors (read before forking)

- **`com-etzhayyim-ooyake`** (etzhayyim/root): read-only civic-wayfinding
  mirror of government structure, non-commercial, barred from acting as
  or for the government (G3 impersonation ban). This blueprint is
  commercial and never claims to be an official channel.
- **`matsurigoto`** (etzhayyim/root): sovereign e-government statecraft —
  literally the government, for etzhayyim's own covenant or an adopting
  nation-state. This blueprint is an independent operator the government
  contracts with or that bids into its procurement — never the
  government.
- **`com-etzhayyim-toritsugi`** (etzhayyim/root): guides a consenting
  INDIVIDUAL citizen through their OWN procedure, non-profit,
  donation-only. This blueprint's client is a business operator, not an
  individual citizen, and it is commercial.
- **`legal-entity.etzhayyim.com`**: read-only aggregated company-registry
  data, no execution. This blueprint executes (gated) registrations.
- **`cloud-itonami-M6910`**: helps a client BECOME a legal entity
  (incorporation, ISIC 6910) — a prior, different regulatory phase
  (company law). This blueprint assumes incorporation is already done and
  handles public-procurement market entry (a different regulatory domain).
- **`cloud-itonami-cofog-{code}`**: a jurisdiction-agnostic operator
  template for ONE public function. This blueprint is the orthogonal
  jurisdiction-specific axis — the two compose (fork a COFOG-function
  blueprint AND this one to operate in Kuwait).
