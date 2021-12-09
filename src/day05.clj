(ns day05
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  (slurp (io/resource "day05.txt")))
(def test-input
  (slurp (io/resource "day05-test.txt")))


(defn parse-line [line]
  (let [[_ & coords]
        (re-find #"(\d+),(\d+) -> (\d+),(\d+)" line)]
    (mapv #(Integer/parseInt %) coords)))


(defn horiz-or-vert-lines [[x1 y1 x2 y2 :as line]]
  (when (or (= x1 x2) (= y1 y2))
    (if (= x1 x2)
      (if (< y1 y2)
        [x1 y1 x2 y2]
        [x2 y2 x1 y1])
      (if (< x1 x2)
        [x1 y1 x2 y2]
        [x2 y2 x1 y1]))))


(defn get-points [[x1 y1 x2 y2]]
  (for [x (range x1 (inc x2))
        y (range y1 (inc y2))]
    [x y]))

(defn solve-part-1 [data]
  (->> data
       (str/split-lines)
       (map parse-line)
       (keep horiz-or-vert-lines)
       (mapcat get-points)
       (frequencies)
       (filter (fn [[_ n]] (> n 1)))
       (count)))

(comment

  (solve-part-1 test-input)
  (solve-part-1 input)
  )
