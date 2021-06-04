(ns clojure-for-fun.leetcode.6-zigzag-conversion
  ;https://leetcode-cn.com/problems/zigzag-conversion/
  )

(defn update-map [m k v]
  (update m k (fnil #(conj % v) [])))

(defn flip-fn [row-fn]
  (if (= row-fn inc)
    dec
    inc))

(defn solution [^String s ^long numRows]
  (loop [row 0
         res {}
         chars (seq s)
         row-fn dec]
    (if (seq chars)
      (if (or (= row 0) (= row (dec numRows)))
        (recur ((flip-fn row-fn) row) (update-map res row (first chars)) (rest chars) (flip-fn row-fn))
        (recur (row-fn row) (update-map res row (first chars)) (rest chars) row-fn))
      (->> res
           vals
           flatten
           (apply str)))))


(comment
  (= (solution "PAYPALISHIRING" 3)
     "PAHNAPLSIIGYIR")
  (= (solution "PAYPALISHIRING" 4)
     "PINALSIGYAHRPI")
  (= (solution "a" 1)
     "a"))