; -------------------------------------------------------------------------------------
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
  ([host db user password] (defdb mySQLDatabase (mysql {:host host
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
  
  
  )                         

(defn disconnectDatabase
  "Closes the connection to the actual defined database."
  [] ()
  
  )

; -------------------------------------------------------------------------------------
; DATABASE SELECT

(defn selectTitle
  "Selects a number of titles from the database"  
  ([] (select title))
  ([conditions] (select title (where conditions)))
  
  )

(defn selectPublisher
  "Selects a number of publishers from the database"
  ([] (select publisher))
  ([conditions] (select publisher (where conditions)))
  
  )

; -------------------------------------------------------------------------------------
; DATABASE INSERT

(defn insertTitle
  "Inserts a title in the database"
  [title] (println "insert title")
  
  )

(defn insertPublisher
  "Inserts a publisher in the database"
  [publisher] (println "insert publisher")
  
  )

; -------------------------------------------------------------------------------------
; DATABASE UPDATE

(defn updateTitle
  "Updates a title in the database"
  [title] (
            (println "modify title")
            (insert title (values {
                                   :isbn "isbn" 
                                   :name "name"
                                   :author "author"
                                   :publisher_id "publisher"
                                   }
                                  )
                    )
            )
  
  )

(defn updatePublisher
  "Updates a publisher in the database"
  [publisher] (
                (println "modify publisher")
                 (insert publisher (values {
                                            :name "name"
                                            }
                                      )
                    )
            )
  
  )

; -------------------------------------------------------------------------------------
; DATABASE DELETE

(defn deleteTitle
  "Deletes a title in the database"
  [title] (println "delete title")
  
  )

(defn deletePublisher
  "Deletes a publisher in the database"
  [publisher] (println "delete publisher")
  
  )
