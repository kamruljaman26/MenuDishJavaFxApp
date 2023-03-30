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
        view.getReleaseDateDp().setValue(LocalDate.now());
        initializeListeners();

        // init menu dao
        menuDAO = MainApplication.getMenuDAO();
        initializeListView();
    }

    // When starting the MasterController screen the ListView displays all the associated Master models by default.
    // To do this, use data binding in conjunction with the associated DAO. Override the toString method of your
    // models such that all data is visible in the ListViews
    private void initializeListView() {
        view.getMenuLv().setItems(FXCollections.observableList(menuDAO.getAll()));
    }

    // handle button actions
    private void initializeListeners() {
        // week 5
        view.getOpslaanBt().setOnAction(event -> {
            String errors = validateInputFields();
            if (errors.isEmpty()) {
                TextField menuName = view.getMenuNameTf();
                DatePicker releaseDate = view.getReleaseDateDp();

                Menu menu = new Menu(menuName.getText(), releaseDate.getValue());
                menuDAO.addOrUpdate(menu);
                initializeListView(); // refresh list view
                displayAlert(menu.toString(), Alert.AlertType.CONFIRMATION);
            } else {
                displayAlert(errors, Alert.AlertType.ERROR);
            }
        });

        // new button
        view.getNieuwBt().setOnAction(event -> {
            view.getMenuNameTf().clear();
            view.getReleaseDateDp().setValue(LocalDate.now());
        });

        // remove button
        view.getVerwijderenBt().setOnAction(event -> {
            Menu selectedMenu = view.getMenuLv().getSelectionModel().getSelectedItem();
            if (selectedMenu != null) {
                menuDAO.remove(selectedMenu);
                initializeListView(); // refresh list view
                displayAlert("Menu removed: " + selectedMenu.toString(), Alert.AlertType.INFORMATION);
            } else {
                displayAlert("Selecteer een menu om te verwijderen", Alert.AlertType.ERROR);
            }
        });

        // switch button
        view.getSwitchBt().setDisable(true); // initially disable switch button
        view.getSwitchBt().setOnAction(event -> {
            Menu selectedMenu = view.getMenuLv().getSelectionModel().getSelectedItem();
            if (selectedMenu != null) {
                MainApplication.switchController(new DishController(selectedMenu));
            } else {
                displayAlert("Selecteer een menu om verder te gaan", Alert.AlertType.ERROR);
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
    private void displayAlert(String text, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, text, ButtonType.OK);
        alert.showAndWait();
    }


    // validate inputs and return all error at ones = week5
    private String validateInputFields() {
        TextField menuName = view.getMenuNameTf();
        DatePicker releaseDate = view.getReleaseDateDp();
        StringBuilder stringBuilder = new StringBuilder();

        if (menuName.getText().isEmpty() || menuName.getText().trim().isEmpty()) {
            stringBuilder.append("Menu name is required\n");
        }

        if (releaseDate.getValue() == null || releaseDate.getValue().isBefore(LocalDate.now())) {
            stringBuilder.append("Invalid release date\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public View getView() {
        return this.view;
    }
}

