package practicumopdracht.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import practicumopdracht.MainApplication;
import practicumopdracht.views.DishView;
import practicumopdracht.views.MenuView;
import practicumopdracht.views.View;

public class DishController extends Controller{

    private DishView view;

    public DishController() {
        view = new DishView();
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
            MainApplication.switchController(new MenuController());
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
