(ns clojure-for-fun.utils
  (:require [criterium.core :as criterium]))

(defn get-digits-str [^long num]
  (-> num str count))


(defn get-digits [^long num]
  (loop [n num
         count 0]
    (if (= 0 n)
      count
      (recur (quot n 10) (inc count)))))


(comment
  (criterium.core/quick-bench
   (get-digits 123456789))
  (criterium/quick-bench
   (get-digits-str 123456789)))