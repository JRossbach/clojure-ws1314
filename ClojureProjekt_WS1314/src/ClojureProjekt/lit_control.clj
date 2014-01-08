; -------------------------------------------------------------------------------------
(ns ClojureProject.lit_control
  (:require [ClojureProjekt.lit_data :as data]))

; -------------------------------------------------------------------------------------

(defn saveConfiguration [saveVec]  
  (println saveVec))

(defn executeSearchTitle [searchVec]
  (println searchVec))

(defn executeSearchPublisher [searchVec]
  (println searchVec))

(defn executeModifyTitle [title]
  (println title))

(defn executeModifyPublisher [publisher]
  (println title))

(defn executeAddTitle [title]
  (println title))

(defn executeAddPublisher [publisher]
  (println publisher))

(defn executeDeleteTitle [title]
  (println title))

(defn executeDeletePublisher [publisher]
  (println publisher))