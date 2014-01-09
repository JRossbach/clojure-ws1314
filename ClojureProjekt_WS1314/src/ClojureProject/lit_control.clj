; -------------------------------------------------------------------------------------
;(ns ClojureProject.lit_control
;  (:require [ClojureProject.lit_data :as data]))

(ns ClojureProject.lit_control)
(require '[ClojureProject.lit_data :refer :all])

; -------------------------------------------------------------------------------------
; DATABASE OPERATIONS

(defn saveConfiguration 
  [] ())

(defn controlConnectDatabase [host db user password]  
  (ClojureProject.lit_data/connectDatabase host db user password))

(defn controlDisconnectDatabase []  
  (ClojureProject.lit_data/disconnectDatabase))

; -------------------------------------------------------------------------------------
; SEARCH ITEMS

(defn executeSearchTitle [searchVec]
  (println (ClojureProject.lit_data/selectTitle)))

(defn executeSearchPublisher [searchVec]
  (println (ClojureProject.lit_data/selectPublisher)))

; -------------------------------------------------------------------------------------
; ADD ITEMS

(defn executeAddTitle [title]
  (println title))

(defn executeAddPublisher [publisher]
  (println publisher))

; -------------------------------------------------------------------------------------
; MODIFY ITEMS

(defn executeModifyTitle [title]
  (println title))

(defn executeModifyPublisher [publisher]
  (println publisher))

; -------------------------------------------------------------------------------------
; DELETE ITEMS

(defn executeDeleteTitle [title]
  (println title))

(defn executeDeletePublisher [publisher]
  (println publisher))
