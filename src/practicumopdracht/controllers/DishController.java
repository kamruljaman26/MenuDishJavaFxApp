package practicumopdracht.controllers;

import javafx.scene.control.*;
import practicumopdracht.MainApplication;
import practicumopdracht.models.Dish;
import practicumopdracht.models.Menu;
import practicumopdracht.views.DishView;
import practicumopdracht.views.MenuView;
import practicumopdracht.views.View;

import java.time.LocalDate;

public class DishController extends Controller {

    private DishView view;

    public DishController() {
        view = new DishView();
        initializeListeners();
    }

    // handle button actions
    private void initializeListeners() {

        // save button
        view.getOpslaanBt().setOnAction(event -> {
            if (validateInputFields()) {
                TextArea dishTa = view.getDishTa();
                TextField priceTf = view.getPriceTf();
                TextField cookingTimeTf = view.getCookingTimeTf();
                CheckBox veganCb = view.getVeganCb();

                ///    public Dish(String dishName, double price, int averageCookingTimeInMinutes, boolean isVegan, Menu belongsTo) {
                Dish menu = new Dish(dishTa.getText(),
                        Double.parseDouble(priceTf.getText()),
                        Integer.parseInt(cookingTimeTf.getText()),
                        veganCb.isSelected(),
                        null // in the current stage this will be null
                );
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
            MainApplication.switchController(new MenuController()); // change scene
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
        TextArea dishTa = view.getDishTa();
        TextField priceTf = view.getPriceTf();
        TextField cookingTimeTf = view.getCookingTimeTf();
        CheckBox veganCb = view.getVeganCb();

        if (dishTa.getText().isEmpty() || dishTa.getText().trim().isEmpty()) {
            displayErrorAlert("Dish name is required");
            return false;
        }

        if (priceTf.getText().isEmpty() || !isDouble(priceTf.getText())) {
            displayErrorAlert("Price must be a valid number");
            return false;
        }

        if (cookingTimeTf.getText().isEmpty() || !isInteger(cookingTimeTf.getText())) {
            displayErrorAlert("Cooking time must be a valid integer");
            return false;
        }

        return true;
    }

    private boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @Override
    public View getView() {
        return this.view;
    }
}
