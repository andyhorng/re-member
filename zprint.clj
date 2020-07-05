#!/usr/bin/env bb

;; bb --nrepl-server 5678
;; Using vim with conjure to develop

(require '[clojure.java.io :as io])
(require '[clojure.string :as s])
(require '[clojure.java.shell :refer [sh]])

(->> (io/file (or (first *command-line-args*) "."))
     file-seq
     (filter (fn [f]
               (let [fname (-> f .getName)]
                 (or (s/ends-with? fname ".clj")
                     (s/ends-with? fname ".cljs")
                     (s/ends-with? fname ".cljc")))))
     (map (fn [f]
            (prn (-> f .getName))
            (->>
              (sh "zprint" :in (slurp f))
              :out
              (spit f)))))
