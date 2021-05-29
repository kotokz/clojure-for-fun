(ns clojure-for-fun.leetcode.20-valid-parentheses
  ;https://leetcode-cn.com/problems/valid-parentheses/
  (:require [clojure.string :as str]
            [criterium.core :as criterium]))

(defn replace-pair [^String input]
  (str/replace input #"\(\)|\[\]|\{\}" ""))

(defn n-times [n f]
  (apply comp (repeat n f)))

(defn replace-pair-n-times [^String input]
  (-> input
      count
      (/ 2)
      int
      (n-times replace-pair)
      (#(% input))))

(defn isValid [^String input]
  (->> input
       (replace-pair-n-times)
       (str/blank?)))

(defn peek! [tv] (nth tv (dec (count tv))))

(defn is-valid? [^String input]
  (let [stack (transient [])]
    (reduce #(condp = %2
               \[ (conj! stack \])
               \( (conj! stack \))
               \{ (conj! stack \})
               (if (= %2 (peek! stack))
                 (do (pop! stack)
                     true)
                 (reduced false)))
            true
            input)))

(comment
  (def test-examples
    ["()" "()[]{}" "(]" "([)]" "{[]}"])
  (str/replace "()[" #"\(\)|\[\]|\{\}" "")
  (replace-pair "()")
  (-> "12315" count (/ 2) int)
  (= (mapv isValid test-examples)
     [true true false false true])
  (= (mapv is-valid? test-examples)
     [true true false false true])
  (criterium/quick-bench
   (mapv isValid test-examples))
  (criterium/quick-bench
   (mapv is-valid? test-examples)))
