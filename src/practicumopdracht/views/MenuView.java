package practicumopdracht.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import practicumopdracht.models.Menu;

import java.time.LocalDate;

public class MenuView extends View {

    private Label menuNameLb, releaseDateLb;
    private TextField menuNameTf;
    private DatePicker releaseDateDp;
    private Button opslaanBt, nieuwBt, verwijderenBt, switchBt;
    private ListView<Menu> menuLv;
    private Parent root;
    private MenuItem loadItem, saveItem, exitItem;


    @Override
    protected Parent initializeView() {

        // Set up the menu details
        menuNameLb = new Label("Menu: ");
        menuNameTf = new TextField();
        menuNameTf.setPrefWidth(460);
        HBox menuNameDetails = new HBox(menuNameLb, menuNameTf);
        menuNameDetails.setSpacing(15);

        // Set up the release date details
        releaseDateLb = new Label("Release: ");
        releaseDateDp = new DatePicker();
        releaseDateDp.setPrefWidth(462);
        HBox releaseDetails = new HBox(releaseDateLb, releaseDateDp);
        releaseDetails.setSpacing(5);

        // Set up the opslaan button details
        opslaanBt = new Button("Opslaan");
        opslaanBt.setPrefWidth(520);
        HBox opslaanDetails = new HBox(opslaanBt);
        opslaanDetails.setAlignment(Pos.CENTER);

        // Set up the list view for the menu
        menuLv = new ListView<>();
        menuLv.setPrefWidth(550);
        HBox menuLvDetails = new HBox(menuLv);
        menuLvDetails.setAlignment(Pos.CENTER);
        menuLvDetails.setMaxHeight(300);

        // Set up the other buttons
        nieuwBt = new Button("Nieuw");
        verwijderenBt = new Button("Verwijderen");
        switchBt = new Button("Terug naar overzicht");
        nieuwBt.setPrefWidth(125);
        verwijderenBt.setPrefWidth(160);
        switchBt.setPrefWidth(210);
        HBox buttonDetails = new HBox(nieuwBt, verwijderenBt, switchBt);
        buttonDetails.setSpacing(10);
        buttonDetails.setAlignment(Pos.CENTER);

        // create menubar and items
        MenuBar menuBar = new MenuBar();
        javafx.scene.control.Menu fileMenu = new javafx.scene.control.Menu("Bestand");
        loadItem = new MenuItem("Laden");
        saveItem = new MenuItem("Opslaan");
        exitItem = new MenuItem("Afsluiten");
        fileMenu.getItems().addAll(loadItem, saveItem, new SeparatorMenuItem(), exitItem);
        menuBar.getMenus().addAll(fileMenu);

        // Add all child nodes into VBox 'all'
        VBox all = new VBox(menuNameDetails, releaseDetails, opslaanDetails, menuLvDetails);
        all.setPadding(new Insets(10, 10, 10, 10));
        all.setSpacing(10);


        // creating border pane
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setTop(menuBar);
        borderPane.setCenter(all);
        borderPane.setBottom(buttonDetails);

        // Set the root to be the grid pane and return it
        root = borderPane;

        return root;
    }

    public TextField getMenuNameTf() {
        return menuNameTf;
    }

    public DatePicker getReleaseDateDp() {
        return releaseDateDp;
    }

    public Button getOpslaanBt() {
        return opslaanBt;
    }

    public Button getNieuwBt() {
        return nieuwBt;
    }

    public Button getVerwijderenBt() {
        return verwijderenBt;
    }

    public Button getSwitchBt() {
        return switchBt;
    }

    public ListView<Menu> getMenuLv() {
        return menuLv;
    }

    public MenuItem getLoadItem() {
        return loadItem;
    }

    public void setLoadItem(MenuItem loadItem) {
        this.loadItem = loadItem;
    }

    public MenuItem getSaveItem() {
        return saveItem;
    }

    public void setSaveItem(MenuItem saveItem) {
        this.saveItem = saveItem;
    }

    public MenuItem getExitItem() {
        return exitItem;
    }

    public void setExitItem(MenuItem exitItem) {
        this.exitItem = exitItem;
    }
}
