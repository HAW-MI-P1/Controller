package de.haw.gui;

import de.haw.controller.Controller;
import de.haw.model.Person;
import de.haw.model.SearchHistory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SearchController {

    @FXML
    private TextField searchStringField;
    @FXML
    private Button newSearchButton;

    private Controller controller;

    // Reference to the main application
    private MainApp mainApp;
    private SearchHistory searchHistory;

    public void setController (Controller controller){
        this.controller = controller;
    }

    public void setSearchHistory(SearchHistory searchHistory){
        this.searchHistory = searchHistory;
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

        /**
         * Called when the user clicks on the New Search button.
         */
    @FXML
    private void handleNewSearch() {
        String searchString = searchStringField.getText();
        ObservableList<Person> personData = FXCollections.observableArrayList();
        searchStringField.clear();

        searchHistory.newHistory(searchString);

        personData.addAll(controller.search(searchString));
        //personData = showMockUpData(searchString);
        //personData = Controller.getDataBySearchString(searchString);
        mainApp.setPersonData(personData);
        mainApp.showPersonOverview();
    }

    private ObservableList<Person> showMockUpData(String searchString) {
        ObservableList<Person> personData = FXCollections.observableArrayList();
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
        return personData;
    }
}
