# cloud-itonami-iso3166-kwt

**`:implemented`** for **KWT**. Flagship `local-content-shortfall` (Article 87), entity `kw-entity-missing` (Article 31).

```
kbb -M:dev:test
```

## Market-entry / statute catalogs

- Central Agency for Public Tenders (CAPT, الجهاز المركزي للمناقصات
  العامة) -- Kuwait's public-procurement authority; its own site
  (https://www.capt.gov.kw/) is also its registration/classification/
  e-tendering portal (no separately-branded portal name found, unlike
  e.g. Oman's "Esnad"). Legal basis: General Tenders Law, Law No. 49
  of 2016, as amended by Law No. 74 of 2019.
- Commercial Registration: Kuwait Business Center (KBC,
  https://kbc.moci.gov.kw/) and the electronic commercial-registry
  service (https://ereg.moci.gov.kw/), both operated under the
  Ministry of Commerce and Industry (MOCI).
- Foreign investment: Law for the Promotion of Direct Investment in
  the State of Kuwait, Law No. 116 of 2013 (administered by KDIPA,
  https://kdipa.gov.kw/) -- cross-referenced directly inside Article
  31 of the General Tenders Law itself.
- Tax: Kuwaiti Income Tax, Amiri Decree No. 3 of 1955 (as amended by
  Law No. 2 of 2008); Tax Law on Multinational Enterprise Groups,
  Decree-Law No. 157 of 2024 (Ministry of Finance, mof.gov.kw).
- Labor: Private Sector Labour Law, Law No. 6 of 2010 (Public
  Authority for Manpower, manpower.gov.kw).

Every citation above was fetched and read directly this session (see
`src/marketentry/facts.cljk` and `src/statute/facts.cljk` docstrings
for exact URLs and verbatim confirmation). CAPT's live site returns a
genuine Cloudflare bot-detection challenge and KDIPA's/MOF's/PAM's
live sites either errored or timed out -- those were fetched via the
Internet Archive Wayback Machine instead, never bypassed. Gaps that
could not be independently confirmed this session (e.g. a specific
corporate income-tax rate, a Zakat/National-Labor-Support-Tax law
number, or a Council-of-Ministers-adjusted local-content percentage
beyond Article 87's own stated 30% floor) are disclosed in
`src/statute/facts.cljk`, not filled in with an invented figure.

AGPL-3.0-or-later.

## Culture catalog

Alongside the market-entry / statute catalogs, this repo carries a
**country-level regional-culture catalog** (ADR-2607171400 addendum 2,
`cloud-itonami-municipality-culture-catalog` Wave 1, in
`com-junkawasaki/root`) — national dishes, protected products, beverages,
crafts, festivals and heritage sites for Kuwait:

- `src/culture/facts.cljk` — the catalog, source of truth (keyed by
  uppercase ISO3, mirroring `statute.facts`).
- `schema/culture.edn` — DataScript schema.
- `data/culture-tx.edn` — derived DataScript tx-data (regenerated from
  the catalog, never hand-edited).

City-level counterparts live in the `cloud-itonami-municipality-*` repos.
Same provenance discipline as the compliance catalogs: every entry cites a
source URL that was actually fetched and read on `:culture/retrieved-at`;
summaries state only what the cited source confirms. An item not in
`culture.facts/catalog` has no spec-basis — never fabricate one.
