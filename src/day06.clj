(ns day06
  (:require [utils]
            [clojure.string :as str]
            [clojure.java.io :as io]))

(defn parse-input [s]
  (->> (str/split s #",")
       (map #(Integer/parseInt (str/trim %)))
       (frequencies)))


(def test-input
  (parse-input "3,4,3,1,2"))


(def input
  (-> (io/resource "day06.txt")
       (slurp)
       (parse-input)))


(defn tick [state]
  {0 (get state 1 0)
   1 (get state 2 0)
   2 (get state 3 0)
   3 (get state 4 0)
   4 (get state 5 0)
   5 (get state 6 0)
   6 (+ (get state 0 0) (get state 7 0))
   7 (get state 8 0)
   8 (get state 0 0)})


(defn simulate [init-state num-days]
 (loop [day 0
        state init-state]
   (if (= day num-days)
     state
     (recur (inc day) (tick state)))))


(defn count-fish [state]
  (apply + (vals state)))


(defn solve [data num-days]
  (-> data
      (simulate num-days)
      (count-fish)))


(comment
  (solve test-input 18)
  (solve test-input 80)

  (solve input 80)

  (solve input 256)

  )


