(ns clojure-for-fun.leetcode.38-count-and-say
  ;https://leetcode-cn.com/problems/count-and-say/
  )

; unnecessary verbose, transducer approach
(defn count-and-say-trans
  [rf]
  (let [a (java.util.ArrayList.)
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
  ([sb] (str sb))
  ([sb x] (.append sb x)))

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
   "122233")
  (= (countAndSay 10)
     "13211311123113112211")
  (= (countAndSay 17)
     "11131221131211132221232112111312212321123113112221121113122113111231133221121321132132211331121321231231121113122113322113111221131221")
  (= (countAndSay 21)
     "311311222113111231133211121312211231131112311211133112111312211213211312111322211231131122211311122122111312211213211312111322211213211321322113311213212322211231131122211311123113223112111311222112132113311213211221121332211211131221131211132221232112111312111213111213211231132132211211131221232112111312211213111213122112132113213221123113112221131112311311121321122112132231121113122113322113111221131221"))