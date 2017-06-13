(ns ya-search.ya-rss
  (:require [clj-http.client :as client]
            [clojure.data.xml :as xml]
            [cemerick.url :as url]
            [clojure.string :as str]
            [com.climate.claypoole :as cp]))

(defn- filter-item-fn [element]
  (= (:tag element) :item))

(defn- filter-link-fn [element]
  (= (:tag element) :link))

(defn- normalize-host [host]
  (str/join "." (take-last 2 (str/split host #"\."))))

(defn- extract-links [raw-xml-str]
  (let [raw-xml (xml/parse-str raw-xml-str)
        rss (-> raw-xml :content first :content)]
    (->> rss
         (filter filter-item-fn)
         (take 10)
         (mapcat :content)
         (filter filter-link-fn)
         (mapcat :content))))

(defn- extract-host [link]
  (->> link
       url/url
       :host
       normalize-host))

(defn ya-search [query]
  (let [search-url (str "http://blogs.yandex.ru/search.rss?text=" query)
        resp (client/get search-url)]
    (:body resp)))

(defn get-frequencies [pool queries]
  (let [links (->> (cp/pmap pool ya-search queries)
                   (mapcat extract-links)
                   distinct
                   (map extract-host))]
    (frequencies links)))
