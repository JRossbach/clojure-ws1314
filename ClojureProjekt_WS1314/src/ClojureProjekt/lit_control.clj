; -------------------------------------------------------------------------------------
(ns ClojureProject.lit_control)

; -------------------------------------------------------------------------------------

(defn saveConfiguration []  
  (println [(config field_database_host :text)
          (config field_database_name :text)
          (config field_database_username :text)
          (config field_database_password :text)]))

(defn executeSearch []
  (println [(config field_smd_titel :text)
          (config field_smd_autor :text)
          (config field_smd_verlag :text)]))

(defn executeAdd []
  (println [(config field_add_titel :text)
          (config field_add_autor :text)
          (config field_add_verlag :text)]))