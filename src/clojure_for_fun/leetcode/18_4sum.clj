(ns clojure-for-fun.leetcode.18-4sum
  ;https://leetcode-cn.com/problems/4sum/
  )

(defn update-map [m k v]
  (update m k (fnil #(conj % v) [])))

(defn generate-two-sum [input]
  "Given a vector, generate all 2sum result to hashmap, sum value as key, and index of 2 elements as vector"
  (let [len (count input)]
    (loop [index 0
           point 1
           acc {}]
      (if (< point len)
        (recur index
               (inc point)
               (update-map acc
                           (+ (get input index)
                              (get input point))
                           [index point]))
        (if (< index (- len 1))
          (recur (inc index) (+ index 2) acc)
          acc)))))

(defn fourSum [input expect]
  (let [two-sum-map (generate-two-sum input)
        answer (transient #{})]
    (reduce-kv
      (fn [_ k v]
        (let [value (- expect k)]
          (if-let [value-set (two-sum-map value)]
            (->> (for [a v
                       b value-set]
                   (set (concat a b)))
                 (filter #(-> % count (> 3)))
                 (reduce #(conj! answer %2)))
            answer)))
      answer
      two-sum-map)
    (->> answer
         persistent!
         (into #{}
               (map (partial map (partial get input)))))
    ))

(comment
  (update-map {-3 [[1 2]] -2 [[0 1]]} -3 [0 3])
  (update-map {-3 [[1 2]] -2 [[0 1]]} 5 [0 3])
  (criterium.core/quick-bench
    (fourSum [1 0 -1 0 -2 2] 0)))