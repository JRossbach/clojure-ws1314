;-------------------------------------------------------------------------------------------------------------------------------
(ns ClojureProject.lit_view
 (:require [seesaw.core :refer :all]
           [seesaw.table :refer :all]
           [seesaw.dev :refer :all]
           [ClojureProject.lit_i18n :as label]
           [ClojureProject.lit_control :as control]))
            
;-------------------------------------------------------------------------------------------------------------------------------
; DECLARETION

(declare setTitleResultTableModel)
(declare setPublisherResultTableModel)

(declare setResultTableModel)
(declare getResultTableHead)
(declare getResultTableBody)

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
(declare field_modifyTitle_id)
(declare field_modifyTitle_name)
(declare field_modifyTitle_isbn)
(declare field_modifyTitle_author)
(declare field_modifyTitle_publisher)

(declare modifyPublisher_panel)
(declare field_modifyPublisher_id)
(declare field_modifyPublisher_name)

(declare frame_modifyTitle)
(declare frame_modifyPublisher)

(declare menu_frame_main)
(declare frame_main)

;-------------------------------------------------------------------------------------------------------------------------------
; SET ITEM CONTENTS

(defn setTitleResultTableModel 
  "Fills the title search result table with the given result map"
  [result]
  (cond (empty? result) (setResultTableModel searchTitle_search_table)
        :else (setResultTableModel searchTitle_search_table result)))

(defn setPublisherResultTableModel 
  "Fills the publisher search result table with the given result map"
  [result]
  (cond (empty? result) (setResultTableModel searchPublisher_search_table)
        :else (setResultTableModel searchPublisher_search_table result)))

(defn setResultTableModel 
  ([table] (config! table 
                    :model [:columns [{:key :text, :text ""}] 
                            :rows [[(label/i18n :text_table_noResult)]]]))
  ([table result] (config! table 
                           :model [:columns (getResultTableHead result) 
                                   :rows (getResultTableBody result)])))

(defn getResultTableHead22 
  [result] (doseq [[key text] (map list (keys (get result 0)) (keys (get result 0)))]
                                   (key (str text))))

(defn getResultTableHead23 
  [result] (map list 
                (keys (get result 0)) 
                (apply str (keys (get result 0)))))


(defn getResultTableHead 
  [result] [{:key :publisher_id, :text "PublisherID"}
            {:key :author, :text "Author"}
            {:key :isbn, :text "ISBN"}
            {:key :name, :text "Name"}
            {:key :id, :text "ID"}])

(defn getResultTableBody 
  [result] (vec (for [record result] 
                  (vec (vals record)))))

;-------------------------------------------------------------------------------------------------------------------------------
; HANDLER DATABASE

(defn switch 
  "Switches the content of the main windows to the given container"
  [container]
  (config! frame_main :content container))

(defn handleSaveConfiguration 
  "Saves the database connection data"
  []
  (control/saveConfiguration))

(defn handleConnectDatabase 
  "Opens a database connection with the connection data from the database fields"
  []
  (control/controlConnectDatabase (config field_database_host :text)
                                  (config field_database_name :text)
                                  (config field_database_username :text)
                                  (config field_database_password :text)))

(defn handleDisconnectDatabase 
  "Closes the actual active database connection"
  []
  (control/controlDisconnectDatabase))

;-------------------------------------------------------------------------------------------------------------------------------
; HANDLER TITLE

(defn handleClearSearchTitle 
  "Clears the search title text fields"
  []
  (config! field_searchTitle_name :text "")
  (config! field_searchTitle_isbn :text "")
  (config! field_searchTitle_author :text "")
  (config! field_searchTitle_publisher :text ""))

(defn handleClearAddTitle 
  "Clears the add title text fields"
  []
  (config! field_addTitle_name :text "")
  (config! field_addTitle_isbn :text "")
  (config! field_addTitle_author :text "")
  (config! field_addTitle_publisher :text ""))

(defn handleExecuteSearchTitle 
  "Searches a title in the database for the given conditions"
  []
  (setTitleResultTableModel (control/executeSearchTitle {:name (config field_searchTitle_name :text)
                                                         :isbn (config field_searchTitle_isbn :text)
                                                         :author (config field_searchTitle_author :text)
                                                         :publisher_id (config field_searchTitle_publisher :text)})))

