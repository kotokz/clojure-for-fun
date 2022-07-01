(ns clojure-for-fun.riddles.prisoners)

; background https://rosettacode.org/wiki/100_prisoners

(defn random-boxes
  "Generate all the 100 numbers. each stands for the card number in the box. Index = prisoner number"
  []
  (shuffle (range 100)))

(defn find-box
  [boxes prisoner]
  (loop [box-num (nth boxes prisoner)
         counter 0]
     (cond
         (= counter 50) false
         (= prisoner box-num) true
         :else (recur (nth boxes box-num) (inc counter)))))

(defn start-game [_n]
  (let [new-boxes (random-boxes)]
    (->> (range 100)
         (map (partial find-box new-boxes ))
         (every? true?))))

(defn count-result
  ([] {})
  ([acc]
   (let [{:keys [success failed]} acc]
     (float (/ success (+ success failed)))))
  ([acc next]
   (if next
     (update acc :success (fnil inc 0))
     (update acc :failed (fnil inc 0)))))

(defn start-simulate [game-count]
  (transduce
    (map start-game)
    count-result
   (range game-count)))

(comment
  (find-box (random-boxes) 10)
  (start-simulate 50000)
  )