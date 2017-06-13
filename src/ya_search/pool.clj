(ns ya-search.pool
  (:require [com.climate.claypoole :as cp]
            [mount.core :as mount]))

;;todo: move to config
(def MAX_THREAD_COUNT 10)

(defn start-pool []
  (cp/threadpool MAX_THREAD_COUNT))

(defn stop-pool [pool]
  (cp/shutdown pool))

(mount/defstate pool
  :start (start-pool)
  :stop (stop-pool pool))