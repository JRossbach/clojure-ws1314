(ns ClojureProject.lit_data)


;(defdb mySQLDatabase (mysql {:db "clojureprojekt"
 ;                        :host "localhost" 
  ;                       :user "root" 
   ;                      :password ""}))

(declare title publisher)

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