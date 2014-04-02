; -------------------------------------------------------------------------------------
(ns ClojureProject.lit_control
  (:require [ClojureProject.lit_data :as data]
            [clojure.tools.logging :as log]))

; -------------------------------------------------------------------------------------
; DATABASE OPERATIONS

(defn controlConnectDatabase 
  "Opens a connection to a database with the given connection data"
  [connectionData]
  (data/connectDatabase connectionData))

(defn controlDisconnectDatabase 
  "closes the open database connection"
  [] 
  (data/disconnectDatabase))

; -------------------------------------------------------------------------------------
; SEARCH ITEMS

; werte einzeln übergeben und map hier zusammenbauen und dann übergeben...
; defprotocoll defrecord
; table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  

(defn executeSearchTitle 
  "Calls the model method to find a title with the given search conditions in the database"
  [searchTitleMap] 
  (data/selectTitle searchTitleMap))

(defn executeSearchPublisher 
  "Calls the model method to find a publisher with the given search conditions in the database"
  [searchPublisherMap] 
  (data/selectPublisher searchPublisherMap))

(defn executeSearchPublisherById 
  "Calls the model method to find a publisher with the given id in the database"
  [publisherId] 
  (data/selectPublisher {:name "" :id publisherId}))

; -------------------------------------------------------------------------------------
; ADD ITEMS

(defn executeAddTitle 
  "Calls the model method to add a new title to the database"
  [titleMap] 
  (data/insertTitle titleMap))

(defn executeAddPublisher 
  "Calls the model method to add a new publisher to the database"
  [publisherMap] 
  (data/insertPublisher publisherMap))

; -------------------------------------------------------------------------------------
; MODIFY ITEMS

(defn executeModifyTitle 
  "Calls the model method to modify a title in the database"
  [titleMap] 
  (data/updateTitle titleMap))

(defn executeModifyPublisher 
  "Calls the model method to modify a publisher in the database"
  [publisherMap] 
  (data/updatePublisher publisherMap))

; -------------------------------------------------------------------------------------
; DELETE ITEMS

(defn executeDeleteTitle 
  "Calls the model method to delete a title from the database"
  [titleId] 
  (data/deleteTitle titleId))

(defn executeDeletePublisher 
  "Calls the model method to delete a publisher from the database"
  [publisherId] 
  (data/deletePublisher publisherId))
