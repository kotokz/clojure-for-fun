(ns clojure-for-fun.leetcode.18-4sum
  ;https://leetcode-cn.com/problems/4sum/
  )

(defn update-map [m k v]
  (update m k (fnil #(conj % v) [])))

(defn generate-two-sum-list [input]
  (let [len (count input)]
    (loop [index 0
           point 1
           acc []]
      (if (< point len)
        (recur index (inc point) (conj acc [index point]))
        (if (< index (- len 1))
          (recur (inc index) (+ index 2) acc)
          acc)))))

(defn gen-all-unique-pos [list1 list2]
  "Given 2 Vector each Vector have element contains 2 values
  generate all combination of the two list and put all values in hashset.
  Only keep hashet length = 4 to remove duplicated
  input: [[1 2] [2 3]] [[3 4]]
  output: #{#{1 4 3 2}}"
  (let [len1 (count list1)
        len2 (count list2)]
    (loop [index1 0
           index2 0
           acc #{}]
      (if (< index2 len2)
        (let [value (set (concat (get list1 index1) (get list2 index2)))]
          (if (-> value count (> 3))
            (recur index1 (inc index2) (conj acc value))
            (recur index1 (inc index2) acc)))
        (if (< index1 (- len1 1))
          (recur (inc index1) 0 acc)
          acc)))))

(defn generate-two-sum [input]
  "Given a vector, generate all 2sum result to hashmap, sum value as key, and index of 2 elements as vector"
  (->> input
       generate-two-sum-list
       (reduce #(update-map %1 (reduce (fn [acc value]
                                         (+ acc (get input value))) 0 %2) %2)
               {})))

(defn fourSum [input expect]
  (let [two-sum-map (generate-two-sum input)
        answer (transient #{})]
    (reduce-kv
      (fn [_ k v]
        (let [value (- expect k)]
          (if-let [value-set (two-sum-map value)]
            (doseq [e (gen-all-unique-pos v value-set)]
              (conj! answer e))
            answer)))
      answer
      two-sum-map)
    (->> answer
         persistent!
         (into #{}
               (comp
                 (map seq)
                 (map (fn [[a b c d]]
                        (conj [(get input a)
                               (get input b)
                               (get input c)
                               (get input d)]))))))
    ))

(comment
  (update-map {-3 [[1 2]] -2 [[0 1]]} -3 [0 3])
  (update-map {-3 [[1 2]] -2 [[0 1]]} 5 [0 3])
  (criterium.core/quick-bench
    (fourSum [1 0 -1 0 -2 2] 0)))