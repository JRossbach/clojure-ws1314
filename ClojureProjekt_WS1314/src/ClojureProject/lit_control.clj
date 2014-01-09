; -------------------------------------------------------------------------------------
;(ns ClojureProject.lit_control
;  (:require [ClojureProject.lit_data :as data]))

(ns ClojureProject.lit_control)
(require '[ClojureProject.lit_data :refer :all])

; -------------------------------------------------------------------------------------
; DATABASE OPERATIONS

(defn saveConfiguration 
  ""
  [saveVec]  
  (println saveVec))

(defn controlConnectDatabase [db host user password]  
  (ClojureProject.lit_data/connectDatabase db host user password))

(defn controlDisconnectDatabase []  
  (ClojureProject.lit_data/connectDatabase))

; -------------------------------------------------------------------------------------
; SEARCH ITEMS

(defn executeSearchTitle [searchVec]
  (println searchVec))

(defn executeSearchPublisher [searchVec]
  (println searchVec))
; -------------------------------------------------------------------------------------
; SEARCH ITEMS

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