(defn handleExecuteAddTitle 
  "Adds a new title to the database"
  []
  (control/executeAddTitle {:name (config field_addTitle_name :text)
                            :isbn (config field_addTitle_isbn :text)
                            :author (config field_addTitle_author :text)
                            :publisher_id (config field_addTitle_publisher :text)})
  (switch searchTitle_panel)
  (handleExecuteSearchTitle))

(defn handleModifyTitle 
  "Opens the modify title panel with the data from the selected title"
  [] 
  (let [title (value-at searchTitle_search_table (selection searchTitle_search_table))]
    (config! field_modifyTitle_id :text (get title :id))
    (config! field_modifyTitle_name :text (get title :name))
    (config! field_modifyTitle_isbn :text (get title :isbn))
    (config! field_modifyTitle_author :text (get title :author))
    (config! field_modifyTitle_publisher :text (get title :publisher_id))
    (switch modifyTitle_panel)))

(defn handleExecuteModifyTitle 
  "Saves the modified title in the database"
  [] 
  (control/executeModifyTitle {:id (config field_modifyTitle_id :text)
                               :name (config field_modifyTitle_name :text)
                               :isbn (config field_modifyTitle_isbn :text)
                               :author (config field_modifyTitle_author :text)
                               :publisher_id (config field_modifyTitle_publisher :text)})
  (switch searchTitle_panel)
  (handleExecuteSearchTitle))

(defn handleExecuteDeleteTitle 
  "Deletes the selected title from the database"
  [] 
  (control/executeDeleteTitle (get (value-at searchTitle_search_table (selection searchTitle_search_table)) :id))
  (handleExecuteSearchTitle))

;-------------------------------------------------------------------------------------------------------------------------------
; HANDLER PUBLISHER

(defn handleClearSearchPublisher 
  "Clears the search publisher text fields"
  []
  (config! field_searchPublisher_name :text ""))

(defn handleClearAddPublisher 
  "Clears the add publisher text fields"
  []
  (config! field_addPublisher_name :text ""))

(defn handleExecuteSearchPublisher 
  "Searches a publisher in the database for the given conditions"
  []
  (control/executeSearchPublisher {:name (config field_searchPublisher_name :text)}))

(defn handleExecuteAddPublisher 
  "Adds a new publisher to the database"
  []
  (control/executeAddPublisher {:name (config field_addPublisher_name :text)}))

(defn handleModifyPublisher 
  "Opens the modify publisher window with the data from the selected publisher"
  [] 
  (println (value-at searchPublisher_search_table (selection searchPublisher_search_table))))

(defn handleExecuteModifyPublisher 
  "Saves the modified publisher in the database"
  [] 
  ())

(defn handleExecuteDeletePublisher 
  "Deletes the selected publisher from the database"
  [] 
  ())

;-------------------------------------------------------------------------------------------------------------------------------
; BUTTON ACTIONS

(def button_database_save_action (action
                                   :handler (fn [e] (handleSaveConfiguration))
                                   :name (label/i18n :text_database_button_save)))

(def button_searchTitle_clear_action (action
                                   :handler (fn [e] (handleClearSearchTitle))
                                   :name (label/i18n :text_searchTitle_button_clear)))

(def button_searchTitle_search_action (action
                                   :handler (fn [e] (handleExecuteSearchTitle))
                                   :name (label/i18n :text_searchTitle_button_search)))

(def button_addTitle_clear_action (action
                                   :handler (fn [e] (handleClearAddTitle))
                                   :name (label/i18n :text_addTitle_button_clear)))

(def button_addTitle_save_action (action
                                   :handler (fn [e] (handleExecuteAddTitle))
                                   :name (label/i18n :text_addTitle_button_save)))

(def button_searchPublisher_clear_action (action
                                   :handler (fn [e] (handleClearSearchPublisher))
                                   :name (label/i18n :text_searchPublisher_button_clear)))

(def button_searchPublisher_search_action (action
                                   :handler (fn [e] (handleExecuteSearchPublisher))
                                   :name (label/i18n :text_searchPublisher_button_search)))

(def button_addPublisher_clear_action (action
                                   :handler (fn [e] (handleClearAddPublisher))
                                   :name (label/i18n :text_addPublisher_button_clear)))

(def button_addPublisher_save_action (action
                                   :handler (fn [e] (handleExecuteAddPublisher))
                                   :name (label/i18n :text_addPublisher_button_save)))

