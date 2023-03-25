package practicumopdracht.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import practicumopdracht.models.Dish;
import practicumopdracht.models.Menu;

public class DishView extends View{

    private Label menuSoortlb, dishLb, priceLb, cookingTimeLb, veganLb;
    private ComboBox<Menu> menuSoort;
    private TextArea dishTa;
    private TextField priceTf;
    private TextField cookingTimeTf;
    private CheckBox veganCb;
    private Button opslaanBt, nieuwBt, verwijderenBt, switchBt;
    private ListView<Dish> dishLv;
    private Parent root;


    @Override
    protected Parent initializeView() {

        // Set up the menuSoort details
        menuSoortlb = new Label("Menu:");
        menuSoort = new ComboBox();
        menuSoort.setPrefWidth(480);
        HBox menuSoortDetails = new HBox(menuSoortlb, menuSoort);
        menuSoortDetails.setSpacing(5);

        // Set up the dish details
        dishLb = new Label("Dish:");
        dishTa = new TextArea();
        dishTa.setMaxHeight(5);
        dishTa.setPrefWidth(480);
        HBox dishDetails = new HBox(dishLb, dishTa);
        dishDetails.setSpacing(13);

        // Set up the price details
        priceLb = new Label("Price:");
        priceTf = new TextField();
        priceTf.setPrefWidth(480);
        HBox priceDetails = new HBox(priceLb, priceTf);
        priceDetails.setSpacing(10);

        // Set up the cooking time details
        cookingTimeLb = new Label("Cooking Time:");
        cookingTimeTf = new TextField();
        cookingTimeTf.setPrefWidth(440);
        HBox cookingTimeDetails = new HBox(cookingTimeLb, cookingTimeTf);
        cookingTimeDetails.setSpacing(5);

        // Set up the vegan details
        veganLb = new Label("Vegan: ");
        veganCb = new CheckBox();
        veganCb.setAlignment(Pos.CENTER_RIGHT);
        HBox veganDetails = new HBox(veganLb, veganCb);
        veganDetails.setSpacing(5);

        // Set up the opslaan button details
        opslaanBt = new Button("Opslaan");
        opslaanBt.setPrefWidth(520);
        HBox opslaanDetails = new HBox(opslaanBt);
        opslaanDetails.setAlignment(Pos.CENTER);

        // Set up the list view for the dish
        dishLv = new ListView<>();
        dishLv.setPrefWidth(540);
        HBox menuLvDetails = new HBox(dishLv);
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
        VBox all = new VBox(menuSoortDetails, dishDetails, priceDetails, cookingTimeDetails, veganDetails,
                opslaanDetails, menuLvDetails, buttonDetails);
        all.setSpacing(10);

        // Set up the grid pane and add 'all' node into it
        GridPane allBoxes = new GridPane();
        allBoxes.setPadding(new Insets(10, 10, 10, 10));
        allBoxes.add(all,0,0);

        // Set the root to be the grid pane and return it
        root = allBoxes;
        return root;
    }

    public ComboBox<Menu> getMenuSoort() {
        return menuSoort;
    }

    public TextArea getDishTa() {
        return dishTa;
    }

    public TextField getPriceTf() {
        return priceTf;
    }

    public TextField getCookingTimeTf() {
        return cookingTimeTf;
    }

    public CheckBox getVeganCb() {
        return veganCb;
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

    public ListView<Dish> getDishLv() {
        return dishLv;
    }
}
