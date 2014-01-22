; -------------------------------------------------------------------------------------
(ns ClojureProject.lit_control
  (:require [ClojureProject.lit_data :as data]
            [clojure.tools.logging :as log]))

; -------------------------------------------------------------------------------------
; DATABASE OPERATIONS

(defn saveConfiguration
  ""
  [] ())

(defn controlConnectDatabase 
  "opens a connection to a database with the given connection data"
  [host db user password]
  (data/connectDatabase {:host host 
                         :db db 
                         :user user 
                         :password password}))

(defn controlDisconnectDatabase 
  "closes the open database connection"
  [] 
  (data/disconnectDatabase))

; -------------------------------------------------------------------------------------
; SEARCH ITEMS

(defn executeSearchTitle 
  ""
  [searchTitleMap] 
  (data/selectTitle {:name (str "%" (get searchTitleMap :name) "%")
                     :isbn (str "%" (get searchTitleMap :isbn) "%")
                     :author (str "%" (get searchTitleMap :author) "%")
                     :publisher_id (str (get searchTitleMap :publisher_id))}))

(defn executeSearchPublisher 
  ""
  [searchPublisherMap] 
  (data/selectPublisher {:name (str "%" (get searchPublisherMap :name) "%")}))

; -------------------------------------------------------------------------------------
; ADD ITEMS

(defn executeAddTitle 
  ""
  [addTitleMap] 
  (data/insertTitle addTitleMap))

(defn executeAddPublisher 
  ""
  [addPublisherMap] 
  (data/insertPublisher addPublisherMap))

; -------------------------------------------------------------------------------------
; MODIFY ITEMS

(defn executeModifyTitle 
  ""
  [titleMap] 
  (data/updateTitle titleMap))

(defn executeModifyPublisher 
  ""
  [publisherMap] 
  (data/updatePublisher publisherMap))

; -------------------------------------------------------------------------------------
; DELETE ITEMS

(defn executeDeleteTitle 
  ""
  [titleId] 
  (data/deleteTitle titleId))

(defn executeDeletePublisher 
  ""
  [publisherId] 
  (data/deletePublisher publisherId))
