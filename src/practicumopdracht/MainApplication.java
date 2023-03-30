package practicumopdracht;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import practicumopdracht.controllers.Controller;
import practicumopdracht.controllers.MenuController;
import practicumopdracht.data.*;


public class MainApplication extends Application {

    private final String TITLE = "Practicumopdracht OOP2 - " + Main.studentNaam;
    private final int WIDTH = 560;
    private final int HEIGHT = 475;
    private static Stage stage;
    private static MenuDAO menuDAO;
    private static DishDAO dishDAO;

    @Override
    public void start(Stage stage) {
        MainApplication.stage = stage;
        if(!Main.launchedFromMain) {
            System.err.println("Je moet deze applicatie opstarten vanuit de Main-class, niet de MainApplication-class!");
            System.exit(1337);

            return;
        }

        stage.setTitle(TITLE);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.show();

        // init Dummy DOAs
        menuDAO = new TextMenuDAO();
        menuDAO.load();
        dishDAO = new TextDishDAO();
        dishDAO.load();

        switchController(new MenuController());
    }

    public static void switchController (Controller view) {
        stage.setScene(new Scene(view.getView().getRoot()));
        stage.show();
    }

    public static MenuDAO getMenuDAO() {
        return menuDAO;
    }

    public static DishDAO getDishDAO() {
        return dishDAO;
    }
}
