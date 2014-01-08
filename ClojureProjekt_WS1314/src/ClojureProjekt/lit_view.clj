;-------------------------------------------------------------------------------------------------------------------------------
(ns ClojureProjekt.lit_view
  (:require [seesaw.core :refer :all]
            [seesaw.dev :refer :all]
            [ClojureProjekt.lit_control :as control]))

;-------------------------------------------------------------------------------------------------------------------------------
; DECLARETION

(declare database_panel)
(declare field_database_host)
(declare field_database_name)
(declare field_database_username)
(declare field_database_password)

(declare smd_search_panel)
(declare smd_search_table)
(declare smd_panel)
(declare field_smd_titel)
(declare field_smd_autor)
(declare field_smd_verlag)

(declare add_panel)
(declare field_add_titel)
(declare field_add_autor)
(declare field_add_verlag)

(declare frame_menu)
(declare frame_main)

;-------------------------------------------------------------------------------------------------------------------------------
; LABELS

(def text_frame_main_title "Literaturverzeichnis")

; MAP MIT KEYWORDS clojure blancas kern / i18n

; TESTEINTRAG

(def text_menubar_database_main_title "Datenbank")
(def text_menubar_database_item_config "Datenbank konfigurieren")
(def text_menubar_database_item_config_tooltip "Zeigt die aktuelle Konfigurieren der Datenbank-Verbindung an")
(def text_menubar_database_item_connect "Verbindung herstellen")
(def text_menubar_database_item_connect_tooltip "Stellt eine Verbindung zur konfigurierten Datenbank her")
(def text_menubar_database_item_disconnect "Verbindung trennen")
(def text_menubar_database_item_disconnect_tooltip "Trennt die Verbindung zur konfigurierten Datenbank")

(def text_menubar_operations_main_title "Operationen")
(def text_menubar_operations_item_searchmodifydelete "Datensätze suchen / bearbeiten / löschen")
(def text_menubar_operations_item_searchmodifydelete_tooltip "Hier können die Einträge der Datenbank eingesehen, verändert oder gelöscht werden")
(def text_menubar_operations_item_add "Datensätze hinzufügen")
(def text_menubar_operations_item_add_tooltip "Hier können neue Datensätze in die Datenbank eingepflegt werden")

(def text_database_border "Verbindungsdaten")
(def text_database_host "Server")
(def text_database_name "Datenbank")
(def text_database_username "Benutzername")
(def text_database_password "Passwort")
(def text_database_button_save "Speichern")

(def text_smd_border "Datensätze suchen / bearbeiten / löschen")
(def text_smd_titel "Titel")
(def text_smd_autor "Autor")
(def text_smd_verlag "Verlag")
(def text_smd_button_clear "Zurücksetzen")
(def text_smd_button_search "Suchen")

(def text_add_border "Datensätze hinzufügen")
(def text_add_titel "Titel")
(def text_add_autor "Autor")
(def text_add_verlag "Verlag")
(def text_add_button_clear "Zurücksetzen")
(def text_add_button_save "Speichern")

;-------------------------------------------------------------------------------------------------------------------------------
; HANDLER

(defn switch [container]
  (config! frame_main :content container))

(defn clearSearch []
  (config! field_smd_titel :text "")
  (config! field_smd_autor :text "")
  (config! field_smd_verlag :text ""))

(defn clearAdd []
  (config! field_add_titel :text "")
  (config! field_add_autor :text "")
  (config! field_add_verlag :text ""))

;-------------------------------------------------------------------------------------------------------------------------------
; BUTTON ACTIONS

(def button_database_save_action (action
                                   :handler (fn [e] (control/saveConfiguration))
                                   :name text_database_button_save))

(def button_smd_clear_action (action
                                   :handler (fn [e] (clearSearch))
                                   :name text_smd_button_clear))

(def button_smd_search_action (action
                                   :handler (fn [e] (control/executeSearch))
                                   :name text_smd_button_search))

(def button_add_clear_action (action
                                   :handler (fn [e] (clearAdd))
                                   :name text_add_button_clear))

(def button_add_save_action (action
                                   :handler (fn [e] (control/executeAdd))
                                   :name text_add_button_save))

