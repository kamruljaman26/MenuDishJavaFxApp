package practicumopdracht.controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import practicumopdracht.MainApplication;
import practicumopdracht.data.MenuDAO;
import practicumopdracht.models.Menu;
import practicumopdracht.views.MenuView;
import practicumopdracht.views.View;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;

public class MenuController extends Controller {

    private MenuView view;
    private MenuDAO menuDAO;

    public MenuController() {
        view = new MenuView();
        initializeListeners();

        // init menu dao
        menuDAO = MainApplication.getMenuDAO();
        initializeListView();
    }

    //When starting the MasterController screen the ListView displays all the associated Master models by default.
    // To do this, use data binding in conjunction with the associated DAO. Override the toString method of your
    // models such that all data is visible in the ListViews
    private void initializeListView() {
        view.getMenuLv().setItems(FXCollections.observableList(menuDAO.getAll()));
    }

    // handle button actions
    private void initializeListeners() {
        view.getOpslaanBt().setOnAction(event -> {
            if (validateInputFields()) {
                TextField menuName = view.getMenuNameTf();
                DatePicker releaseDate = view.getReleaseDateDp();

                Menu menu = new Menu(menuName.getText(), releaseDate.getValue());
                menuDAO.addOrUpdate(menu);
                initializeListView(); // refresh list view
                displayInfoAlert(menu.toString());
            }
        });

        // new button
        view.getNieuwBt().setOnAction(event -> {
            displayInfoAlert("Nieuw button was pressed!");
        });

        // remove button
        view.getVerwijderenBt().setOnAction(event -> {
            Menu selectedMenu = view.getMenuLv().getSelectionModel().getSelectedItem();
            if (selectedMenu != null) {
                menuDAO.remove(selectedMenu);
                initializeListView(); // refresh list view
                displayInfoAlert("Menu removed: " + selectedMenu.toString());
            } else {
                displayErrorAlert("Selecteer een menu om te verwijderen");
            }
        });

        // switch button
        view.getSwitchBt().setDisable(true); // initially disable switch button
        view.getSwitchBt().setOnAction(event -> {
            Menu selectedMenu = view.getMenuLv().getSelectionModel().getSelectedItem();
            if (selectedMenu != null) {
                MainApplication.switchController(new DishController(selectedMenu));
            } else {
                displayErrorAlert("Selecteer een menu om verder te gaan");
            }
        });

        // enable switch button only when a menu is selected
        view.getMenuLv().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                view.getSwitchBt().setDisable(false);
            } else {
                view.getSwitchBt().setDisable(true);
            }
        });
    }

    // show info alert
    private void displayInfoAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, text, ButtonType.OK);
        alert.showAndWait();
    }

    // show error alert
    private void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // validate inputs
    private boolean validateInputFields() {
        TextField menuName = view.getMenuNameTf();
        DatePicker releaseDate = view.getReleaseDateDp();

        if (menuName.getText().isEmpty() || menuName.getText().trim().isEmpty()) {
            displayErrorAlert("Menu name is required");
            return false;
        }

        if (releaseDate.getValue() == null || releaseDate.getValue().isBefore(LocalDate.now())) {
            displayErrorAlert("Invalid release date");
            return false;
        }

        return true;
    }

    @Override
    public View getView() {
        return this.view;
    }
}

