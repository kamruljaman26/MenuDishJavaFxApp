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

    private boolean toSave = true;
    private Dish selectedDish = null;

    public DishController(Menu menu) {
        dishDAO = MainApplication.getDishDAO();
        menuDAO = MainApplication.getMenuDAO();
        selectedMenu = menu;
        view = new DishView();

        initializeListeners();
        initializeComboBox();
        initializeListView();
        initializeMenuItemListeners();
    }

    private void initializeMenuItemListeners() {
        // exit menu item handler
        view.getExitItem().setOnAction(actionEvent -> {
            Alert saveAlert = new Alert(Alert.AlertType.CONFIRMATION);
            saveAlert.setTitle("Exit");
            saveAlert.setHeaderText("Do you want to save before exiting?");
            ButtonType saveButtonType = new ButtonType("Yes");
            ButtonType exitButtonType = new ButtonType("No");
            saveAlert.getButtonTypes().setAll(saveButtonType, exitButtonType);

            Optional<ButtonType> result = saveAlert.showAndWait();
            if (result.get() == saveButtonType) {
                // save before exiting
                menuDAO.save();
                dishDAO.save();
                System.exit(0);
            } else if (result.get() == exitButtonType) {
                // exit without saving
                System.exit(0);
            }
        });

        // save menu item handler
        view.getSaveItem().setOnAction(actionEvent -> {
            // ask user for permission before saving
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to save changes?", ButtonType.YES, ButtonType.NO);
            confirmation.setTitle("Save confirmation");
            confirmation.setHeaderText(null);
            Optional<ButtonType> result = confirmation.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                // save data
                boolean menuSaved = menuDAO.save();
                boolean dishSaved = dishDAO.save();

                // display result of save operation to user
                Alert saveResult = new Alert(Alert.AlertType.INFORMATION);
                saveResult.setTitle("Save result");
                saveResult.setHeaderText(null);

                if (menuSaved && dishSaved) {
                    saveResult.setContentText("Data saved successfully!");
                } else {
                    saveResult.setContentText("An error occurred while saving data.");
                }

                saveResult.showAndWait();
            }
        });


        // load menu item handler
        view.getLoadItem().setOnAction(actionEvent -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to load data?");
            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    // load data from file
                    boolean loadSuccessful = menuDAO.load() && dishDAO.load();
                    if (loadSuccessful) {
                        // update ListView with loaded data
                        initializeListView();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Data loaded successfully!");
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load data.");
                        alert.showAndWait();
                    }
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "An error occurred while loading data.");
                    alert.showAndWait();
                    e.printStackTrace();
                }
            }
        });
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

                selectedDish = newValue;
                toSave = false;
            }
        });
    }

    // handle button actions
    private void initializeListeners() {

        // save/update button
        view.getOpslaanBt().setOnAction(event -> {
            // validate fields
            String errors = validateInputFields();
            if (errors.isEmpty()) {
                if (toSave) {
                    // init dish with values form files
                    Dish dish = new Dish(view.getDishTa().getText(),
                            Double.parseDouble(view.getPriceTf().getText()),
                            Integer.parseInt(view.getCookingTimeTf().getText()),
                            view.getVeganCb().isSelected(),
                            selectedMenu
                    );


                    clearFields();
                    displayAlert("Saved Successfully!", String.format("Details\nName: %s\nPrice: %.2f\nCookingTime: %d Min\nIs Vegan: %b",
                                    dish.getDishName(), dish.getPrice(), dish.getAverageCookingTimeInMinutes(), dish.isVegan()),
                            Alert.AlertType.INFORMATION);
                    dishDAO.addOrUpdate(dish);
                    initializeListView(); // update list
                } else {
                    int idFor = dishDAO.getIdFor(selectedDish);
                    Dish dish = dishDAO.getById(idFor);
                    dish.setDishName(view.getDishTa().getText());
                    dish.setPrice(Double.parseDouble(view.getPriceTf().getText()));
                    dish.setAverageCookingTimeInMinutes(Integer.parseInt(view.getCookingTimeTf().getText()));
                    dish.setVegan(view.getVeganCb().isSelected());

                    clearFields();
                    displayAlert("Saved Successfully!", String.format("Details\nName: %s\nPrice: %.2f\nCookingTime: %d Min\nIs Vegan: %b",
                                    dish.getDishName(), dish.getPrice(), dish.getAverageCookingTimeInMinutes(), dish.isVegan()),
                            Alert.AlertType.INFORMATION);
                    initializeListView(); // update list
                }

            } else {
                displayAlert("Please fix following errors to add dish", errors, Alert.AlertType.ERROR);
            }
        });

        // new button
        view.getNieuwBt().setOnAction(event -> {
            clearFields();
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

    private void clearFields() {
        view.getDishLv().getSelectionModel().clearSelection(); // reset selection
        view.getDishTa().clear(); // clear dish name field
        view.getPriceTf().clear(); // clear price field
        view.getCookingTimeTf().clear(); // clear cooking time field
        view.getVeganCb().setSelected(false); // clear vegan checkbox
        toSave = true; // for handle update operation

        // clear filed colors
        view.getMenuSoort().setStyle("-fx-border-color: none ;");
        view.getDishTa().setStyle("-fx-border-color: none ;");
        view.getPriceTf().setStyle("-fx-border-color: none ;");
        view.getCookingTimeTf().setStyle("-fx-border-color: none ;");
    }

    // show alert
    private void displayAlert(String title, String text, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, text, ButtonType.OK);
        alert.setTitle(title);
        alert.showAndWait();
    }

    // validate inputs
    private String validateInputFields() {
        TextArea dishTa = view.getDishTa();
        TextField priceTf = view.getPriceTf();
        TextField cookingTimeTf = view.getCookingTimeTf();
        StringBuilder stringBuilder = new StringBuilder();

        if (menuCb.getValue() == null) {
            stringBuilder.append("Please select a menu\n");
            view.getMenuSoort().setStyle("-fx-border-color: red ;");
        } else {
            view.getMenuSoort().setStyle("-fx-border-color: none ;");
        }

        if (dishTa.getText().isEmpty() || dishTa.getText().trim().isEmpty()) {
            stringBuilder.append("Dish name is required\n");
            view.getDishTa().setStyle("-fx-border-color: red ;");
        } else {
            view.getDishTa().setStyle("-fx-border-color: none ;");
        }

        if (priceTf.getText().isEmpty() || !isDouble(priceTf.getText())) {
            stringBuilder.append("Price must be a valid number\n");
            view.getPriceTf().setStyle("-fx-border-color: red ;");
        } else {
            view.getPriceTf().setStyle("-fx-border-color: none ;");
        }

        if (cookingTimeTf.getText().isEmpty() || !isInteger(cookingTimeTf.getText())) {
            stringBuilder.append("Cooking time must be a valid integer\n");
            view.getCookingTimeTf().setStyle("-fx-border-color: red ;");
        } else {
            view.getCookingTimeTf().setStyle("-fx-border-color: none ;");
        }

        return stringBuilder.toString();
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
