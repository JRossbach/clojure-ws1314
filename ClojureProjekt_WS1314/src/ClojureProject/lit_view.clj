;-------------------------------------------------------------------------------------------------------------------------------
(ns ClojureProject.lit_view
 (:require [seesaw.core :refer :all]
           [seesaw.table :refer :all]
           [seesaw.dev :refer :all]
           [seesaw.mig :refer :all]
           [seesaw.cells :refer :all]
           [clojure.tools.logging :as log]
           [ClojureProject.lit_i18n :as label]
           [ClojureProject.lit_control :as control]
           [ClojureProject.lit_types :as types]))

;-------------------------------------------------------------------------------------------------------------------------------
; DECLARETION

(declare setTitleResult)
(declare setTitleResultTableModel)
(declare setPublisherResult)
(declare setPublisherResultTableModel)
(declare getTitleResultTableBody)
(declare getPublisherResultTableBody)

(declare database_panel)
(declare field_database_host)
(declare field_database_name)
(declare field_database_username)
(declare field_database_password)

(declare searchTitle_search_table)
(declare searchTitle_panel)
(declare field_searchTitle_name)
(declare field_searchTitle_isbn)
(declare field_searchTitle_author)
(declare field_searchTitle_publisher)

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

(declare menu_frame_main)
(declare frame_main)
(declare frame_messagebox)

;-------------------------------------------------------------------------------------------------------------------------------
; UTIL

(native!) ; native os ui

(def defaultComboBoxEntry {:id -1 :name "-"})
(def defaultTitleId -1)
(def defaultPublisherId -1)

(def searchTitleTableModel [{:key :id, :text (label/i18n :text_table_head_id)}
                            {:key :name, :text (label/i18n :text_table_head_name)}
                            {:key :author, :text (label/i18n :text_table_head_author)}
                            {:key :isbn, :text (label/i18n :text_table_head_isbn)}
                            {:key :publisher, :text (label/i18n :text_table_head_publisher)}])

(def searchPublisherTableModel [{:key :id, :text (label/i18n :text_table_head_id)}
                                {:key :name, :text (label/i18n :text_table_head_name)}])

(defn switch 
  "Switches the content of the main windows to the given container"
  [container]
  (config! frame_main :content container))

(defn string-renderer 
  "Renderer for combobox display string"
  [f] 
  (default-list-cell-renderer (fn [this {:keys [value]}] (.setText this (str (f value))))))

(defn actualizePublisherComboBoxModel 
  "Actualizes the combobox entries with the existing publishers"
  []
  (let [comboBoxModelSearch (new javax.swing.DefaultComboBoxModel)
        comboBoxModelSelect (new javax.swing.DefaultComboBoxModel)
        publisher (control/search (types/publisher defaultPublisherId ""))]
    (.addElement comboBoxModelSearch defaultComboBoxEntry)
    (doseq [p publisher] 
      (.addElement comboBoxModelSearch p)
      (.addElement comboBoxModelSelect p))    
    (config! field_searchTitle_publisher :model comboBoxModelSearch)
    (config! field_addTitle_publisher :model comboBoxModelSelect)
    (config! field_modifyTitle_publisher :model comboBoxModelSelect)))

(defn handleHideMessageBox 
  ""
  []
  (hide! frame_messagebox))

;-------------------------------------------------------------------------------------------------------------------------------
; SET ITEM CONTENTS

(defn setTitleResult
  "Fills the search title result table with the given result map"
  [result]
  (if (empty? result) 
      (setTitleResultTableModel)
      (setTitleResultTableModel result)))

(defn setTitleResultTableModel 
  ""
  ([] (clear! searchTitle_search_table))
  ([result] (config! searchTitle_search_table :model [:columns searchTitleTableModel 
                                                      :rows (getTitleResultTableBody result)])))

(defn getTitleResultTableBody 
  ""
  [result]
  (with-local-vars [acc, result]
    (var-set acc [])
    (doseq [value result]
      (var-set acc (conj @acc {:id (value :id)
                               :name (value :name)
                               :author (value :author)
                               :isbn (value :isbn)
                               :publisher ((value :publisher) :name)})))@acc))

(defn setPublisherResult
  "Fills the search publisher result table with the given result map"
  [result]
  (if (empty? result) 
      (setTitleResultTableModel)
      (setTitleResultTableModel result)))

(defn setPublisherResultTableModel 
  ""
  ([] (clear! searchPublisher_search_table))
  ([result] (config! searchPublisher_search_table :model [:columns searchPublisherTableModel 
                                                          :rows (getPublisherResultTableBody result)])))

(defn getPublisherResultTableBody
  ""
  [result]  
  (with-local-vars [acc, result]
    (var-set acc [])
    (doseq [value result]
      (var-set acc (conj @acc {:id (value :id)
                               :name (value :name)})))@acc))

