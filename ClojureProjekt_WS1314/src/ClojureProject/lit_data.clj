(ns ClojureProject.lit_data)


(declare title publisher)


(defn connectDB [[db host user pass]]
  (defdb mySQL (mysql {:db db :host host :user user :password pass})))


(defn disconnect)

;(defentity title
 ; (database mySQLDatabase) 
;  (pk :id) 
 ; (table :tbl_title)
 ; (entity-fields :name :isbn :author :publisher_id)
 ; (belongs-to verlag {:fk :publisher_id}))

;(defentity publisher
;  (database mySQLDatabase) 
 ; (pk :id) 
;  (table :tbl_publisher)
 ; (entity-fields :name)) 


;(select autor)
;(select titel)
;(select verlag)

;(select autor
;        (with titel))

;(select titel
 ;       (with autor))

;(select titel
 ;       (with verlag))

;(type (select autor
 ;       (with titel)))

;(-> (select* titel)
 ;   (with autor)
 ;   (where {:vorname "Alexander"})
  ;  (as-sql))

;(-> (select* titel)
   ; (as-sql))