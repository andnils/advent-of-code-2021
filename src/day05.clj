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

(defn diag? [[x1 y1 x2 y2]]
  (=(- (max x1 x2) (min x1 x2))
    (- (max y1 y2) (min y1 y2))))

(defn line-type [coords]
  (cond
    (diag? coords) [coords :diag (get-points coords)]
    (horiz? coords) [coords :horiz (get-points coords)]
    (vert? coords) [coords :vert (get-points coords)]
    :else [coords :unknown (get-points coords)]))


(defn get-points [[x1 y1 x2 y2 :as coords]]
  (if (diag? coords)
    (let [xdir (if (< x2 x1) -1 1)
          ydir (if (< y2 y1) -1 1)]
      (map (fn [x y] [x y])
           (range x1 (+ x2 xdir) xdir)
           (range y1 (+ y2 ydir) ydir)))
    ;;
    (let [x1' (min x1 x2)
          x2' (inc (max x1 x2))
          y1' (min y1 y2)
          y2' (inc (max y1 y2))]
      (for [x (range x1' x2')
            y (range y1' y2')]
        [x y]))))





(defn solve-part-1 [data]
  (->> data
       (str/split-lines)
       (map parse-line)
       (filter (fn [line] (or (horiz? line) (vert? line))))
       (mapcat get-points)
       (frequencies)
       (filter (fn [[_ n]] (> n 1)))
       (count)))

(defn solve-part-2 [data]
  (->> data
       (str/split-lines)
       (map parse-line)
       ;;(filter (fn [line] (or (horiz? line) (vert? line) (diag? line))))
       (mapcat get-points)
       (frequencies)
       (filter (fn [[_ n]] (> n 1)))
       (count)
       ))

(comment

  (solve-part-1 test-input)
  (solve-part-1 input)

  (solve-part-2 test-input)
  (solve-part-2 input)

   (->> test-input
       (str/split-lines)
       (map parse-line))

   (get-points  [8 0 0 8])

   (map line-type
        '(
          [0 9 5 9]
          [8 0 0 8]
          [9 4 3 4]
          [2 2 2 1]
          [7 0 7 4]
          [6 4 2 0]
          [0 9 2 9]
          [3 4 1 4]
          [0 0 8 8]
          [5 5 8 2]
          ))
  )
