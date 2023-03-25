package practicumopdracht;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import practicumopdracht.controllers.Controller;
import practicumopdracht.controllers.DishController;
import practicumopdracht.controllers.MenuController;
import practicumopdracht.views.DishView;
import practicumopdracht.views.MenuView;
import practicumopdracht.views.View;

public class MainApplication extends Application {

    private final String TITLE = "Practicumopdracht OOP2 - " + Main.studentNaam;
    private final int WIDTH = 560;
    private final int HEIGHT = 475;
    private static Stage stage;

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

        Controller menuController = new DishController();
        switchController(menuController);
//        switchController(new DishController()); menu is master model
    }

    public static void switchController (Controller view) {
        stage.setScene(new Scene(view.getView().getRoot()));
        stage.show();
    }
}
