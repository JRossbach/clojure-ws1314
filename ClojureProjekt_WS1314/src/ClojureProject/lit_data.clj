; -------------------------------------------------------------------------------------
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
; UTIL

(defn getModString [string]
  (str "%" string "%"))

; -------------------------------------------------------------------------------------
; DATABASE + ENTITES

(defdb mySQLDatabase (mysql {:host "localhost"
                              :db "clojureprojekt"
                              :user "root"
                              :password ""})) 

(defentity title
  (database mySQLDatabase) 
  (pk :id) 
  (table :tbl_title)
  (entity-fields :isbn :name :author)
  (belongs-to publisher {:fk :publisher_id}))

(defentity publisher
  (database mySQLDatabase) 
  (pk :id) 
  (table :tbl_publisher)
  (entity-fields :name)
  (has-many title))
  
(log/info "connection opened to database [" mySQLDatabase "]")
(log/info "defined entity title [" title "]")
(log/info "defined entity publisher [" publisher "]")     
  
; -------------------------------------------------------------------------------------
; DATABASE SELECT
; auch auf get nil überprüfen
(defn selectTitle
  "Selects a number of titles from the database"    
  ([] (select title 
          (with publisher)
          (order :name :ASC)))     
  ([conditions] 
    (if (identical? -1 ((conditions :publisher) :id))
        (select title 
                (with publisher)
                (where (and {:isbn [like (getModString (conditions :isbn))]}
                            {:name [like (getModString (conditions :name))]}
                            {:author [like (getModString (conditions :author))]}))
                (order :name :ASC))
        (select title 
                (with publisher)
                (where (and {:isbn [like (getModString (conditions :isbn))]}
                            {:name [like (getModString (conditions :name))]}
                            {:author [like (getModString (conditions :author))]}
                            {:publisher_id [= ((conditions :publisher) :id)]}))
                (order :name :ASC)))))

(defn selectPublisher
  "Selects a number of publishers from the database"  
  ([] (select publisher 
              (order :name :ASC)))  
  ([conditions]     
    (if (identical? -1 (get conditions :id)) 
        (select publisher 
                (where  (and {:name [like (getModString (get conditions :name))]})) 
                (order :name :ASC))
        (select publisher 
                (where  (and {:id [= (get conditions :id)]}
                             {:name [like (getModString (get conditions :name))]})) 
                (order :name :ASC)))))

; -------------------------------------------------------------------------------------
; DATABASE INSERT

(defn insertTitle
  "Inserts a title in the database"
  [newtitle] 
  (transaction
    (insert title (values newtitle))))

(defn insertPublisher
  "Inserts a publisher in the database"
  [newPublisher] 
    (transaction
      (insert publisher (values newPublisher))))

; -------------------------------------------------------------------------------------
; DATABASE UPDATE

(defn updateTitle
  "Updates a title in the database"
  [titleMap]
  (transaction
    (update title 
            (set-fields {:isbn (titleMap :isbn) 
                         :name (titleMap :name)
                         :author (titleMap :author)
                         :publisher_id ((titleMap :publisher) :id)})
            (where {:id [= (get titleMap :id)]}))))

(defn updatePublisher
  "Updates a publisher in the database"
  [publisherMap] 
  (transaction
    (update publisher
            (set-fields {:name (publisherMap :name)})                        
            (where {:id [= (publisherMap :id)]}))))

; -------------------------------------------------------------------------------------
; DATABASE DELETE

(defn deleteTitle
  "Deletes a title in the database"
  [titleId] 
  (transaction
    (delete title (where {:id [= titleId]}))))

(defn deletePublisher
  "Deletes a publisher in the database"
  [publisherId] 
  (transaction
    (delete publisher (where {:id [= publisherId]}))))


