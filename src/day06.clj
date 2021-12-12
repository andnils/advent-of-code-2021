(ns day06
  (:require [utils]))

(def test-input [3 4 3 1 2])

;; gör om state till en map där vi räknar antal av varje "dag":
{0 0
 1 1
 2 1
 3 2
 4 1
 5 0
 6 0
 7 0
 8 0}

(def input
  (utils/read-edn-string "day06.txt"))



(defn increase-population [state]
  (let [zeroes (count (filter zero? state))]
    (concat state (repeat zeroes 9))))


(defn advance-timers [state]
  (map (fn [n] (if (zero? n) 6 (dec n))) state))

(defn tick [state]
  (-> state
      (increase-population)
      (advance-timers)))

(defn simulate [init-state num-days]
 (loop [day 0
        state init-state]
   (if (= day num-days)
     state
     (recur (inc day) (tick state)))))

(comment
  (count (simulate test-input 18))
  (count (simulate test-input 80))

  (count (simulate input 80))

  (count (simulate test-input 256))
  )


