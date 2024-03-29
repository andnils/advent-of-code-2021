(ns utils
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.edn :as edn]))

(set! *warn-on-reflection* true)

(defn abs
  "Absolute value of `i`"
  [^long i]
  (Math/abs i))

(defn ->int [s]
  (Integer/parseInt (str s)))

(def sum
  "Sum of numbers"
  (partial reduce +))

(defn read-edn-string [filename]
  (-> filename
      (io/resource)
      (slurp)
      (edn/read-string)))

(defn read-file [filename & {:keys [parse-lines-with]
                             :or {parse-lines-with identity}}]
  (->> (io/resource filename)
       (slurp)
       (str/split-lines)
       (map parse-lines-with)))


