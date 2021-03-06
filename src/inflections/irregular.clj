(ns inflections.irregular
  (:use [clojure.string :only [lower-case]]
        [inflections.plural :only [plural!]]
        [inflections.singular :only [singular!]]
        [inflections.uncountable :only [delete-uncountable!]]))

(def ^{:dynamic true} *irregular-words*
  (atom (sorted-set)))

(defprotocol Irregular
  (add-irregular! [singular plural]
    "Adds obj to the set of *irregular-words*.")
  (delete-irregular! [singular plural]
    "Delete obj from the set of *irregular-words*.")
  (irregular? [obj]
    "Returns true if obj is an irregular word, otherwise false."))

(extend-type clojure.lang.Keyword
  Irregular
  (add-irregular! [singular plural]
    (add-irregular! (name singular) (name plural)))
  (delete-irregular! [singular plural]
    (delete-irregular! (name singular) (name plural)))
  (irregular? [k]
    (irregular? (name k))))

(extend-type String
  Irregular
  (add-irregular! [singular plural]
    (let [singular (lower-case singular)
          plural (lower-case (name plural))]
      (delete-uncountable! singular)
      (delete-uncountable! plural)
      (singular! (re-pattern (str "^" plural "$")) singular)
      (plural! (re-pattern (str "^" singular "$")) plural)
      (swap! *irregular-words* conj singular)
      (swap! *irregular-words* conj plural)))
  (delete-irregular! [singular plural]
    (let [singular (lower-case singular)
          plural (lower-case (name plural))]
      (swap! *irregular-words* disj singular)
      (swap! *irregular-words* disj plural)))
  (irregular? [s]
    (contains? @*irregular-words* (lower-case s))))

(extend-type clojure.lang.Symbol
  Irregular
  (add-irregular! [singular plural]
    (add-irregular! (name singular) (name plural)))
  (delete-irregular! [singular plural]
    (delete-irregular! (name singular) (name plural)))
  (irregular? [k]
    (irregular? (name k))))

(defn init-irregular-words []
  (doall
   (map #(add-irregular! (first %) (second %))
        [["amenity" "amenities"]
         ["child" "children"]
         ["cow" "kine"]
         ["foot" "feet"]
         ["louse" "lice"]
         ["mailman" "mailmen"]
         ["man" "men"]
         ["mouse" "mice"]
         ["move" "moves"]
         ["ox" "oxen"]
         ["person" "people"]
         ["sex" "sexes"]
         ["tooth" "teeth"]
         ["woman" "women"]])))
