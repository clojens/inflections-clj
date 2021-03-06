# Clojure Inflection Library [![Build Status](https://travis-ci.org/r0man/inflections-clj.png)](https://travis-ci.org/r0man/inflections-clj)

Rails-like inflection library for Clojure and ClojureScript.

## Installation

Via Clojars: http://clojars.org/inflections.

## Usage

    (use 'inflections.core)

    (plural "word")
    ;=> "words"

    (plural "virus")
    ;=> "viri"

    (singular "apples")
    ;=> "apple"

    (singular "octopi")
    ;=> "octopus"

    (underscore "puni-puni")
    ;=> "puni_puni"

    (ordinalize "52")
    ;=> "52nd"

    (capitalize "clojure")
    ;=> "Clojure"

## License

Copyright (C) 2013 Roman Scherer

Distributed under the Eclipse Public License, the same as Clojure.
