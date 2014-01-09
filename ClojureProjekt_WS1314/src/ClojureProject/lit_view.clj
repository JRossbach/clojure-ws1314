;-------------------------------------------------------------------------------------------------------------------------------
;(ns ClojureProject.lit_view
; (:require [seesaw.core :refer :all]
;           [seesaw.dev :refer :all]
;           [ClojureProject.lit_i18n :refer :all]
;           [ClojureProject.lit_control :refer :all]))
    
(ns ClojureProject.lit_view)

(require '[ClojureProject.lit_i18n :refer :all]
         '[ClojureProject.lit_control :refer :all])

(use '[seesaw.core])
(use '[seesaw.dev])
            
;-------------------------------------------------------------------------------------------------------------------------------
; DECLARETION

(declare database_panel)
(declare field_database_host)
(declare field_database_name)
(declare field_database_username)
(declare field_database_password)

(declare searchTitle_search_panel)
(declare searchTitle_search_table)
(declare searchTitle_panel)
(declare field_searchTitle_name)
(declare field_searchTitle_isbn)
(declare field_searchTitle_author)
(declare field_searchTitle_publisher)

(declare searchPublisher_search_panel)
(declare searchPublisher_search_table)
(declare searchPublisher_panel)
(declare field_searchPublisher_name)

(declare addTitle_panel)
(declare field_addTitle_name)
(declare field_addTitle_isbn)
(declare field_addTitle_author)
(declare field_addTitle_publisher)

(declare addPublisher_panel)
(declare field_addPublisher_name)

(declare frame_menu)
(declare frame_main)

;-------------------------------------------------------------------------------------------------------------------------------
; HANDLER

(defn switch [container]
  (config! frame_main :content container))

(defn viewSaveConfiguration []
  (ClojureProject.lit_control/saveConfiguration))

(defn viewClearSearchTitle []
  (config! field_searchTitle_name :text "")
  (config! field_searchTitle_isbn :text "")
  (config! field_searchTitle_author :text "")
  (config! field_searchTitle_publisher :text ""))

(defn viewClearAddTitle []
  (config! field_addTitle_name :text "")
  (config! field_addTitle_isbn :text "")
  (config! field_addTitle_author :text "")
  (config! field_addTitle_publisher :text ""))

(defn viewExecuteSearchTitle []
  (ClojureProject.lit_control/executeSearchTitle [(config field_searchTitle_name :text)
                                                  (config field_searchTitle_isbn :text)
                                                  (config field_searchTitle_author :text)
                                                  (config field_searchTitle_publisher :text)]))

(defn viewExecuteAddTitle []
  (ClojureProject.lit_control/executeAddTitle [(config field_addTitle_name :text)
                                               (config field_addTitle_isbn :text)
                                               (config field_addTitle_author :text)
                                               (config field_addTitle_publisher :text)]))

(defn viewClearSearchPublisher []
  (config! field_searchPublisher_name :text ""))

(defn viewClearAddPublisher []
  (config! field_addPublisher_name :text ""))

(defn viewExecuteSearchPublisher []
  (ClojureProject.lit_control/executeSearchPublisher [(config field_searchPublisher_name :text)]))

(defn viewExecuteAddPublisher []
  (ClojureProject.lit_control/executeAddPublisher [(config field_addPublisher_name :text)]))


;-------------------------------------------------------------------------------------------------------------------------------
; BUTTON ACTIONS

(def button_database_save_action (action
                                   :handler (fn [e] (viewSaveConfiguration))
                                   :name (ClojureProject.lit_i18n/i18n :text_database_button_save)))

(def button_searchTitle_clear_action (action
                                   :handler (fn [e] (viewClearSearchTitle))
                                   :name (ClojureProject.lit_i18n/i18n :text_searchTitle_button_clear)))

(def button_searchTitle_search_action (action
                                   :handler (fn [e] (viewExecuteSearchTitle))
                                   :name (ClojureProject.lit_i18n/i18n :text_searchTitle_button_search)))

(def button_addTitle_clear_action (action
                                   :handler (fn [e] (viewClearAddTitle))
                                   :name (ClojureProject.lit_i18n/i18n :text_addTitle_button_clear)))

(def button_addTitle_save_action (action
                                   :handler (fn [e] (viewExecuteAddTitle))
                                   :name (ClojureProject.lit_i18n/i18n :text_addTitle_button_save)))

(def button_searchPublisher_clear_action (action
                                   :handler (fn [e] (viewClearSearchPublisher))
                                   :name (ClojureProject.lit_i18n/i18n :text_searchPublisher_button_clear)))

(def button_searchPublisher_search_action (action
                                   :handler (fn [e] (viewExecuteSearchPublisher))
                                   :name (ClojureProject.lit_i18n/i18n :text_searchPublisher_button_search)))

(def button_addPublisher_clear_action (action
                                   :handler (fn [e] (viewClearAddPublisher))
                                   :name (ClojureProject.lit_i18n/i18n :text_addPublisher_button_clear)))

(def button_addPublisher_save_action (action
                                   :handler (fn [e] (viewExecuteAddPublisher))
                                   :name (ClojureProject.lit_i18n/i18n :text_addPublisher_button_save)))

;-------------------------------------------------------------------------------------------------------------------------------
; MENU ACTIONS

(def menubar_database_item_config_action (action
                    :handler (fn [e] (switch database_panel))
                    :name (ClojureProject.lit_i18n/i18n :text_menubar_database_item_config)
                    :tip  (ClojureProject.lit_i18n/i18n :text_menubar_database_item_config_tooltip)))

(def menubar_database_item_connect_action (action
                    :handler (fn [e] (ClojureProject.lit_control/controlConnectDatabase (config field_database_host :text)
                                                                                        (config field_database_name :text)
                                                                                        (config field_database_username :text)
                                                                                        (config field_database_password :text)))
                    :handler (fn [e] ())
                    :name (ClojureProject.lit_i18n/i18n :text_menubar_database_item_connect)
                    :tip  (ClojureProject.lit_i18n/i18n :text_menubar_database_item_connect_tooltip)))

(def menubar_database_item_disconnect_action (action
                    :handler (fn [e] (ClojureProject.lit_control/controlDisconnectDatabase))
                    :name (ClojureProject.lit_i18n/i18n :text_menubar_database_item_disconnect)
                    :tip  (ClojureProject.lit_i18n/i18n :text_menubar_database_item_disconnect_tooltip)))

(def menubar_operations_item_searchTitle_action (action
                    :handler (fn [e] (switch searchTitle_panel))
                    :name (ClojureProject.lit_i18n/i18n :text_menubar_operations_item_searchTitle)
                    :tip  (ClojureProject.lit_i18n/i18n :text_menubar_operations_item_searchTitle_tooltip)))

(def menubar_operations_item_addTitle_action (action
                    :handler (fn [e] (switch addTitle_panel))
                    :name (ClojureProject.lit_i18n/i18n :text_menubar_operations_item_addTitle)
                    :tip  (ClojureProject.lit_i18n/i18n :text_menubar_operations_item_addTitle_tooltip)))

(def menubar_operations_item_searchPublisher_action (action
                    :handler (fn [e] (switch searchPublisher_panel))
                    :name (ClojureProject.lit_i18n/i18n :text_menubar_operations_item_searchPublisher)
                    :tip  (ClojureProject.lit_i18n/i18n :text_menubar_operations_item_searchPublisher_tooltip)))

(def menubar_operations_item_addPublisher_action (action
                    :handler (fn [e] (switch addPublisher_panel))
                    :name (ClojureProject.lit_i18n/i18n :text_menubar_operations_item_addPublisher)
                    :tip  (ClojureProject.lit_i18n/i18n :text_menubar_operations_item_addPublisher_tooltip)))

;-------------------------------------------------------------------------------------------------------------------------------
; MENU

(def frame_menu (menubar :items 
                         [(menu :text (ClojureProject.lit_i18n/i18n :text_menubar_database_main_title) 
                                :items [menubar_database_item_config_action
                                        menubar_database_item_connect_action
                                        menubar_database_item_disconnect_action])
                          (menu :text (ClojureProject.lit_i18n/i18n :text_menubar_operations_main_title)
                                :items [menubar_operations_item_searchTitle_action
                                        menubar_operations_item_addTitle_action
                                        menubar_operations_item_searchPublisher_action
                                        menubar_operations_item_addPublisher_action])]))


;-------------------------------------------------------------------------------------------------------------------------------
; DATABASE CONFIGURATION PANEL

(def field_database_host (text "localhost"))
(def field_database_name (text "clojureprojekt"))
(def field_database_username (text"root"))
(def field_database_password (text ""))

(def database_panel (grid-panel 
                      :border (ClojureProject.lit_i18n/i18n :text_database_border)
                      :columns 1
                      :items [(grid-panel
                                :columns 2
                                :items [(ClojureProject.lit_i18n/i18n :text_database_host) field_database_host
                                        (ClojureProject.lit_i18n/i18n :text_database_name) field_database_name
                                        (ClojureProject.lit_i18n/i18n :text_database_username) field_database_username
                                        (ClojureProject.lit_i18n/i18n :text_database_password) field_database_password])
                              (flow-panel
                                :align :right
                                :items [(button :action button_database_save_action)])]))

;-------------------------------------------------------------------------------------------------------------------------------
; SEARCH TITLE PANEL

(def field_searchTitle_name (text))
(def field_searchTitle_isbn (text))
(def field_searchTitle_author (text))
(def field_searchTitle_publisher (text))

(def searchTitle_search_panel (grid-panel
                                :border (ClojureProject.lit_i18n/i18n :text_searchTitle_border)
                                :columns 1 
                                :items [(grid-panel
                                         :columns 2
                                         :items [(ClojureProject.lit_i18n/i18n :text_searchTitle_name) field_searchTitle_name
                                                 (ClojureProject.lit_i18n/i18n :text_searchTitle_isbn) field_searchTitle_isbn
                                                 (ClojureProject.lit_i18n/i18n :text_searchTitle_author) field_searchTitle_author
                                                 (ClojureProject.lit_i18n/i18n :text_searchTitle_publisher) field_searchTitle_publisher])
                                       (flow-panel
                                         :align :right
                                         :items [(button :action button_searchTitle_clear_action)
                                                 (button :action button_searchTitle_search_action)])]))

(def searchTitle_search_table (scrollable (table 
                                     :model [
                                             :columns [{:key :id, :text "ID"} {:key :name, :text "Name"}] 
                                             :rows [["01" "Alexander Nadler"]
                                                    ["02" "Julian Rossbach"]]])))

(def searchTitle_panel (top-bottom-split searchTitle_search_panel searchTitle_search_table))

;-------------------------------------------------------------------------------------------------------------------------------
; ADD TITLE PANEL

(def field_addTitle_name (text))
(def field_addTitle_isbn (text))
(def field_addTitle_author (text))
(def field_addTitle_publisher (text))

(def addTitle_panel (grid-panel
                      :border (ClojureProject.lit_i18n/i18n :text_addTitle_border)
                      :columns 1 
                      :items [(grid-panel
                                :columns 2
                                :items [(ClojureProject.lit_i18n/i18n :text_addTitle_name) field_addTitle_name
                                        (ClojureProject.lit_i18n/i18n :text_addTitle_isbn) field_addTitle_isbn
                                        (ClojureProject.lit_i18n/i18n :text_addTitle_author) field_addTitle_author
                                        (ClojureProject.lit_i18n/i18n :text_addTitle_publisher) field_addTitle_publisher])
                              (flow-panel
                                :align :right
                                :items [(button :action button_addTitle_clear_action)
                                        (button :action button_addTitle_save_action)])]))

;-------------------------------------------------------------------------------------------------------------------------------
; SEARCH PUBLISHER PANEL

(def field_searchPublisher_name (text))

(def searchPublisher_search_panel (grid-panel
                                :border (ClojureProject.lit_i18n/i18n :text_searchPublisher_border)
                                :columns 1 
                                :items [(grid-panel
                                         :columns 2
                                         :items [(ClojureProject.lit_i18n/i18n :text_searchPublisher_name) field_searchPublisher_name])
                                       (flow-panel
                                         :align :right
                                         :items [(button :action button_searchPublisher_clear_action)
                                                 (button :action button_searchPublisher_search_action)])]))

(def searchPublisher_search_table (scrollable (table 
                                     :model [
                                             :columns [{:key :id, :text "ID"} {:key :name, :text "Name"}] 
                                             :rows [["01" "Alexander Nadler"]
                                                    ["02" "Julian Rossbach"]]])))

(def searchPublisher_panel (top-bottom-split searchPublisher_search_panel searchPublisher_search_table))

;-------------------------------------------------------------------------------------------------------------------------------
; ADD PUBLISHER PANEL

(def field_addPublisher_name (text))

(def addPublisher_panel (grid-panel
                          :border (ClojureProject.lit_i18n/i18n :text_addPublisher_border)
                          :columns 1 
                          :items [(grid-panel
                                    :columns 2
                                    :items [(ClojureProject.lit_i18n/i18n :text_addPublisher_name) field_addPublisher_name])
                                  (flow-panel
                                    :align :right
                                    :items [(button :action button_addPublisher_clear_action)
                                            (button :action button_addPublisher_save_action)])]))

;-------------------------------------------------------------------------------------------------------------------------------
; MAIN FRAME

(def frame_main (frame :title (ClojureProject.lit_i18n/i18n :text_frame_main_title)
                       :size [640 :by 480]
                       :menubar frame_menu
                       :content searchTitle_panel))
                       ;:on-close :exit))

(native!) ; native os ui
(-> frame_main pack! show!)                                                  
