(ns day03
  (:require [utils]))

(def input (utils/read-file "day03.txt"))


(def test-input
  ["00100"
   "11110"
   "10110"
   "10111"
   "10101"
   "01111"
   "00111"
   "11100"
   "10000"
   "11001"
   "00010"
   "01010"])


(defn get-bit-in-column [idx]
  (fn [s] (nth s idx)))


(defn get-most-common [m]
  (let [zeroes (get m \0 0)
        ones (get m \1 0)]
    (if (> zeroes ones) 0 1)))



(defn get-least-common [m]
  (let [zeroes (get m \0 0)
        ones (get m \1 0)]
    (if (> zeroes ones) 1 0)))



(defn find-most-common-bit [data pos]
  (->> data
       (map (get-bit-in-column pos))
       (frequencies)
       (get-most-common)))


(defn find-least-common-bit [data pos]
  (->> data
       (map (get-bit-in-column pos))
       (frequencies)
       (get-least-common)))


(defn find-most-common-bits [data]
  (let [n (count (first data))]
    (apply str (mapv (fn [pos] (find-most-common-bit data pos)) (range n)))))


(defn find-least-common-bits [data]
  (let [n (count (first data))]
    (apply str (mapv (fn [pos] (find-least-common-bit data pos)) (range n)))) )


(defn- fill-string [s n]
  (apply str (repeat n s)))


(defn gamma-rate [data]
  (Integer/parseUnsignedInt (find-most-common-bits data) 2))


(defn epsilon-rate [data]
  (let [n (count (first data))]
    (bit-xor (gamma-rate data) (Integer/parseUnsignedInt (fill-string "1" n) 2))))


(defn solve-part-1 []
  (let [data input]
    (* (gamma-rate data)
       (epsilon-rate data))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defn oxygen-rating [input]
  (loop [data input
         pos 0]
    (let [most-common-bits (find-most-common-bits data)]
      (if (or (= pos (count (first data))) (= (count data) 1))
        (Integer/parseUnsignedInt (first data) 2)
        (recur (filter (fn [x]
                         (= (nth x pos)
                            (nth most-common-bits pos)))
                       data)
               (inc pos))))))


(defn co2-scrubber-rating [input]
  (loop [data input
         pos 0]
    (let [least-common-bits (find-least-common-bits data)]
      (if (or (= pos (count (first data))) (= (count data) 1))
        (Integer/parseUnsignedInt (first data) 2)
        (recur (filter (fn [x]
                         (= (nth x pos)
                            (nth least-common-bits pos)))
                       data)
               (inc pos))))))

(defn solve-part-2 []
  (let [data input]
    (* (oxygen-rating data)
       (co2-scrubber-rating data))))




(comment
  (solve-part-1)
  (solve-part-2)
  )
