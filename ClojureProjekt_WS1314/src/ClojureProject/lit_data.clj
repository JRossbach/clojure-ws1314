;This class is responsible for den database connection and CRUD-methods----------------
(ns ClojureProject.lit_data)

(use 'korma.core)
(use 'korma.db)
(require '[clojure.string :as str])

; -------------------------------------------------------------------------------------
; DECLARETIONS

; declare entities
(declare title publisher)

; declare database
(declare mySQLDatabase)

; -------------------------------------------------------------------------------------
; DATABASE

(defn connectDatabase 
  "Opens the connection to a database with the given connection data."
  [host db user password] (defdb mySQLDatabase (mysql {:host host
                                                       :db db  
                                                       :user user 
                                                       :password password})) 
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
                             (entity-fields :name)))                    

(defn disconnectDatabase
  "Closes the connection to the actual defined database."
  [] ())

; -------------------------------------------------------------------------------------
; DATABASE SELECT

(defn selectTitle
  "Selects a number of titles from the database"  
  ([] (select title))
  ([conditions] (select title (where conditions))))

(defn selectPublisher
  "Selects a number of publishers from the database"
  ([] (select publisher))
  ([conditions] (select publisher (where conditions))))

; -------------------------------------------------------------------------------------
; DATABASE INSERT

(defn insertTitle
  "Inserts a title in the database"
  [title] (insert title (values {:isbn (:isbn title)
                                 :name (:name title)
                                 :author (:author title)
                                 :publisher_id (:publisher_id title)})))

(defn insertPublisher
  "Inserts a publisher in the database"
  [publisher] (insert publisher (values {:name (:name publisher)})))

; -------------------------------------------------------------------------------------
; DATABASE UPDATE

(defn updateTitle
  "Updates a title in the database"
  [title] (update title 
                  (set-fields {:isbn (:isbn title) 
                               :name (:name title)
                               :author (:author title)
                               :publisher_id (:publisher_id title)})
                  (where {:id [= (:id title)]})))

(defn updatePublisher
  "Updates a publisher in the database"
  [publisher] (update publisher
                      (set-fields {:name (:name publisher)})                        
                      (where {:id [= (:id publisher)]})))

; -------------------------------------------------------------------------------------
; DATABASE DELETE

(defn deleteTitle
  "Deletes a title in the database"
  [title] (delete title (where {:id [= (:id title)]})))

(defn deletePublisher
  "Deletes a publisher in the database"
  [publisher] (delete publisher (where {:id [= (:id publisher)]})))
