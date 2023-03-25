package practicumopdracht.controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import practicumopdracht.views.MenuView;
import practicumopdracht.views.View;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import practicumopdracht.views.MenuView;
import practicumopdracht.views.View;

public class MenuController extends Controller {

    private MenuView view;

    public MenuController() {
        view = new MenuView();
        initializeListeners();
    }

    private void initializeListeners() {
        view.getOpslaanBt().setOnAction(event -> {
            displayAlert("Opslaan");
        });

        view.getNieuwBt().setOnAction(event -> {
            displayAlert("Nieuw");
        });

        view.getVerwijderenBt().setOnAction(event -> {
            displayAlert("Verwijderen");
        });

        view.getSwitchBt().setOnAction(event -> {
            displayAlert("Terug naar overzicht");
        });
    }

    private void displayAlert(String buttonName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, buttonName + " button was pressed!", ButtonType.OK);
        alert.showAndWait();
    }

    @Override
    public View getView() {
        return this.view;
    }
}

