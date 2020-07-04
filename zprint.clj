#!/usr/bin/env bb

;; bb --nrepl-server 5678
;; Using vim with conjure to develop

(require '[clojure.java.io :as io])
(require '[clojure.string :as s])
(require '[clojure.java.shell :refer [sh]])

(do (->> (io/file (or (first *command-line-args*) "."))
         (file-seq)
         (filter (fn [line] (or (s/ends-with? line ".clj") (s/ends-with? line ".cljs") (s/ends-with? line ".cljc"))))
         (map (fn [f]
                (->> (sh "zprint" :in (io/file f))
                     :out
                     (spit (io/file f))))))
    (prn "done"))
