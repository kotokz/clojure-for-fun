(ns clojure-for-fun.riddles.prisoners)


(defn random-boxes []
  (shuffle (range 100)))


(defn find-box
  [boxes prisoner]
  (loop [next prisoner
         counter 0]
    (let [box-num (nth boxes next)]
      (cond
        (= counter 50) false
        (= prisoner box-num) true
        :else (recur box-num (inc counter))))))

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