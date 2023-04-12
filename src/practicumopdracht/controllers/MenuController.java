package practicumopdracht.controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import practicumopdracht.MainApplication;
import practicumopdracht.data.DishDAO;
import practicumopdracht.data.MenuDAO;
import practicumopdracht.models.Menu;
import practicumopdracht.views.MenuView;
import practicumopdracht.views.View;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;
import java.util.Optional;

public class MenuController extends Controller {

    private MenuView view;
    private MenuDAO menuDAO;
    private DishDAO dishDAO;

    private boolean toSave = true;
    private Menu selectedMenu = null;

    public MenuController() {
        view = new MenuView();
        view.getReleaseDateDp().setValue(LocalDate.now());
        initializeListeners();

        // init menu dao
        menuDAO = MainApplication.getMenuDAO();
        dishDAO = MainApplication.getDishDAO();
        initializeListView();
        initializeMenuItemListeners();
    }

    // handle menu button functionality
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

    // When starting the MasterController screen the ListView displays all the associated Master models by default.
    // To do this, use data binding in conjunction with the associated DAO. Override the toString method of your
    // models such that all data is visible in the ListViews
    private void initializeListView() {
        view.getMenuLv().setItems(FXCollections.observableList(menuDAO.getAll()));
    }

    // handle button actions
    private void initializeListeners() {
        // save button
        view.getOpslaanBt().setOnAction(event -> {
            String errors = validateInputFields();
            if (errors.isEmpty()) {

                TextField menuName = view.getMenuNameTf();
                DatePicker releaseDate = view.getReleaseDateDp();

                if(toSave) {
                    Menu menu = new Menu(menuName.getText(), releaseDate.getValue());
                    menuDAO.addOrUpdate(menu);
                    initializeListView(); // refresh list view
                    displayAlert(String.format("Details\nName: %s\nDate: %s", menu.getMenuName(), menu.getReleaseDate()), Alert.AlertType.CONFIRMATION);
                }else {
                    int id = menuDAO.getIdFor(selectedMenu);
                    Menu menu = menuDAO.getById(id);
                    menu.setMenuName(menuName.getText());
                    menu.setReleaseDate(releaseDate.getValue());

                    initializeListView(); // refresh list view
                    displayAlert(String.format("Details\nName: %s\nDate: %s", menu.getMenuName(), menu.getReleaseDate()), Alert.AlertType.CONFIRMATION);
                }

                cleanFields();
            } else {
                displayAlert(errors, Alert.AlertType.ERROR);
            }
        });

        // new button
        view.getNieuwBt().setOnAction(event -> {
            cleanFields();
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
                view.getMenuNameTf().setText(newValue.getMenuName());
                view.getReleaseDateDp().setValue(newValue.getReleaseDate());

                view.getSwitchBt().setDisable(false);
                toSave = false;
                selectedMenu = newValue;
            } else {
                view.getSwitchBt().setDisable(true);
            }
        });
    }

    private void cleanFields() {
        toSave = true;

        view.getMenuNameTf().clear();
        view.getReleaseDateDp().setValue(LocalDate.now());

        //clear border color
        view.getMenuNameTf().setStyle("-fx-border-color: none ;");
        view.getReleaseDateDp().setStyle("-fx-border-color: none ;");
    }

    // show alert
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
            view.getMenuNameTf().setStyle("-fx-border-color: red ;");
        } else {
            view.getMenuNameTf().setStyle("-fx-border-color: none ;");
        }

        if (releaseDate.getValue() == null || releaseDate.getValue().isBefore(LocalDate.now())) {
            stringBuilder.append("Invalid release date\n");
            view.getReleaseDateDp().setStyle("-fx-border-color: red ;");
        } else {
            view.getReleaseDateDp().setStyle("-fx-border-color: none ;");
        }

        return stringBuilder.toString();
    }

    @Override
    public View getView() {
        return this.view;
    }
}

