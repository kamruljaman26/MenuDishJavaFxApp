package practicumopdracht.data;

import practicumopdracht.models.Dish;
import practicumopdracht.models.Menu;

public class DummyDishDAO extends DishDAO {

    @Override
    public boolean save() {
        //Note! The save-method of both classes do not need any logic, since we are data will never actually save with these DAOs.hjkl
        return false;
    }

    @Override
    public boolean load() {
        DAO<Menu> menuDAO = new DummyMenuDAO();
        menuDAO.load();

        dishes.add(new Dish("Sushi Platter", 18.5, 45, false, menuDAO.getById(0)));
        dishes.add(new Dish("Spicy Noodles", 9.25, 15, true, menuDAO.getById(0)));
        dishes.add(new Dish("Spaghetti Bolognese", 12.5, 25, false, menuDAO.getById(1)));
        dishes.add(new Dish("Caesar Salad", 8.75, 15, true, menuDAO.getById(1)));
        dishes.add(new Dish("Beef Burger", 14.0, 20, false, menuDAO.getById(2)));
        dishes.add(new Dish("Vegetable Curry", 11.5, 30, true, menuDAO.getById(2)));
        dishes.add(new Dish("Fish and Chips", 13.25, 25, false, menuDAO.getById(3)));
        dishes.add(new Dish("Chicken Satay", 9.75, 15, false, menuDAO.getById(3)));
        dishes.add(new Dish("Ratatouille", 10.5, 35, true, menuDAO.getById(4)));
        dishes.add(new Dish("Pizza Margherita", 11.0, 20, true, menuDAO.getById(4)));
        dishes.add(new Dish("Beef Stroganoff", 15.5, 40, false, menuDAO.getById(5)));
        dishes.add(new Dish("Veggie Burger", 12.0, 20, true, menuDAO.getById(5)));
        dishes.add(new Dish("Lamb Kebab", 13.75, 30, false, menuDAO.getById(6)));
        dishes.add(new Dish("Shepherd's Pie", 12.25, 35, false, menuDAO.getById(6)));
        dishes.add(new Dish("Sushi Platter", 18.5, 45, false, menuDAO.getById(7)));
        dishes.add(new Dish("Spicy Noodles", 9.25, 15, true, menuDAO.getById(7)));

        return true;
    }


}
