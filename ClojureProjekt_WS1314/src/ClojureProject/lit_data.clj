;This class is responsible for den database connection and CRUD-methods----------------
(ns ClojureProject.lit_data
  (:require [korma.core :refer :all]
            [korma.db :refer :all]
            [clojure.string :as str] 
            [clojure.tools.logging :as log]))

; -------------------------------------------------------------------------------------
; DECLARETIONS

(declare mySQLDatabase)
(declare title)
(declare publisher)

(declare connectDatabase)
(declare disconnectDatabase)

(declare selectTitle)
(declare selectPublisher)

(declare insertTitle)
(declare insertPublisher)

(declare updateTitle)
(declare updatePublisher)

(declare deleteTitle)
(declare deletePublisher)

(declare checkTitle)
(declare checkPublisher)

; -------------------------------------------------------------------------------------
; DATABASE

(defn connectDatabase 
  "Opens the connection to a database with the given connection data."
  [connectionData] 
  
  ; database
  (defdb mySQLDatabase (mysql connectionData)) 
  (log/info "connection opened to database [" mySQLDatabase "]")
                  
  ; entity title  
  (defentity title
    (database mySQLDatabase) 
    (pk :id) 
    (table :tbl_title)
    (entity-fields :isbn :name :author :publisher_id)
    (belongs-to publisher {:fk :publisher_id}))
  (log/info "defined entity title [" title "]")

  ; entity publisher
  (defentity publisher
    (database mySQLDatabase) 
    (pk :id) 
    (table :tbl_publisher)
    (entity-fields :name))
  (log/info "defined entity publisher [" publisher "]"))     

(defn disconnectDatabase
  "Closes the connection to the actual defined database."
  [] 
  (log/debug "close database connection")
  (.close ((get-connection mySQLDatabase) :datasource) true))

; -------------------------------------------------------------------------------------
; DATABASE SELECT

(defn selectTitle
  "Selects a number of titles from the database"    
  ([] (let [result (select title (order :name :ASC))]
        (log/debug "select all title result [" result "]") 
        result))     
  ([conditions] 
    (if (identical? -1 (get conditions :publisher_id)) 
      (let [result (select title 
                           (where (and {:isbn [like (str "%" (get conditions :isbn) "%")]}
                                       {:name [like (str "%" (get conditions :name) "%")]}
                                       {:author [like (str "%" (get conditions :author) "%")]}))
                           (order :name :ASC))]
        (log/debug "select title conditions [" conditions "]")
        (log/debug "select title result [" result "]") 
        result)
      (let [result (select title 
                           (where (and {:isbn [like (str "%" (get conditions :isbn) "%")]}
                                       {:name [like (str "%" (get conditions :name) "%")]}
                                       {:author [like (str "%" (get conditions :author) "%")]}
                                       {:publisher_id [= (get conditions :publisher_id)]}))
                           (order :name :ASC))]
        (log/debug "select title conditions [" conditions "]")
        (log/debug "select title result [" result "]") 
        result))))

(defn selectPublisher
  "Selects a number of publishers from the database"  
  ([] (let [result (select publisher (order :name :ASC))]
        (log/debug "select all publisher result [" result "]")
        result))    
  ([conditions]     
    (if (nil? (get conditions :id)) 
     (let [result (select publisher 
                       (where  (and {:name [like (str "%" (get conditions :name) "%")]})) 
                       (order :name :ASC))]
                (log/debug "select publisher conditions [" conditions "]")
                (log/debug "select publisher result [" result "]")
     result)
     (let [result (select publisher 
                         (where  (and {:id [= (get conditions :id)]}
                                      {:name [like (str "%" (get conditions :name) "%")]})) 
                         (order :name :ASC))]
                  (log/debug "select publisher conditions [" conditions "]")
                  (log/debug "select publisher result [" result "]")
      result))))

; -------------------------------------------------------------------------------------
; DATABASE INSERT

(defn insertTitle
  "Inserts a title in the database"
  [newtitle] 
    (log/debug "insert title [" newtitle "]")
    (insert title (values newtitle)))

(defn insertPublisher
  "Inserts a publisher in the database"
  [newPublisher] 
    (log/debug "insert publisher [" newPublisher "]")
    (insert publisher (values newPublisher)))

; -------------------------------------------------------------------------------------
; DATABASE UPDATE

(defn updateTitle
  "Updates a title in the database"
  [titleMap] 
  (log/debug "update title [" titleMap "]")
  (update title 
          (set-fields {:isbn (get titleMap :isbn) 
                       :name (get titleMap :name)
                       :author (get titleMap :author)
                       :publisher_id (get titleMap :publisher_id)})
          (where {:id [= (get titleMap :id)]})))

(defn updatePublisher
  "Updates a publisher in the database"
  [publisherMap] 
  (log/debug "update publisher [" publisherMap "]")
  (update publisher
          (set-fields {:name (get publisherMap :name)})                        
          (where {:id [= (get publisherMap :id)]})))

; -------------------------------------------------------------------------------------
; DATABASE DELETE

(defn deleteTitle
  "Deletes a title in the database"
  [titleId] 
    (log/debug "delete title with id [" titleId "]")
    (delete title (where {:id [= titleId]})))

(defn deletePublisher
  "Deletes a publisher in the database"
  [publisherId] 
    (log/debug "delete publisher with id [" publisherId "]")
    (if (checkPublisher publisherId) 
      (delete publisher (where {:id [= publisherId]}))
      (log/debug "connot delete")))

; -------------------------------------------------------------------------------------
; UTIL FUNCTIONS

(defn checkPublisher
  "Checks if the publisher you want to delete is linked by a title"
  [publisherId]
  ;(if (> (count (selectPublisher {:publisher_id [= publisherId]})) 0) (boolean false) (boolean true)))
  (if (> (count (selectPublisher {:id (str publisherId)})) 0) (boolean false) (boolean true)))