;-------------------------------------------------------------------------------------------------------------------------------
; MENU ACTIONS

(def menubar_database_item_config_action (action
                    :handler (fn [e] (switch database_panel))
                    :name text_menubar_database_item_config
                    :tip  text_menubar_database_item_config_tooltip))

(def menubar_database_item_connect_action (action
                    :handler (fn [e] (alert "Datenbank-Verbindung herstellen"))
                    :name text_menubar_database_item_connect
                    :tip  text_menubar_database_item_connect_tooltip))

(def menubar_database_item_disconnect_action (action
                    :handler (fn [e] (alert "Datenbank-Verbindung trennen"))
                    :name text_menubar_database_item_disconnect
                    :tip  text_menubar_database_item_disconnect_tooltip))

(def menubar_operations_item_searchmodify_action (action
                    :handler (fn [e] (switch smd_panel))
                    :name text_menubar_operations_item_searchmodifydelete
                    :tip  text_menubar_operations_item_searchmodifydelete_tooltip))

(def menubar_operations_item_add_action (action
                    :handler (fn [e] (switch add_panel))
                    :name text_menubar_operations_item_add
                    :tip  text_menubar_operations_item_add_tooltip))

;-------------------------------------------------------------------------------------------------------------------------------
; MENU

(def frame_menu (menubar :items 
                        [(menu :text text_menubar_database_main_title 
                                     :items [menubar_database_item_config_action
                                             menubar_database_item_connect_action
                                             menubar_database_item_disconnect_action])
                               (menu :text text_menubar_operations_main_title
                                     :items [menubar_operations_item_searchmodify_action
                                             menubar_operations_item_add_action])]))

;-------------------------------------------------------------------------------------------------------------------------------
; DATABASE CONFIGURATION PANEL

(def field_database_host (text))
(def field_database_name (text))
(def field_database_username (text))
(def field_database_password (text))

(def database_panel (grid-panel 
                      :border text_database_border
                      :columns 1
                      :items [(grid-panel
                                :columns 2
                                :items [text_database_host field_database_host
                                        text_database_name field_database_name
                                        text_database_username field_database_username
                                        text_database_password field_database_password])
                              (flow-panel
                                :align :right
                                :items [(button :action button_database_save_action)])]))

;-------------------------------------------------------------------------------------------------------------------------------
; SEARCH MODIFY DELETE PANEL

(def field_smd_titel (text))
(def field_smd_autor (text))
(def field_smd_verlag (text))

(def smd_search_panel (grid-panel
                                :border text_smd_border
                                :columns 1 
                                :items [(grid-panel
                                         :columns 2
                                         :items [text_smd_titel field_smd_titel
                                                 text_smd_autor field_smd_autor
                                                 text_smd_verlag field_smd_verlag])
                                       (flow-panel
                                         :align :right
                                         :items [(button :action button_smd_clear_action)
                                                 (button :action button_smd_search_action)])]))

(def smd_search_table (scrollable (table 
                                     :model [
                                             :columns [{:key :id, :text "ID"} {:key :name, :text "Name"}] 
                                             :rows [["01" "Alexander Nadler"]
                                                    ["02" "Julian Rossbach"]]])))

(def smd_panel (top-bottom-split smd_search_panel smd_search_table))

;-------------------------------------------------------------------------------------------------------------------------------
; ADD PANEL

(def field_add_titel (text))
(def field_add_autor (text))
(def field_add_verlag (text))

(def add_panel (grid-panel
                 :border text_add_border
                 :columns 1 
                 :items [(grid-panel
                          :columns 2
                          :items [text_add_titel field_add_titel
                                  text_add_autor field_add_autor
                                  text_add_verlag field_add_verlag])
                        (flow-panel
                          :align :right
                          :items [(button :action button_add_clear_action)
                                  (button :action button_add_save_action)])]))

;-------------------------------------------------------------------------------------------------------------------------------
; MAIN FRAME

(def frame_main (frame :title text_frame_main_title
                       :size [640 :by 480]
                       :menubar frame_menu
                       :content smd_panel))
                       ;:on-close :exit))

(native!) ; native os ui
(-> frame_main pack! show!)                                                  
