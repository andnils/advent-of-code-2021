(ns day02
  (:require [clojure.string :as str]
            [utils]))

(defn parse-fn [line]
  (let [[command value] (str/split line #" ")]
    [(keyword command) (utils/->int value)]))

(def input
  (utils/read-file "day02.txt" :parse-lines-with parse-fn))


(def test-input
  [[:forward 5]
   [:down 5]
   [:forward 8]
   [:up 3]
   [:down 8]
   [:forward 2]])

(defn- inc-fn [amount]
  (fn [x] (+ amount (or x 0))))


(def rules-step-1
  {:forward (fn [acc amount]
              (update acc :horiz (inc-fn amount)))
   :down    (fn [acc amount]
              (update acc :depth (inc-fn amount)))
   :up      (fn [acc amount]
              (update acc :depth (inc-fn (- amount))))})



(def rules-step-2
  {:forward (fn [acc amount]
              (-> acc
                  (update :horiz (inc-fn amount))
                  (update :depth (inc-fn (* (or (:aim acc) 0) amount)))))
   :down    (fn [acc amount]
              (-> acc
                  (update :aim (inc-fn amount))))
   :up      (fn [acc amount]
              (-> acc
                  (update :aim (inc-fn (- amount)))))} )




(def ^:dynamic *rules*)

(defn apply-command [state [command amount]]
  ((get *rules* command) state amount))


(defn solve [rules input]
  (binding [*rules* rules]
    (let [end-state (reduce apply-command {} input)]
      (* (:horiz end-state)
         (:depth end-state)))))


(comment
  (solve rules-step-1 test-input)
  (solve rules-step-2 test-input)

  (solve rules-step-1 input)
  (solve rules-step-2 input)
  
  )
