(ns day01
  (:require [utils]))


(def input
  (utils/read-file "day01.txt" :parse-lines-with utils/->int))


(def test-input
  [199 200 208 210
   200 207 240 269
   260 263])


(defn solve-part-1 [input]
  (->> input
       (partition 2 1)
       (filter (fn [[x y]] (< x y)))
       (count)))


(defn solve-part-2 [input]
  (->> input
       (partition 3 1)
       (map utils/sum)
       (solve-part-1)))


(comment
  (solve-part-1 test-input)
  (solve-part-1 input)

  (solve-part-2 test-input)
  (solve-part-2 input)
  )