(def button_modifyTitle_action (action
                                   :handler (fn [e] (handleModifyTitle))
                                   :name (label/i18n :text_modifyTitle_button)))

(def button_modifyTitle_save_action (action
                                   :handler (fn [e] (handleExecuteModifyTitle))
                                   :name (label/i18n :text_modifyTitle_button_save)))

(def button_modifyPublisher_action (action
                                   :handler (fn [e] (handleModifyPublisher))
                                   :name (label/i18n :text_modifyPublisher_button)))

(def button_modifyPublisher_save_action (action
                                   :handler (fn [e] (handleExecuteModifyPublisher))
                                   :name (label/i18n :text_modifyPublisher_button_save)))

(def button_deleteTitle_action (action
                                   :handler (fn [e] (handleExecuteDeleteTitle))
                                   :name (label/i18n :text_deleteTitle_button)))

(def button_deletePublisher_action (action
                                   :handler (fn [e] (handleExecuteDeletePublisher))
                                   :name (label/i18n :text_deletePublisher_button)))

;-------------------------------------------------------------------------------------------------------------------------------
; MENU ACTIONS

(def menubar_database_item_config_action (action
                    :handler (fn [e] (switch database_panel))
                    :name (label/i18n :text_menubar_database_item_config)
                    :tip  (label/i18n :text_menubar_database_item_config_tooltip)))

(def menubar_database_item_connect_action (action
                    :handler (fn [e] (handleConnectDatabase))
                    :name (label/i18n :text_menubar_database_item_connect)
                    :tip  (label/i18n :text_menubar_database_item_connect_tooltip)))

(def menubar_database_item_disconnect_action (action
                    :handler (fn [e] (handleDisconnectDatabase))
                    :name (label/i18n :text_menubar_database_item_disconnect)
                    :tip  (label/i18n :text_menubar_database_item_disconnect_tooltip)))

(def menubar_operations_item_searchTitle_action (action
                    :handler (fn [e] (switch searchTitle_panel))
                    :name (label/i18n :text_menubar_operations_item_searchTitle)
                    :tip  (label/i18n :text_menubar_operations_item_searchTitle_tooltip)))

(def menubar_operations_item_addTitle_action (action
                    :handler (fn [e] (switch addTitle_panel))
                    :name (label/i18n :text_menubar_operations_item_addTitle)
                    :tip  (label/i18n :text_menubar_operations_item_addTitle_tooltip)))

(def menubar_operations_item_searchPublisher_action (action
                    :handler (fn [e] (switch searchPublisher_panel))
                    :name (label/i18n :text_menubar_operations_item_searchPublisher)
                    :tip  (label/i18n :text_menubar_operations_item_searchPublisher_tooltip)))

(def menubar_operations_item_addPublisher_action (action
                    :handler (fn [e] (switch addPublisher_panel))
                    :name (label/i18n :text_menubar_operations_item_addPublisher)
                    :tip  (label/i18n :text_menubar_operations_item_addPublisher_tooltip)))

;-------------------------------------------------------------------------------------------------------------------------------
; MENU

