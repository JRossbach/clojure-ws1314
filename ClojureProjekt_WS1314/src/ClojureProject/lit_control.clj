; -------------------------------------------------------------------------------------
;(ns ClojureProject.lit_control
;  (:require [ClojureProject.lit_data :as data]))

(ns ClojureProject.lit_control)
(require '[ClojureProject.lit_data :refer :all])

; -------------------------------------------------------------------------------------
; DATABASE OPERATIONS

(defn saveConfiguration
  ""
  [] ())

(defn controlConnectDatabase 
  ""
  [host db user password] (ClojureProject.lit_data/connectDatabase {:host host :db db :user user :password password}))

(defn controlDisconnectDatabase 
  ""
  [] (ClojureProject.lit_data/disconnectDatabase))

; -------------------------------------------------------------------------------------
; SEARCH ITEMS

(defn executeSearchTitle 
  ""
  [searchTitleMap] (println (get (ClojureProject.lit_data/selectTitle) 1)))

(defn executeSearchPublisher 
  ""
  [searchPublisherVector] (println (ClojureProject.lit_data/selectPublisher)))

; -------------------------------------------------------------------------------------
; ADD ITEMS

(defn executeAddTitle 
  ""
  [addTitleVector] (ClojureProject.lit_data/insertTitle {:name (get addTitleVector 1)
                                                         :isbn (get addTitleVector 2)                                                         
                                                         :author (get addTitleVector 3)
                                                         :publisher_id (get addTitleVector 4)}))

(defn executeAddPublisher 
  ""
  [addPublisherVector] (ClojureProject.lit_data/insertPublisher {:name (get addPublisherVector 1)}))

; -------------------------------------------------------------------------------------
; MODIFY ITEMS

(defn executeModifyTitle 
  ""
  [title] (println title))

(defn executeModifyPublisher 
  ""
  [publisher] (println publisher))

; -------------------------------------------------------------------------------------
; DELETE ITEMS

(defn executeDeleteTitle 
  ""
  [titleId] (ClojureProject.lit_data/deleteTitle titleId))

(defn executeDeletePublisher 
  ""
  [publisherId] (ClojureProject.lit_data/deletePublisher publisherId))