;-------------------------------------------------------------------------------------------------------------------------------
; SEARCH TITLE

(defn handleClearSearchTitle 
  "Clears the search title text fields"
  []
  (config! field_searchTitle_name :text "")
  (config! field_searchTitle_isbn :text "")
  (config! field_searchTitle_author :text "")
  (selection! field_searchTitle_publisher defaultComboBoxEntry))

(defn handleExecuteSearchTitle 
  "Searches a title in the database for the given conditions"
  []
  (try 
    (setTitleResult (control/search (types/title defaultPublisherId
                                                 (config field_searchTitle_name :text)
                                                 (config field_searchTitle_author :text)
                                                 (config field_searchTitle_isbn :text)
                                                 (selection field_searchTitle_publisher))))
    (catch Exception e (str "caught exception: " (.getMessage e)))))

;-------------------------------------------------------------------------------------------------------------------------------
; ADD TITLE

(defn handleClearAddTitle 
  "Clears the add title text fields"
  []
  (config! field_addTitle_name :text "")
  (config! field_addTitle_isbn :text "")
  (config! field_addTitle_author :text "")
  (selection! field_searchTitle_publisher defaultComboBoxEntry))  

(defn handleExecuteAddTitle 
  "Adds a new title to the database"
  []
  (try 
    (control/add (types/title defaultTitleId 
                              (config field_addTitle_name :text)
                              (config field_addTitle_author :text)
                              (config field_addTitle_isbn :text)
                              (selection field_modifyTitle_publisher)))
    (switch searchTitle_panel)
    (handleExecuteSearchTitle)
    (catch Exception e (str "caught exception: " (.getMessage e)))))
  
;-------------------------------------------------------------------------------------------------------------------------------
; MODIFY TITLE

(defn handleModifyTitle 
  "Opens the modify title panel with the data from the selected title"
  [] 
  (let [selectedTitle (value-at searchTitle_search_table (selection searchTitle_search_table))
        title (control/searchById (types/title (selectedTitle :id) "" "" "" (types/publisher defaultPublisherId "")))]
    (config! field_modifyTitle_id :text (title :id))
    (config! field_modifyTitle_name :text (title :name))
    (config! field_modifyTitle_isbn :text (title :isbn))
    (config! field_modifyTitle_author :text (title :author))
    (selection! field_modifyTitle_publisher (title :publisher))
    (switch modifyTitle_panel)))

(defn handleExecuteModifyTitle 
  "Saves the modified title in the database"
  [] 
  (try 
    (control/update (types/title (config field_modifyTitle_id :text)
                                 (config field_modifyTitle_name :text)
                                 (config field_modifyTitle_author :text)
                                 (config field_modifyTitle_isbn :text)
                                 (selection field_modifyTitle_publisher)))    
    (handleExecuteSearchTitle)
    (switch searchTitle_panel)
    (catch Exception e (str "caught exception: " (.getMessage e)))))

;-------------------------------------------------------------------------------------------------------------------------------
; DELETE TITLE

(defn handleExecuteDeleteTitle 
  "Deletes the selected title from the database"
  [] 
  (try 
    (let [title (value-at searchTitle_search_table (selection searchTitle_search_table))]
      (control/delete (types/title (title :id)
                                   (title :name)
                                   (title :author)
                                   (title :isbn)
                                   (title :publisher))))
    (handleExecuteSearchTitle)
    (switch searchTitle_panel)
    (catch Exception e (str "caught exception: " (.getMessage e)))))

;-------------------------------------------------------------------------------------------------------------------------------
; SEARCH PUBLISHER

(defn handleClearSearchPublisher 
  "Clears the search publisher text fields"
  []
  (config! field_searchPublisher_name :text ""))

(defn handleExecuteSearchPublisher 
  "Searches a publisher in the database for the given conditions"
  []
  (try 
    (setPublisherResultTableModel (control/search (types/publisher defaultPublisherId
                                                                   (config field_searchPublisher_name :text))))
    (catch Exception e (str "caught exception: " (.getMessage e)))))

;-------------------------------------------------------------------------------------------------------------------------------
; ADD PUBLISHER

(defn handleClearAddPublisher 
  "Clears the add publisher text fields"
  []
  (config! field_addPublisher_name :text ""))

(defn handleExecuteAddPublisher 
  "Adds a new publisher to the database"
  []
  (try 
    (control/add (types/publisher defaultPublisherId
                                  (config field_addPublisher_name :text)))
    (actualizePublisherComboBoxModel)
    (handleExecuteSearchPublisher)
    (handleExecuteSearchTitle)
    (switch searchPublisher_panel)
    (catch Exception e (str "caught exception: " (.getMessage e)))))

;-------------------------------------------------------------------------------------------------------------------------------
; MODIFY PUBLISHER

