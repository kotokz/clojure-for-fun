(ns clojure-for-fun.leetcode.38-count-and-say
  ;https://leetcode-cn.com/problems/count-and-say/
  (:import (java.util ArrayList)))

; unnecessary verbose, transducer approach
(defn count-and-say-trans
  [rf]
  (let [a (ArrayList.)
        pv (volatile! ::none)]
    (fn
      ([] (rf))
      ([result]
       (let [result (if (.isEmpty a)
                      result
                      (let [v (vec (.toArray a))]
                        (.clear a)
                        (rf result (count v))
                        (unreduced (rf result (first v)))))]
         (rf result)))
      ([result input]
       (let [pval @pv]
         (vreset! pv input)
         (if (or (identical? pval ::none)
                 (= input pval))
           (do
             (.add a input)
             result)
           (let [v (vec (.toArray a))]
             (.clear a)
             (rf result (count v))
             (let [ret (rf result (first v))]
               (when-not (reduced? ret)
                 (.add a input))
               ret))))))))

(defn str!
  ([] (StringBuilder.))
  ([^StringBuilder sb] (str sb))
  ([^StringBuilder sb x] (.append sb x)))

(defn trans [^String input]
  (transduce
   count-and-say-trans
   str!
   input))


(defn countAndSay [n]
  (loop [acc "1"
         count 1]
    (if (= count n)
      acc
      (recur (trans acc) (inc count)))))


(comment
  (transduce
   count-and-say-trans
   str!
   "122233"))