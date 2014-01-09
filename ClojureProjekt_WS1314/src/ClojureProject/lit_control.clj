; -------------------------------------------------------------------------------------
(ns ClojureProject.lit_control
  (:require [ClojureProject.lit_data :as data]))

; -------------------------------------------------------------------------------------
; DATABASE OPERATIONS

(defn saveConfiguration 
  ""
  [saveVec]  
  (println saveVec))

(defn connectDatabase []  
  (println "connect database"))

(defn disconnectDatabase []  
  (println "disconnect database"))

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
