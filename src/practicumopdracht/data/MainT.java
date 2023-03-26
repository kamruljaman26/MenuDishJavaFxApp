package practicumopdracht.data;

import practicumopdracht.models.Dish;
import practicumopdracht.models.Menu;

public class MainT {
    public static void main(String[] args) {
        DummyMenuDAO menuDAO = new DummyMenuDAO();
        menuDAO.load();
        DummyDishDAO dishDAO = new DummyDishDAO();
        dishDAO.load();

/*        for (Menu menu:menuDAO.getAll()){
            System.out.println(menu);
        }

        for (Dish dish:dishDAO.getAll()){
            System.out.println(dish);
        }*/

        for (Dish dish:dishDAO.getAllFor(menuDAO.getById(1))){
            System.out.println(dish);
        }
    }
}
