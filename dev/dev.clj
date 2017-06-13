(ns dev
    (:require [clojure.tools.namespace.repl :refer [refresh]]
      [mount.core :as mount]))

(defn stop-mount []
      (mount/stop))

(defn start-mount []
      (clojure.pprint/pprint
        {::mount (mount/start)}))

(defn reset []
      (stop-mount)
      (refresh :after 'dev/start-mount))