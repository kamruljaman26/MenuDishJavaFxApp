package practicumopdracht.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import practicumopdracht.models.Menu;

public class MenuView extends View{

    private Label menuNameLb,releaseDateLb;
    private TextField menuNameTf;
    private DatePicker releaseDateDp;
    private Button opslaanBt, nieuwBt, verwijderenBt, switchBt;
    private ListView<Menu> menuLv;
    private Parent root;

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

        // Add all child nodes into VBox 'all'
        VBox all = new VBox(menuNameDetails, releaseDetails, opslaanDetails, menuLvDetails, buttonDetails);
        all.setSpacing(10);

        // Set up the grid pane and add 'all' node into it
        GridPane allBoxes = new GridPane();
        allBoxes.setPadding(new Insets(10, 10, 10, 10));
        allBoxes.add(all,0,0);

        // Set the root to be the grid pane and return it
        root = allBoxes;

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
}
