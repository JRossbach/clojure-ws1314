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
                             (entity-fields :name))
                           )
  
  
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
  [title] (
            (println "insert title")
            
            ;TODO: map values to object attributes
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

(defn insertPublisher
  "Inserts a publisher in the database"
  [publisher] (
                (println "insert publisher")
                
                ;TODO: map "name" to object attribute
                (insert publisher (values {
                                           :name "name"
                                          }
                                     )
                   )
            )
  
  )

; -------------------------------------------------------------------------------------
; DATABASE UPDATE

(defn updateTitle
  "Updates a title in the database"
  [title] (
               (update title
                       ;TODO: map attributes to object attribute
                       (set-fields {
                                    :isbn "isbn" 
                                    :name "name"
                                    :author "author"
                                    :publisher_id "publisher"}
                                   )
                       (where {:id [= "id"]})
                )
               
               (println "modify title")
         )
  )

(defn updatePublisher
  "Updates a publisher in the database"
  [publisher](
               (update publisher
                       ;TODO: map "id", "name" to object attribute
                       (set-fields {:name "name"})
                       (where {:id [= "id"]})
                       )

                (println "modify publisher")
  )
  )

; -------------------------------------------------------------------------------------
; DATABASE DELETE

(defn deleteTitle
  "Deletes a title in the database"
  [title] (
            (println "delete title")
            
            ;TODO: map "isbn" to object attribute
            (delete title (where {:isbn [="isbn"]}))
            
            )
            
            
  
  )

(defn deletePublisher
  "Deletes a publisher in the database"
  [publisher] (
                (println "delete publisher")
               
                ;TODO: map "name" to object attribute
                (delete publisher (where {:name [="name"]}))
                
                )
  
  )
