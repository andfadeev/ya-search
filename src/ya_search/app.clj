(ns ya-search.app
  (:require [mount.core :as mount]
            [yada.yada :as yada]
            [bidi.vhosts :as vhosts]
            [cheshire.core :as json]
            [ya-search.ya-rss :as ya-rss]
            [hiccup.core :as html]
            [ya-search.pool :as pool]
            [yada.handler :refer [as-handler]]
            [ya-search.util.runtime :as runtime]
            [aleph.netty :as netty]
            [aleph.http :as http])
  (:gen-class))

(defn search-html [queries]
  (html/html [:div
              [:h1 "Stats"]
              (for [[k v] queries]
                [:div [:b k] ": " v])]))

(defn app-routes []
  ["/search" (yada/resource
               {:methods
                {:get
                 {:parameters {:query {:query [String]}}
                  :produces #{"text/html" "application/json"}
                  :response (fn [ctx]
                              (let [queries (get-in ctx [:parameters :query :query])
                                    freqs (ya-rss/get-frequencies pool/pool queries)]
                                (case (yada/content-type ctx)
                                  "text/html" (search-html freqs)
                                  (json/generate-string freqs {:pretty true}))))}}})])

(defn start-aleph-server [routes aleph-options]
  (let [server (http/start-server (as-handler routes) aleph-options)]
    {:port (aleph.netty/port server)
     :close (fn [] (.close server))
     :server (netty/wait-for-close server)}))

(defn start-app []
  (println "starting ya-search app")
  (let [vhosts-model (vhosts/vhosts-model [{:scheme :http :host "localhost:3000"} (app-routes)])]
    (start-aleph-server vhosts-model {:port 3000 :raw-stream? true})))

(defn stop-app [app]
  (when-let [close-fn (:close app)]
    (println "stopping ya-search app")
    (close-fn)))

(mount/defstate app
  :start (start-app)
  :stop (stop-app app))

(defn -main [& _]
  (runtime/add-shutdown-hook ::stop-system #(mount/stop))
  (mount/start))