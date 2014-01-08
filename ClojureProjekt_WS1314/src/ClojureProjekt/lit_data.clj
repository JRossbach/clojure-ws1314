(ns ClojureProjekt.lit_data)


(defdb mySQLDatabase (mysql {:db "clojureprojekt"
                         :host "localhost" 
                         :user "root" 
                         :password ""}))

(declare autor titel verlag)

(defentity autor
  (database mySQLDatabase) 
  (pk :id) 
  (table :tbl_autor)
  (entity-fields :vorname :nachname :alias :geburtsjahr)
  (many-to-many titel :tbl_autor_titel))

(defentity titel
  (database mySQLDatabase) 
  (pk :id) 
  (table :tbl_titel)
  (entity-fields :name :isbn :erscheinungsjahr :auflage :verlagId)
  (many-to-many autor :tbl_autor_titel)
  (belongs-to verlag {:fk :verlag_id})) 

(defentity verlag
  (database mySQLDatabase) 
  (pk :id) 
  (table :tbl_verlag)
  (entity-fields :name)) 

(select autor)
(select titel)
(select verlag)

(select autor
        (with titel))

(select titel
        (with autor))

(select titel
        (with verlag))

(type (select autor
        (with titel)))

(-> (select* titel)
    (with autor)
    (where {:vorname "Alexander"})
    (as-sql))

(-> (select* titel)
    (as-sql))