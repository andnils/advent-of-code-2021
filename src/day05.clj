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


(defn horiz? [[_ y1 _ y2]]
  (= y1 y2))

(defn vert? [[x1 _ x2 _]]
  (= x1 x2))


(defn get-points [[x1 y1 x2 y2]]
  (let [x1' (min x1 x2)
        x2' (inc (max x1 x2))
        y1' (min y1 y2)
        y2' (inc (max y1 y2))]
    (for [x (range x1' x2')
          y (range y1' y2')]
      [x y])))




(defn solve-part-1 [data]
  (->> data
       (str/split-lines)
       (map parse-line)
       (filter (fn [line] (or (horiz? line) (vert? line))))
       (mapcat get-points)
       (frequencies)
       (filter (fn [[_ n]] (> n 1)))
       (count)))

(comment

  (solve-part-1 test-input)
  (solve-part-1 input)
  )
