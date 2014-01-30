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
                    (log/info "connection opened to database [" mySQLDatabase "]")
                    
                    (defentity title
                      (database mySQLDatabase) 
                      (pk :id) 
                      (table :tbl_title)
                      (entity-fields :isbn :name :author :publisher_id)
                      (belongs-to publisher {:fk :publisher_id}))
                    (log/info "defined entity title [" title "]")

                    (defentity publisher
                      (database mySQLDatabase) 
                      (pk :id) 
                      (table :tbl_publisher)
                      (entity-fields :name))
                    (log/info "defined entity publisher [" publisher "]")))               

(defn disconnectDatabase
  "Closes the connection to the actual defined database."
  [] 
  (log/debug "close database connection")
  (-> (connection-pool (get-connection mySQLDatabase)):datasource .close))

; -------------------------------------------------------------------------------------
; DATABASE SELECT

(defn selectTitle
  "Selects a number of titles from the database"    
  ([] (let [result (select title (order :name :ASC))]
        (log/debug "select all title result [" result "]") result))    
  ([conditions] (let [result (select title 
                                     (where (and {:isbn [like (get conditions :isbn)]}
                                                 {:name [like (get conditions :name)]}
                                                 ;{:publisher_id [like (get conditions :publisher_id)]}
                                                 {:author [like (get conditions :author)]}))
                                     (order :name :ASC))]
                (log/debug "select title conditions [" conditions "]")
                (log/debug "select title result [" result "]") result)))

(defn selectPublisher
  "Selects a number of publishers from the database"  
  ([] (let [result (select publisher (order :name :ASC))]
         (log/debug "select all publisher result [" result "]")))    
  ([conditions] (let [result (select publisher 
                                     (where {:name [like (get conditions :name)]}) 
                                     (order :name :ASC))]
                (log/debug "select publisher conditions [" conditions "]")
                (log/debug "select publisher result [" result "]"))))

; -------------------------------------------------------------------------------------
; DATABASE INSERT

(defn insertTitle
  "Inserts a title in the database"
  ([newtitle] 
    (log/debug "insert title [" newtitle "] into database")
    (insert title (values newtitle))))

(defn insertPublisher
  "Inserts a publisher in the database"
  ([newPublisher] 
    (log/debug "insert publisher [" newPublisher "] into database")
    (insert publisher (values newPublisher))))

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
  ([titleId] 
    (log/debug "delete title with id [" titleId "]")
    (delete title (where {:id [= titleId]}))))

(defn deletePublisher
  "Deletes a publisher in the database"
  ([publisherId] 
    (log/debug "delete publisher with id [" publisherId "]")
    (delete publisher (where {:id [= publisherId]}))))
