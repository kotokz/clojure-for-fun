(ns clojure-for-fun.leetcode.18-4sum-test
  (:require [clojure.test :refer :all])
  (:require [clojure-for-fun.leetcode.18-4sum :refer [fourSum]]))

(deftest fourSum-test
  (is (= (fourSum [1 0 -1 0 -2 2] 0)
         #{[1 0 0 -1] [0 -2 0 2] [1 -2 -1 2]}))
  (is (= (fourSum [2 2 2 2 2] 8)
         #{[2 2 2 2]})))
