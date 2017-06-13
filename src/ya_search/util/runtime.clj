(ns ya-search.util.runtime
  (:import [java.lang Runtime Thread]))

(def ^:private hooks (atom {}))

(defn- run-hooks []
  (doseq [f (vals @hooks)] (f)))

(defonce ^:private init-shutdown-hook
         (delay (.addShutdownHook (Runtime/getRuntime) (Thread. #'run-hooks))))

(defn add-shutdown-hook [k f]
  (force init-shutdown-hook)
  (swap! hooks assoc k f))