(defn handleModifyPublisher 
  "Opens the modify publisher window with the data from the selected publisher"
  [] 
  (let [publisher (value-at searchPublisher_search_table (selection searchPublisher_search_table))]
    (config! field_modifyPublisher_id :text (get publisher :id))
    (config! field_modifyPublisher_name :text (get publisher :name))
    (switch modifyPublisher_panel)))

(defn handleExecuteModifyPublisher 
  "Saves the modified publisher in the database"
  []
  (try 
    (control/update (types/publisher (config field_modifyPublisher_id :text)
                                     (config field_modifyPublisher_name :text)))
    (actualizePublisherComboBoxModel)
    (handleExecuteSearchPublisher)
    (handleExecuteSearchTitle)
    (switch searchPublisher_panel)
    (catch Exception e (str "caught exception: " (.getMessage e)))))

;-------------------------------------------------------------------------------------------------------------------------------
; DELETE PUBLISHER

(defn handleExecuteDeletePublisher 
  "Deletes the selected publisher from the database"
  [] 
  (try 
    (let [publisher (value-at searchPublisher_search_table (selection searchPublisher_search_table))]
      (control/delete (types/publisher (publisher :id)
                                       (publisher :name))))    
    (actualizePublisherComboBoxModel)
    (handleExecuteSearchPublisher)
    (handleExecuteSearchTitle)
    (switch searchPublisher_panel)
    (catch Exception e (str "caught exception: " (.getMessage e)))))

;-------------------------------------------------------------------------------------------------------------------------------
; BUTTON ACTIONS

(def button_messageBox_close_action (action
                                   :handler (fn [e] (handleHideMessageBox))
                                   :name (label/i18n :text_frame_messagebox_button)))

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
                         [(menu :text (label/i18n :text_menubar_operations_main_title)
                                :items [menubar_operations_item_searchTitle_action
                                        menubar_operations_item_addTitle_action
                                        menubar_operations_item_searchPublisher_action
                                        menubar_operations_item_addPublisher_action])]))

;-------------------------------------------------------------------------------------------------------------------------------
; MESSAGEBOX

(def messageBoxText (text :multi-line? true?
                          :editable? false
                          :wrap-lines?  true
                          :rows 10))
                       
(def frame_messagebox (frame :title (label/i18n :text_frame_messagebox_title)
                             :width 400
                             :height 200
                             :content (mig-panel :constraints ["wrap 1" "[grow, fill]" "[fill]"]
                                                 :items [[messageBoxText]
                                                         [(button :action button_messageBox_close_action)]])))

;-------------------------------------------------------------------------------------------------------------------------------
; SEARCH TITLE PANEL

(def field_searchTitle_name (text))
(def field_searchTitle_isbn (text))
(def field_searchTitle_author (text))
(def field_searchTitle_publisher (combobox :renderer (string-renderer :name)))
(def searchTitle_search_table (table))
(.setSelectionMode searchTitle_search_table javax.swing.ListSelectionModel/SINGLE_SELECTION)

(def searchTitle_panel (mig-panel :border (label/i18n :text_searchTitle_border)
                                  :constraints ["wrap 1" "[grow, fill]" "[fill]"]
                                  :items [[(mig-panel :constraints ["wrap 2" "[shrink 0]100px[200, grow, fill]" "[shrink 0]5px[]"]
                                                      :items [[(label/i18n :text_searchTitle_name)] [field_searchTitle_name]
                                                              [(label/i18n :text_searchTitle_isbn)] [field_searchTitle_isbn]
                                                              [(label/i18n :text_searchTitle_author)] [field_searchTitle_author]
                                                              [(label/i18n :text_searchTitle_publisher)] [field_searchTitle_publisher]])]
                                          [(flow-panel :align :right 
                                                       :items [(button :action button_searchTitle_clear_action)
                                                               (button :action button_searchTitle_search_action)])]
                                          [(scrollable searchTitle_search_table)]                                          
                                          [(flow-panel :align :right 
                                                       :items [(button :action button_modifyTitle_action)
                                                               (button :action button_deleteTitle_action)])]]))

;-------------------------------------------------------------------------------------------------------------------------------
; SEARCH PUBLISHER PANEL

(def field_searchPublisher_name (text))
(def searchPublisher_search_table (table))
(.setSelectionMode searchPublisher_search_table javax.swing.ListSelectionModel/SINGLE_SELECTION)

