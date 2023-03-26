package practicumopdracht.controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import practicumopdracht.MainApplication;
import practicumopdracht.data.DishDAO;
import practicumopdracht.data.MenuDAO;
import practicumopdracht.models.Dish;
import practicumopdracht.models.Menu;
import practicumopdracht.views.DishView;
import practicumopdracht.views.View;
import java.util.Optional;

public class DishController extends Controller {

    private DishView view;
    private Menu selectedMenu;
    private ComboBox<Menu> menuCb;
    private DishDAO dishDAO;
    private MenuDAO menuDAO;
    private boolean toSave = false;

    public DishController(Menu menu) {
        dishDAO = MainApplication.getDishDAO();
        menuDAO = MainApplication.getMenuDAO();
        selectedMenu = menu;
        view = new DishView();

        initializeListeners();
        initializeComboBox();
        initializeListView();
    }

    // setup combo box
    private void initializeComboBox() {
        menuCb = view.getMenuSoort();
        menuCb.setItems(FXCollections.observableList(menuDAO.getAll()));
        menuCb.getSelectionModel().select(selectedMenu);
        menuCb.setOnAction(event -> {
            // update selected model
            selectedMenu = menuCb.getValue();
            initializeListView();
        });
    }

    // init data in listview
    private void initializeListView() {
        view.getDishLv().setItems(FXCollections.observableList(dishDAO.getAllFor(selectedMenu)));

        // set up a listener to detect when a model is selected in the ListView
        view.getDishLv().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // update the input fields with the selected model's data
            if (newValue != null) {
                view.getDishTa().setText(newValue.getDishName());
                view.getPriceTf().setText(Double.toString(newValue.getPrice()));
                view.getCookingTimeTf().setText(Integer.toString(newValue.getAverageCookingTimeInMinutes()));
                view.getVeganCb().setSelected(newValue.isVegan());

                toSave = false;
            }
        });
    }

    // handle button actions
    private void initializeListeners() {

        // save/update button
        view.getOpslaanBt().setOnAction(event -> {
            // validate fields
            if (validateInputFields()) {

                // init dish with values form files
                Dish dish = new Dish(view.getDishTa().getText(),
                        Double.parseDouble(view.getPriceTf().getText()),
                        Integer.parseInt(view.getCookingTimeTf().getText()),
                        view.getVeganCb().isSelected(),
                        selectedMenu
                );

                if (!toSave && view.getDishLv().getSelectionModel().getSelectedItem() != null) {
                    // if a model is selected in the ListView, update the selected model with the new data
                    Dish selectedDish = view.getDishLv().getSelectionModel().getSelectedItem();
                    selectedDish.setDishName(dish.getDishName());
                    selectedDish.setPrice(dish.getPrice());
                    selectedDish.setAverageCookingTimeInMinutes(dish.getAverageCookingTimeInMinutes());
                    selectedDish.setVegan(dish.isVegan());

                    initializeListView(); // update
                } else {
                    dishDAO.addOrUpdate(dish);
                    initializeListView();
                }
            }
        });

        // new button
        view.getNieuwBt().setOnAction(event -> {
            view.getDishLv().getSelectionModel().clearSelection(); // reset selection
            view.getDishTa().clear(); // clear dish name field
            view.getPriceTf().clear(); // clear price field
            view.getCookingTimeTf().clear(); // clear cooking time field
            view.getVeganCb().setSelected(false); // clear vegan checkbox
            toSave = true; // for handle update operation
        });

        // remove button
        view.getVerwijderenBt().setOnAction(event -> {
            // Get selected Dish
            Dish selectedDish = view.getDishLv().getSelectionModel().getSelectedItem();
            if (selectedDish != null) {
                // Prompt user for confirmation
                Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDelete.setContentText("Weet je zeker dat je dit gerecht wilt verwijderen?");
                Optional<ButtonType> result = confirmDelete.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Remove selected Dish from DAO and ListView
                    dishDAO.remove(selectedDish);
                    view.getDishLv().getItems().remove(selectedDish);
                }
            } else {
                // No Dish selected, show error message
                Alert noSelection = new Alert(Alert.AlertType.ERROR);
                noSelection.setContentText("Selecteer een gerecht om te verwijderen.");
                noSelection.showAndWait();
            }
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

        if (menuCb.getValue() == null) {
            displayErrorAlert("Please select a menu");
            return false;
        }

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
