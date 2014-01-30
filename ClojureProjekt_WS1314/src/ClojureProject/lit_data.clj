;This class is responsible for den database connection and CRUD-methods----------------
(ns ClojureProject.lit_data
  (:require [korma.core :refer :all]
            [korma.db :refer :all]
            [clojure.string :as str] 
            [clojure.tools.logging :as log]))

; -------------------------------------------------------------------------------------
; DECLARETIONS

; declare entities
(declare title)
(declare publisher)

; declare database
(declare mySQLDatabase)

; -------------------------------------------------------------------------------------
; DATABASE

(defn connectDatabase 
  "Opens the connection to a database with the given connection data."
  ([connectionData] (defdb mySQLDatabase (mysql connectionData)) 
                    
                    (defentity title
                      (database mySQLDatabase) 
                      (pk :id) 
                      (table :tbl_title)
                      (entity-fields :isbn :name :author :publisher_id)
                      (belongs-to publisher {:fk :publisher_id}))

                    (defentity publisher
                      (database mySQLDatabase) 
                      (pk :id) 
                      (table :tbl_publisher)
                      (entity-fields :name))))               

(defn disconnectDatabase
  "Closes the connection to the actual defined database."
  [] 
  (-> (connection-pool (get-connection mySQLDatabase)):datasource .close))

; -------------------------------------------------------------------------------------
; DATABASE SELECT

(defn selectTitle
  "Selects a number of titles from the database"    
  ([] (select title (order :name :ASC)))  
  ([conditions] (select title 
                        (where (and {:isbn [like (get conditions :isbn)]}
                                    {:name [like (get conditions :name)]}
                                    ;{:publisher_id [like (get conditions :publisher_id)]}
                                    {:author [like (get conditions :author)]}))
                        (order :name :ASC))))

(defn selectPublisher
  "Selects a number of publishers from the database"  
  ([] (select publisher (order :name :ASC)))  
  ([conditions] (select publisher 
                        (where {:name [like (get conditions :name)]}) 
                        (order :name :ASC))))

; -------------------------------------------------------------------------------------
; DATABASE INSERT

(defn insertTitle
  "Inserts a title in the database"
  [newtitle] (insert title (values newtitle)))

(defn insertPublisher
  "Inserts a publisher in the database"
  [newPublisher] (insert publisher (values newPublisher)))

; -------------------------------------------------------------------------------------
; DATABASE UPDATE

(defn updateTitle
  "Updates a title in the database"
  [titleMap] (update title 
                     (set-fields {:isbn (get titleMap :isbn) 
                                  :name (get titleMap :name)
                                  :author (get titleMap :author)
                                  :publisher_id (get titleMap :publisher_id)})
                     (where {:id [= (get titleMap :id)]})))

(defn updatePublisher
  "Updates a publisher in the database"
  [publisherMap] (update publisher
                         (set-fields {:name (get publisherMap :name)})                        
                         (where {:id [= (get publisherMap :id)]})))

; -------------------------------------------------------------------------------------
; DATABASE DELETE

(defn deleteTitle
  "Deletes a title in the database"
  [titleId] (delete title 
                    (where {:id [= titleId]})))

(defn deletePublisher
  "Deletes a publisher in the database"
  [publisherId] (delete publisher 
                        (where {:id [= publisherId]})))
