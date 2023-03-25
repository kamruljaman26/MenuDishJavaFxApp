package practicumopdracht.controllers;

import practicumopdracht.views.MenuView;
import practicumopdracht.views.View;


public class MenuController extends Controller {

    private MenuView view;

    public MenuController() {
        view = new MenuView();
    }


    @Override
    public View getView() {
        return this.view;
    }
}