(def searchPublisher_panel (mig-panel :border (label/i18n :text_searchPublisher_border)
                                      :constraints ["wrap 1" "[grow, fill]" "[fill]"]
                                      :items [[(mig-panel :constraints ["wrap 2" "[shrink 0]100px[200, grow, fill]" "[shrink 0]5px[]"]
                                                          :items [[(label/i18n :text_searchPublisher_name)][field_searchPublisher_name]])]
                                              [(flow-panel :align :right
                                                           :items [(button :action button_searchPublisher_clear_action)
                                                                   (button :action button_searchPublisher_search_action)])]  
                                              [(scrollable searchPublisher_search_table)]  
                                              [(flow-panel :align :right
                                                               :items [(button :action button_modifyPublisher_action)
                                                                       (button :action button_deletePublisher_action)])]]))

;-------------------------------------------------------------------------------------------------------------------------------
; ADD TITLE PANEL

(def field_addTitle_name (text))
(def field_addTitle_isbn (text))
(def field_addTitle_author (text))
(def field_addTitle_publisher (combobox :renderer (string-renderer :name)))

(def addTitle_panel (mig-panel :border (label/i18n :text_addTitle_border)
                               :constraints ["wrap 1" "[grow, fill]" "[fill]"]
                               :items [[(mig-panel :constraints ["wrap 2" "[shrink 0]100px[200, grow, fill]" "[shrink 0]5px[]"]
                                                   :items [[(label/i18n :text_addTitle_name)][field_addTitle_name]
                                                           [(label/i18n :text_addTitle_isbn)][field_addTitle_isbn]
                                                           [(label/i18n :text_addTitle_author)][field_addTitle_author]
                                                           [(label/i18n :text_addTitle_publisher)][field_addTitle_publisher]])]
                                       [(flow-panel :align :right
                                                    :items [(button :action button_addTitle_clear_action)
                                                            (button :action button_addTitle_save_action)])]]))

;-------------------------------------------------------------------------------------------------------------------------------
; ADD PUBLISHER PANEL

(def field_addPublisher_name (text))

(def addPublisher_panel (mig-panel :border (label/i18n :text_addPublisher_border)
                                   :constraints ["wrap 1" "[grow, fill]" "[fill]"]
                                   :items [[(mig-panel :constraints ["wrap 2" "[shrink 0]100px[200, grow, fill]" "[shrink 0]5px[]"]
                                                       :items [[(label/i18n :text_addPublisher_name)][field_addPublisher_name]])]
                                           [(flow-panel :align :right
                                                        :items [(button :action button_addPublisher_clear_action)
                                                                (button :action button_addPublisher_save_action)])]]))

;-------------------------------------------------------------------------------------------------------------------------------
; MODIFY TITLE PANEL / FRAME

(def field_modifyTitle_id (text :editable? false))
(def field_modifyTitle_name (text))
(def field_modifyTitle_isbn (text))
(def field_modifyTitle_author (text))
(def field_modifyTitle_publisher (combobox :renderer (string-renderer :name)))

(def modifyTitle_panel (mig-panel :border (label/i18n :text_modifyTitle_border)
                                  :constraints ["wrap 1" "[grow, fill]" "[fill]"]
                                  :items [[(mig-panel :constraints ["wrap 2" "[shrink 0]100px[200, grow, fill]" "[shrink 0]5px[]"]
                                                      :items [[(label/i18n :text_modifyTitle_id)][field_modifyTitle_id]
                                                              [(label/i18n :text_modifyTitle_name)][field_modifyTitle_name]
                                                              [(label/i18n :text_modifyTitle_isbn)][field_modifyTitle_isbn]
                                                              [(label/i18n :text_modifyTitle_author)][field_modifyTitle_author]
                                                              [(label/i18n :text_modifyTitle_publisher)][field_modifyTitle_publisher]])]
                                          [(flow-panel :align :right
                                                       :items [(button :action button_modifyTitle_save_action)])]]))

;-------------------------------------------------------------------------------------------------------------------------------
; MODIFY PUBLISHER PANEL / FRAME

(def field_modifyPublisher_id (text :editable? false))
(def field_modifyPublisher_name (text))

(def modifyPublisher_panel (mig-panel :border (label/i18n :text_modifyPublisher_border)
                                      :constraints ["wrap 1" "[grow, fill]" "[fill]"]
                                      :items [[(mig-panel :constraints ["wrap 2" "[shrink 0]100px[200, grow, fill]" "[shrink 0]5px[]"]
                                                          :items [[(label/i18n :text_modifyPublisher_id)][field_modifyPublisher_id]
                                                                  [(label/i18n :text_modifyPublisher_name)][field_modifyPublisher_name]])]
                                              [(flow-panel :align :right
                                                           :items [(button :action button_modifyPublisher_save_action)])]]))

;-------------------------------------------------------------------------------------------------------------------------------
; MAIN FRAME

(def frame_main (frame :title (label/i18n :text_frame_main_title)
                       :width 800
                       :height 600
                       :menubar menu_frame_main
                       :content searchTitle_panel
                       :on-close :exit))

(log/info "start programm")
(actualizePublisherComboBoxModel)
(show! frame_main)

