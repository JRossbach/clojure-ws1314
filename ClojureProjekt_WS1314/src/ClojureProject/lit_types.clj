(ns ClojureProject.lit_types)

; -------------------------------------------------------------------------------------
; RECORDS

(defn title [id, name, author, isbn, publisher]
  ^{:type ::title} {:id id, :name name, :author author, :isbn isbn, :publisher publisher})

(defn publisher [id, name]
  ^{:type ::publisher} {:id id, :name name})
