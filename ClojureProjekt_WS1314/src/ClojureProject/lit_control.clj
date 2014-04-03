; -------------------------------------------------------------------------------------
(ns ClojureProject.lit_control
  (:require [clojure.tools.logging :as log]
            [ClojureProject.lit_data :as data]
            [ClojureProject.lit_types :as types]))

; -------------------------------------------------------------------------------------
; SEARCH ITEMS

(defmulti search type)

(defmethod search ::types/title [t] 
  (log/debug "Search title with conditions[" + t + "]")
  (let [result (data/selectTitle t)]
    (with-local-vars [acc, result]
      (var-set acc [])
      (doseq [value result]
        (var-set acc (conj @acc (types/title (value :id)
                                             (value :name)
                                             (value :author)
                                             (value :isbn)
                                             (types/publisher (value :id_2)
                                                              (value :name_2))))))@acc)))

(defmethod search ::types/publisher [p] 
  (log/debug "Search publisher with conditions[" + p + "]")
  (let [result (data/selectPublisher p)]
    (with-local-vars [acc, result]
      (var-set acc [])
      (doseq [value result]
        (var-set acc (conj @acc (types/publisher (value :id)
                                                 (value :name)))))@acc)))

(defmulti searchById type)

(defmethod searchById ::types/title [t] 
  (log/debug "Search title with id[" + (t :id) + "]")
  (let [result (get(data/selectTitleById (t :id)) 0)]
    (types/title (result :id)
                 (result :name)
                 (result :author)
                 (result :isbn)
                 (types/publisher (result :id_2)
                                  (result :name_2)))))

(defmethod searchById ::types/publisher [p] 
  (log/debug "Search publisher with id[" + (p :id) + "]")
  (let [result (get (data/selectPublisherById (p :id)) 0)]
    (types/title (result :id)
                 (result :name))))

; -------------------------------------------------------------------------------------
; ADD ITEMS

(defmulti add type)

(defmethod add ::types/title [t] 
  (log/debug "try to add title [" t "]")
  (data/insertTitle t))

(defmethod add ::types/publisher [p]  
  (log/debug "try to add publisher [" p "]")
  (data/insertPublisher p))

; -------------------------------------------------------------------------------------
; UPDATE ITEMS

(defmulti update type)

(defmethod update ::types/title [t] 
  (log/debug "try to update title [" t "]")
  (data/updateTitle t))

(defmethod update ::types/publisher [p] 
  (log/debug "try to update publisher [" p "]")
  (data/updatePublisher p))

; -------------------------------------------------------------------------------------
; DELETE ITEMS

(defmulti delete type)

(defmethod delete ::types/title [t]
  (log/debug "try to delete title [" t "]")
  (data/deleteTitle (:id t)))

(defmethod delete ::types/publisher [p]
  (log/debug "try to delete publisher [" p "]")
  (data/deletePublisher (:id p)))


