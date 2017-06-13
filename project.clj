(defproject ya-search "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main ya-search.app
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [mount "0.1.11"]
                 [bidi "2.0.16"]
                 [yada "1.2.2" :exclusions [aleph]]
                 [aleph "0.4.3"]
                 [com.cemerick/url "0.1.1"]
                 [org.clojure/data.xml "0.0.8"]
                 [cheshire "5.7.1"]
                 [clj-http "3.6.1"]
                 [hiccup "1.0.5"]
                 [com.climate/claypoole "1.1.4"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.11"]]
                   :source-paths ["dev"]}})
