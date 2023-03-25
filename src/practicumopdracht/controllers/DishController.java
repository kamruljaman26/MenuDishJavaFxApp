package practicumopdracht.controllers;

import practicumopdracht.views.DishView;
import practicumopdracht.views.MenuView;
import practicumopdracht.views.View;

public class DishController extends Controller{

    private DishView view;

    public DishController() {
        view = new DishView();
    }


    @Override
    public View getView() {
        return this.view;
    }
}
