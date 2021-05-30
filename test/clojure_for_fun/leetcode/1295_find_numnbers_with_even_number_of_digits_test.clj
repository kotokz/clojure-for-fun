(ns clojure-for-fun.leetcode.1295-find-numnbers-with-even-number-of-digits-test
  (:require [clojure.test :refer :all])
  (:require [clojure-for-fun.leetcode.1295-find-numnbers-with-even-number-of-digits :refer [find-numbers]]))

(deftest find-numbers-test
  (is (= (find-numbers [12 345 2 6 7896])
         2))
  (is (= (find-numbers [555 901 482 1771])
         1)))
