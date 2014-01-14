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

(use '[seesaw.table])

(use '[seesaw.dev])
            
;-------------------------------------------------------------------------------------------------------------------------------
; DECLARETION

(declare database_panel)
(declare field_database_host)
(declare field_database_name)
(declare field_database_username)
(declare field_database_password)

(declare searchTitle_search_panel)
(declare searchTitle_result_panel)
(declare searchTitle_search_table)
(declare searchTitle_panel)
(declare field_searchTitle_name)
(declare field_searchTitle_isbn)
(declare field_searchTitle_author)
(declare field_searchTitle_publisher)

(declare searchPublisher_search_panel)
(declare searchPublisher_result_panel)
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

(declare modifyTitle_panel)
(declare field_modifyTitle_name)
(declare field_modifyTitle_isbn)
(declare field_modifyTitle_author)
(declare field_modifyTitle_publisher)

(declare modifyPublisher_panel)
(declare field_modifyPublisher_name)

(declare frame_modifyTitle)
(declare frame_modifyPublisher)

(declare frame_menu)
(declare frame_main)

;-------------------------------------------------------------------------------------------------------------------------------
; HANDLER

(defn switch [container]
  (config! frame_main :content container))

(defn handleSaveConfiguration []
  (ClojureProject.lit_control/saveConfiguration))

(defn handleConnectDatabase []
  (ClojureProject.lit_control/controlConnectDatabase (config field_database_host :text)
                                                     (config field_database_name :text)
                                                     (config field_database_username :text)
                                                     (config field_database_password :text)))

(defn handleDisconnectDatabase []
 (ClojureProject.lit_control/controlDisconnectDatabase))

(defn handleClearSearchTitle []
  (config! field_searchTitle_name :text "")
  (config! field_searchTitle_isbn :text "")
  (config! field_searchTitle_author :text "")
  (config! field_searchTitle_publisher :text ""))

(defn handleClearAddTitle []
  (config! field_addTitle_name :text "")
  (config! field_addTitle_isbn :text "")
  (config! field_addTitle_author :text "")
  (config! field_addTitle_publisher :text ""))

(defn handleExecuteAddTitle []
  (ClojureProject.lit_control/executeAddTitle [:name (str (config field_addTitle_name :text))
                                               :isbn (str (config field_addTitle_isbn :text))
                                               :author (str (config field_addTitle_author :text))
                                               :publisher_id (str (config field_addTitle_publisher :text))]))

(defn handleExecuteSearchTitle []
  (ClojureProject.lit_control/executeSearchTitle {:name (str (config field_searchTitle_name :text))
                                                  :isbn (str (config field_searchTitle_isbn :text))
                                                  :author (str (config field_searchTitle_author :text))
                                                  :publisher_id (str (config field_searchTitle_publisher :text))}))

(defn handleClearSearchPublisher []
  (config! field_searchPublisher_name :text ""))

(defn handleClearAddPublisher []
  (config! field_addPublisher_name :text ""))

(defn handleExecuteAddPublisher []
  (ClojureProject.lit_control/executeAddPublisher [(config field_addPublisher_name :text)]))

(defn handleExecuteSearchPublisher []
  (ClojureProject.lit_control/executeSearchPublisher [(config field_searchPublisher_name :text)]))

(defn handleModifyTitle [] (println (value-at searchTitle_search_table (selection searchTitle_search_table))))

(defn handleModifyPublisher [] (println (value-at searchPublisher_search_table (selection searchPublisher_search_table))))

(defn handleExecuteModifyTitle [] ())

(defn handleExecuteModifyPublisher [] ())

(defn handleExecuteDeleteTitle [] ())

(defn handleExecuteDeletePublisher [] ())

;-------------------------------------------------------------------------------------------------------------------------------
; BUTTON ACTIONS

(def button_database_save_action (action
                                   :handler (fn [e] (handleSaveConfiguration))
                                   :name (ClojureProject.lit_i18n/i18n :text_database_button_save)))

(def button_searchTitle_clear_action (action
                                   :handler (fn [e] (handleClearSearchTitle))
                                   :name (ClojureProject.lit_i18n/i18n :text_searchTitle_button_clear)))

(def button_searchTitle_search_action (action
                                   :handler (fn [e] (handleExecuteSearchTitle))
                                   :name (ClojureProject.lit_i18n/i18n :text_searchTitle_button_search)))

(def button_addTitle_clear_action (action
                                   :handler (fn [e] (handleClearAddTitle))
                                   :name (ClojureProject.lit_i18n/i18n :text_addTitle_button_clear)))

(def button_addTitle_save_action (action
                                   :handler (fn [e] (handleExecuteAddTitle))
                                   :name (ClojureProject.lit_i18n/i18n :text_addTitle_button_save)))

(def button_searchPublisher_clear_action (action
                                   :handler (fn [e] (handleClearSearchPublisher))
                                   :name (ClojureProject.lit_i18n/i18n :text_searchPublisher_button_clear)))

(def button_searchPublisher_search_action (action
                                   :handler (fn [e] (handleExecuteSearchPublisher))
                                   :name (ClojureProject.lit_i18n/i18n :text_searchPublisher_button_search)))

(def button_addPublisher_clear_action (action
                                   :handler (fn [e] (handleClearAddPublisher))
                                   :name (ClojureProject.lit_i18n/i18n :text_addPublisher_button_clear)))

(def button_addPublisher_save_action (action
                                   :handler (fn [e] (handleExecuteAddPublisher))
                                   :name (ClojureProject.lit_i18n/i18n :text_addPublisher_button_save)))

(def button_modifyTitle_action (action
                                   :handler (fn [e] (handleModifyTitle))
                                   :name (ClojureProject.lit_i18n/i18n :text_modifyTitle_button)))

(def button_modifyTitle_save_action (action
                                   :handler (fn [e] (handleExecuteModifyTitle))
                                   :name (ClojureProject.lit_i18n/i18n :text_modifyTitle_button_save)))

(def button_modifyPublisher_action (action
                                   :handler (fn [e] (handleModifyPublisher))
                                   :name (ClojureProject.lit_i18n/i18n :text_modifyPublisher_button)))

(def button_modifyPublisher_save_action (action
                                   :handler (fn [e] (handleExecuteModifyPublisher))
                                   :name (ClojureProject.lit_i18n/i18n :text_modifyPublisher_button_save)))

(def button_deleteTitle_action (action
                                   :handler (fn [e] (handleExecuteDeleteTitle))
                                   :name (ClojureProject.lit_i18n/i18n :text_deleteTitle_button)))

(def button_deletePublisher_action (action
                                   :handler (fn [e] (handleExecuteDeletePublisher))
                                   :name (ClojureProject.lit_i18n/i18n :text_deletePublisher_button)))

;-------------------------------------------------------------------------------------------------------------------------------
; MENU ACTIONS

(def menubar_database_item_config_action (action
                    :handler (fn [e] (switch database_panel))
                    :name (ClojureProject.lit_i18n/i18n :text_menubar_database_item_config)
                    :tip  (ClojureProject.lit_i18n/i18n :text_menubar_database_item_config_tooltip)))

(def menubar_database_item_connect_action (action
                    :handler (fn [e] (handleConnectDatabase))
                    :name (ClojureProject.lit_i18n/i18n :text_menubar_database_item_connect)
                    :tip  (ClojureProject.lit_i18n/i18n :text_menubar_database_item_connect_tooltip)))

(def menubar_database_item_disconnect_action (action
                    :handler (fn [e] (handleDisconnectDatabase))
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

(def searchTitle_search_table (table 
                                :model [:columns [{:key :id, :text "ID"} {:key :name, :text "Name"}] 
                                        :rows [["01" "Alexander Nadler"]
                                               ["02" "Julian Rossbach"]]]))

(def searchTitle_result_panel (grid-panel
                                :columns 1 
                                :items [(flow-panel :items [searchTitle_search_table])
                                        (flow-panel :align :right
                                                    :items [(button :action button_modifyTitle_action)
                                                            (button :action button_deleteTitle_action)])]))

(def searchTitle_panel (scrollable (top-bottom-split searchTitle_search_panel searchTitle_result_panel)))

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

(def searchPublisher_search_table (table 
                                    :model [:columns [{:key :id, :text "ID"} {:key :name, :text "Name"}] 
                                            :rows [["01" "Alexander Nadler"]
                                                   ["02" "Julian Rossbach"]]]))

(def searchPublisher_result_panel (grid-panel
                                    :columns 1 
                                    :items [(flow-panel :items [searchPublisher_search_table])
                                            (flow-panel :align :right
                                                        :items [(button :action button_modifyPublisher_action)
                                                                (button :action button_deletePublisher_action)])]))

(def searchPublisher_panel (top-bottom-split searchPublisher_search_panel searchPublisher_result_panel))

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
; MODIFY TITLE PANEL / FRAME

(def field_modifyTitle_name (text))
(def field_modifyTitle_isbn (text))
(def field_modifyTitle_author (text))
(def field_modifyTitle_publisher (text))

(def modifyTitle_panel (grid-panel
                         :columns 1 
                         :items [(grid-panel
                                   :columns 2
                                   :items [(ClojureProject.lit_i18n/i18n :text_modifyTitle_name) field_modifyTitle_name
                                           (ClojureProject.lit_i18n/i18n :text_modifyTitle_isbn) field_modifyTitle_isbn
                                           (ClojureProject.lit_i18n/i18n :text_modifyTitle_author) field_modifyTitle_author
                                           (ClojureProject.lit_i18n/i18n :text_modifyTitle_publisher) field_modifyTitle_publisher])
                                 (flow-panel
                                   :align :right
                                   :items [(button :action button_modifyTitle_save_action)])]))

(def frame_modifyTitle (frame :title (ClojureProject.lit_i18n/i18n :text_modifyTitle_frame_title)
                              :size [640 :by 480]
                              :content modifyTitle_panel))

;-------------------------------------------------------------------------------------------------------------------------------
; MODIFY PUBLISHER PANEL / FRAME

(def field_modifyPublisher_name (text))

(def modifyPublisher_panel (grid-panel
                             :columns 1 
                             :items [(grid-panel
                                       :columns 2
                                       :items [(ClojureProject.lit_i18n/i18n :text_modifyPublisher_name) field_modifyPublisher_name])
                                     (flow-panel
                                       :align :right
                                       :items [(button :action button_modifyPublisher_save_action)])]))

(def frame_modifyPublisher (frame :title (ClojureProject.lit_i18n/i18n :text_modifyTitle_frame_title)
                                  :size [640 :by 480]
                                  :content modifyPublisher_panel))

;-------------------------------------------------------------------------------------------------------------------------------
; MAIN FRAME

(def frame_main (frame :title (ClojureProject.lit_i18n/i18n :text_frame_main_title)
                       :size [640 :by 480]
                       :menubar frame_menu
                       :content searchTitle_panel))
                       ;:on-close :exit))

(native!) ; native os ui
(-> frame_main pack! show!)                                                  
