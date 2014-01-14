;-------------------------------------------------------------------------------------------------------------------------------
(ns ClojureProject.lit_i18n)

;-------------------------------------------------------------------------------------------------------------------------------
; NATIONALIZATION

(def ^:private default
  {:text_frame_main_title "Literaturverzeichnis"   
   :text_menubar_database_main_title "Datenbank"
   :text_menubar_database_item_config "Datenbank konfigurieren"
   :text_menubar_database_item_config_tooltip "Zeigt die aktuelle Konfigurieren der Datenbank-Verbindung an"
   :text_menubar_database_item_connect "Verbindung herstellen"
   :text_menubar_database_item_connect_tooltip "Stellt eine Verbindung zur konfigurierten Datenbank her"
   :text_menubar_database_item_disconnect "Verbindung trennen"
   :text_menubar_database_item_disconnect_tooltip "Trennt die Verbindung zur konfigurierten Datenbank"
					
   :text_menubar_operations_main_title "Operationen"
   :text_menubar_operations_item_searchTitle "Literatur suchen"
   :text_menubar_operations_item_searchTitle_tooltip "Hier kann nach gespeicherter Literatur gesucht werden"
   :text_menubar_operations_item_addTitle "Literatur hinzufügen"
   :text_menubar_operations_item_addTitle_tooltip "Hier kann neue Literatur hinzugefügt werden"
   :text_menubar_operations_item_searchPublisher "Verlag suchen"
   :text_menubar_operations_item_searchPublisher_tooltip "Hier kann nach gespeicherten Verlagen gesucht werden"
   :text_menubar_operations_item_addPublisher "Verlag hinzufügen"
   :text_menubar_operations_item_addPublisher_tooltip "Hier können neue Verlage hinzugefügt werden"
					
   :text_database_border "Verbindungsdaten"
   :text_database_host "Server"
   :text_database_name "Datenbank"
   :text_database_username "Benutzername"
   :text_database_password "Passwort"
   :text_database_button_save "Speichern"
					
   :text_searchTitle_border "Literatur suchen"
   :text_searchTitle_name "Name"
   :text_searchTitle_isbn "ISBN"
   :text_searchTitle_author "Autor"
   :text_searchTitle_publisher "Verlag"
   :text_searchTitle_button_clear "Zurücksetzen"
   :text_searchTitle_button_search "Suchen"
   
   :text_searchPublisher_border "Verlag suchen"
   :text_searchPublisher_name "Name"
   :text_searchPublisher_button_clear "Zurücksetzen"
   :text_searchPublisher_button_search "Suchen"
					
   :text_addTitle_border "Literatur hinzufügen"
   :text_addTitle_name "Name"
   :text_addTitle_isbn "ISBN"
   :text_addTitle_author "Autor"
   :text_addTitle_publisher "Verlag"
   :text_addTitle_button_clear "Zurücksetzen"
   :text_addTitle_button_save "Speichern"
   
   :text_addPublisher_border "Verlag hinzufügen"
   :text_addPublisher_name "Name"
   :text_addPublisher_button_clear "Zurücksetzen"
   :text_addPublisher_button_save "Speichern"
   
   :text_modifyTitle_frame_title "Literatur bearbeiten"
   :text_modifyTitle_name "Name"
   :text_modifyTitle_isbn "ISBN"
   :text_modifyTitle_author "Autor"
   :text_modifyTitle_publisher "Verlag"
   :text_modifyTitle_button_save "Speichern"
   
   :text_modifyPublisher_frame_title "Verlag bearbeiten"
   :text_modifyPublisher_name "Name"
   :text_modifypublisher_button_save "Speichern"
   
   :text_deleteTitle_button ""
   :text_deletePublisher_button ""})

(def ^:private text (atom default))

(defn i18n-merge
  "Merges m into the text map for customization."
  [m] (swap! text merge m))

(defn i18n
  "Gets or sets the value for the supplied key."
  ([k] (k (deref text)))
  ([k v] (swap! text assoc k v)))

(defn fmt
  "Formats a string with a key and more arguments."
  [k & more]
  (apply format (i18n k) more))

(defn di18n
  "Returns a Delay instance with the value for the supplied key.
Useful in (def)'ed expressions that evaluate too soon."
  [k] (delay (k (deref text))))

(defn dfmt
  "Returns a Delay instance with a string formatted with a key and more
arguments. Useful in (def)'ed expressions that evaluate too soon."
  [k & more]
  (delay (apply format (i18n k) more)))
