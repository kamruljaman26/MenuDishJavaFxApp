package practicumopdracht.data;

import practicumopdracht.models.Menu;

import java.time.LocalDate;

public class DummyMenuDAO extends MenuDAO {

    @Override
    public boolean save() {
        //Note! The save-method of both classes do not need any logic, since we are data will never actually save with these DAOs.
        return false;
    }

    @Override
    public boolean load() {
        // Create 15 demo menus
        Menu menu1 = new Menu("Menu 1", LocalDate.of(2022, 4, 1));
        Menu menu2 = new Menu("Menu 2", LocalDate.of(2022, 5, 1));
        Menu menu3 = new Menu("Menu 3", LocalDate.of(2022, 6, 1));
        Menu menu4 = new Menu("Menu 4", LocalDate.of(2022, 7, 1));
        Menu menu5 = new Menu("Menu 5", LocalDate.of(2022, 8, 1));
        Menu menu6 = new Menu("Menu 6", LocalDate.of(2022, 9, 1));
        Menu menu7 = new Menu("Menu 7", LocalDate.of(2022, 10, 1));
        Menu menu8 = new Menu("Menu 8", LocalDate.of(2022, 11, 1));
        Menu menu9 = new Menu("Menu 9", LocalDate.of(2022, 12, 1));
        Menu menu10 = new Menu("Menu 10", LocalDate.of(2023, 1, 1));
        Menu menu11 = new Menu("Menu 11", LocalDate.of(2023, 2, 1));
        Menu menu12 = new Menu("Menu 12", LocalDate.of(2023, 3, 1));
        Menu menu13 = new Menu("Menu 13", LocalDate.of(2023, 4, 1));
        Menu menu14 = new Menu("Menu 14", LocalDate.of(2023, 5, 1));
        Menu menu15 = new Menu("Menu 15", LocalDate.of(2023, 6, 1));

        // Add the demo menus to the list
        menus.add(menu1);
        menus.add(menu2);
        menus.add(menu3);
        menus.add(menu4);
        menus.add(menu5);
        menus.add(menu6);
        menus.add(menu7);
        menus.add(menu8);
        menus.add(menu9);
        menus.add(menu10);
        menus.add(menu11);
        menus.add(menu12);
        menus.add(menu13);
        menus.add(menu14);
        menus.add(menu15);

        return true;
    }

}
