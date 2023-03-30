package practicumopdracht.views;

import javafx.scene.Parent;

public abstract class View {

    private Parent root;

    protected abstract Parent initializeView();

    public View() {
        root = initializeView();
    }

    public Parent getRoot(){
        return root;
    }
}
