(ns culture.facts
  "Country-level regional-culture catalog for Kuwait (KWT) -- national
  dishes, protected products, beverages, crafts, festivals and heritage
  sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"KWT"
   [{:culture/id "kwt.dish.machboos"
     :culture/name "Machboos (kabsa)"
     :culture/country "KWT"
     :culture/kind :dish
     :culture/summary "Arab mixed rice dish of rice, meat, vegetables and aromatic spices; commonly regarded as a national dish in all Gulf Cooperation Council countries and listed as a national symbol of Kuwait, where it is known as machboos."
     :culture/url "https://en.wikipedia.org/wiki/Kabsa"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kwt.dish.mutabbaq-samak"
     :culture/name "Mutabbaq samak"
     :culture/country "KWT"
     :culture/kind :dish
     :culture/summary "Rice-based dish of spiced fried fish and caramelized onions served over rice cooked in spiced fish stock; considered a national dish in Iraq and Kuwait."
     :culture/url "https://en.wikipedia.org/wiki/Mutabbaq_samak"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kwt.dish.harees"
     :culture/name "Harees"
     :culture/country "KWT"
     :culture/kind :dish
     :culture/summary "Dish of boiled cracked wheat mixed with meat, spices and butter, commonly eaten in Arab states of the Persian Gulf during Ramadan; its know-how was inscribed as UNESCO intangible cultural heritage in 2023 by Kuwait among other Gulf states."
     :culture/url "https://en.wikipedia.org/wiki/Harees"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kwt.dish.murabyan"
     :culture/name "Murabyan"
     :culture/country "KWT"
     :culture/kind :dish
     :culture/summary "Kuwaiti dish of rice cooked with either fresh or dried shrimp."
     :culture/url "https://en.wikipedia.org/wiki/Kuwaiti_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kwt.dish.gers-ogaily"
     :culture/name "Gers ogaily"
     :culture/country "KWT"
     :culture/kind :dish
     :culture/summary "Traditional Kuwaiti cake made with eggs, flour, sugar, cardamom and saffron, traditionally served with tea."
     :culture/url "https://en.wikipedia.org/wiki/Kuwaiti_cuisine"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kwt.craft.al-sadu"
     :culture/name "Al Sadu weaving"
     :culture/country "KWT"
     :culture/kind :craft
     :culture/summary "Traditional Bedouin craft of weaving geometric patterns on a ground loom; Sadu House in Kuwait was established in 1980, and traditional Al Sadu weaving in Kuwait and Saudi Arabia was inscribed by UNESCO as intangible cultural heritage in 2020."
     :culture/url "https://en.wikipedia.org/wiki/Al_Sadu"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kwt.craft.dhow"
     :culture/name "Dhow"
     :culture/country "KWT"
     :culture/kind :craft
     :culture/summary "Traditional sailing vessel of the Red Sea and Indian Ocean; Kuwait's Al-Hashemi-II is recognized by Guinness World Records as the largest wooden dhow ever built, and the Kuwaiti Maritime Museum in Salmiya holds replicas of many dhow types."
     :culture/url "https://en.wikipedia.org/wiki/Dhow"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kwt.festival.hala-febrayer"
     :culture/name "Hala Febrayer"
     :culture/country "KWT"
     :culture/kind :festival
     :culture/summary "Month-long festival commemorating the liberation of Kuwait, running from 1 to 28 February with music concerts, parades and carnivals; Liberation Day itself is celebrated on 26 February."
     :culture/url "https://en.wikipedia.org/wiki/Kuwait"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kwt.heritage.failaka-island"
     :culture/name "Failaka Island"
     :culture/country "KWT"
     :culture/kind :heritage
     :culture/summary "Kuwaiti island in the Persian Gulf about 20 km off Kuwait City, inhabited since antiquity with archaeological evidence of Dilmun, Mesopotamian and Hellenistic Greek settlement (the Greeks called it Ikaros)."
     :culture/url "https://en.wikipedia.org/wiki/Failaka_Island"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-kwt culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "KWT"))
                 " KWT entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
