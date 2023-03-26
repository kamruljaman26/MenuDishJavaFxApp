package practicumopdracht.controllers;

import javafx.scene.control.*;
import practicumopdracht.MainApplication;
import practicumopdracht.models.Menu;
import practicumopdracht.views.MenuView;
import practicumopdracht.views.View;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;

public class MenuController extends Controller {

    private MenuView view;

    public MenuController() {
        view = new MenuView();
        initializeListeners();
    }

    // handle button actions
    private void initializeListeners() {

        // save button
        view.getOpslaanBt().setOnAction(event -> {
            if (validateInputFields()) {
                TextField menuName = view.getMenuNameTf();
                DatePicker releaseDate = view.getReleaseDateDp();

                Menu menu = new Menu(menuName.getText(), releaseDate.getValue());
                displayInfoAlert(menu.toString());
            }
        });

        view.getNieuwBt().setOnAction(event -> {
            displayInfoAlert("Nieuw button was pressed!");
        });

        view.getVerwijderenBt().setOnAction(event -> {
            displayInfoAlert("Verwijderen button was pressed!");
        });

        // switch button
        view.getSwitchBt().setOnAction(event -> {
            MainApplication.switchController(new DishController());
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

