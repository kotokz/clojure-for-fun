(ns clojure-for-fun.leetcode.1295-find-numnbers-with-even-number-of-digits
  ; https://leetcode-cn.com/problems/find-numbers-with-even-number-of-digits/
  ; Given an array nums of integers, return how many of them contain an even number of digits.
  (:require [clojure-for-fun.utils :as utils]
            [criterium.core :as criterium]))


(defn find-numbers [nums]
  (->> nums
       (map utils/get-digits)
       (filter even?)
       count))

(comment
  (def nums
    [12 345 2 6 7896])
  (= (find-numbers nums)
     2)
  (= (find-numbers  [555 901 482 1771])
     1)
  (criterium/quick-bench
   (utils/get-digits 123456789)))