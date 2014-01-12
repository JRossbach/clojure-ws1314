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
  [searchTitleVector] (println (ClojureProject.lit_data/selectTitle)))

(defn executeSearchPublisher 
  ""
  [searchPublisherVector] (println (ClojureProject.lit_data/selectPublisher)))

; -------------------------------------------------------------------------------------
; ADD ITEMS

(defn executeAddTitle 
  ""
  [addTitleVector] (ClojureProject.lit_data/insertTitle {:isbn (:isbn title)
                                                         :name (:name title)
                                                         :author (:author title)
                                                         :publisher_id (:publisher_id title)}))

(defn executeAddPublisher 
  ""
  [addPublisherVector] (ClojureProject.lit_data/insertPublisher {:name (:name publisher)})

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