(def menu_frame_main (menubar :items 
                         [(menu :text (label/i18n :text_menubar_database_main_title) 
                                :items [menubar_database_item_config_action
                                        menubar_database_item_connect_action
                                        menubar_database_item_disconnect_action])
                          (menu :text (label/i18n :text_menubar_operations_main_title)
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
                      :border (label/i18n :text_database_border)
                      :columns 1
                      :items [(grid-panel
                                :columns 2
                                :items [(label/i18n :text_database_host) field_database_host
                                        (label/i18n :text_database_name) field_database_name
                                        (label/i18n :text_database_username) field_database_username
                                        (label/i18n :text_database_password) field_database_password])
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
                                :border (label/i18n :text_searchTitle_border)
                                :columns 1 
                                :items [(grid-panel
                                         :columns 2
                                         :items [(label/i18n :text_searchTitle_name) field_searchTitle_name
                                                 (label/i18n :text_searchTitle_isbn) field_searchTitle_isbn
                                                 (label/i18n :text_searchTitle_author) field_searchTitle_author
                                                 (label/i18n :text_searchTitle_publisher) field_searchTitle_publisher])
                                       (flow-panel
                                         :align :right
                                         :items [(button :action button_searchTitle_clear_action)
                                                 (button :action button_searchTitle_search_action)])]))

(def searchTitle_search_table (table 
                                :model [:columns [{:key :id, :text "-"}] 
                                        :rows [[" " " "]]]))

(def searchTitle_result_panel (grid-panel
                                :columns 1 
                                :items [(flow-panel :items [(scrollable searchTitle_search_table)])
                                        (flow-panel :align :right
                                                    :items [(button :action button_modifyTitle_action)
                                                            (button :action button_deleteTitle_action)])]))

(def searchTitle_panel (scrollable (top-bottom-split searchTitle_search_panel searchTitle_result_panel)))

;-------------------------------------------------------------------------------------------------------------------------------
; SEARCH PUBLISHER PANEL

(def field_searchPublisher_name (text))

(def searchPublisher_search_panel (grid-panel
                                :border (label/i18n :text_searchPublisher_border)
                                :columns 1 
                                :items [(grid-panel
                                         :columns 2
                                         :items [(label/i18n :text_searchPublisher_name) field_searchPublisher_name])
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
                                    :items [(flow-panel :items [(scrollable searchPublisher_search_table)])
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
                      :border (label/i18n :text_addTitle_border)
                      :columns 1 
                      :items [(grid-panel
                                :columns 2
                                :items [(label/i18n :text_addTitle_name) field_addTitle_name
                                        (label/i18n :text_addTitle_isbn) field_addTitle_isbn
                                        (label/i18n :text_addTitle_author) field_addTitle_author
                                        (label/i18n :text_addTitle_publisher) field_addTitle_publisher])
                              (flow-panel
                                :align :right
                                :items [(button :action button_addTitle_clear_action)
                                        (button :action button_addTitle_save_action)])]))

;-------------------------------------------------------------------------------------------------------------------------------
; ADD PUBLISHER PANEL

(def field_addPublisher_name (text))

(def addPublisher_panel (grid-panel
                          :border (label/i18n :text_addPublisher_border)
                          :columns 1 
                          :items [(grid-panel
                                    :columns 2
                                    :items [(label/i18n :text_addPublisher_name) field_addPublisher_name])
                                  (flow-panel
                                    :align :right
                                    :items [(button :action button_addPublisher_clear_action)
                                            (button :action button_addPublisher_save_action)])]))

;-------------------------------------------------------------------------------------------------------------------------------
; MODIFY TITLE PANEL / FRAME

(def field_modifyTitle_id (text))
(def field_modifyTitle_name (text))
(def field_modifyTitle_isbn (text))
(def field_modifyTitle_author (text))
(def field_modifyTitle_publisher (text))

(def modifyTitle_panel (grid-panel
                         :border (label/i18n :text_modifyTitle_border)
                         :columns 1 
                         :items [(grid-panel
                                   :columns 2
                                   :items [(label/i18n :text_modifyTitle_id) field_modifyTitle_id
                                           (label/i18n :text_modifyTitle_name) field_modifyTitle_name
                                           (label/i18n :text_modifyTitle_isbn) field_modifyTitle_isbn
                                           (label/i18n :text_modifyTitle_author) field_modifyTitle_author
                                           (label/i18n :text_modifyTitle_publisher) field_modifyTitle_publisher])
                                 (flow-panel
                                   :align :right
                                   :items [(button :action button_modifyTitle_save_action)])]))

(def frame_modifyTitle (frame :title (label/i18n :text_modifyTitle_frame_title)
                              :size [640 :by 480]
                              :content modifyTitle_panel))

;-------------------------------------------------------------------------------------------------------------------------------
; MODIFY PUBLISHER PANEL / FRAME

(def field_modifyPublisher_id (text))
(def field_modifyPublisher_name (text))

(def modifyPublisher_panel (grid-panel
                             :border (label/i18n :text_modifyPublisher_border)
                             :columns 1 
                             :items [(grid-panel
                                       :columns 2
                                       :items [(label/i18n :text_modifyPublisher_id) field_modifyPublisher_id
                                               (label/i18n :text_modifyPublisher_name) field_modifyPublisher_name])
                                     (flow-panel
                                       :align :right
                                       :items [(button :action button_modifyPublisher_save_action)])]))

;-------------------------------------------------------------------------------------------------------------------------------
; MAIN FRAME

(def frame_main (frame :title (label/i18n :text_frame_main_title)
                       :size [640 :by 480]
                       :menubar menu_frame_main
                       :content searchTitle_panel))
                       ;:on-close :exit))

(native!) ; native os ui
(-> frame_main pack! show!)                                                  
