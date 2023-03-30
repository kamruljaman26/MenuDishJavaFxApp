package practicumopdracht.data;

import practicumopdracht.IO.TextIO;
import practicumopdracht.exceptions.InvalidLineFormatException;
import practicumopdracht.models.Dish;
import practicumopdracht.models.Menu;

import java.io.IOException;
import java.util.List;

public class TextMenuDAO extends MenuDAO {
    private static final String FILENAME = "menu.txt";

    @Override
    public boolean save() {
        try {
            TextIO<Menu> textIO = new TextIO<>();
            textIO.writeMenuFile(FILENAME, this);
            return true;
        } catch (IOException | InvalidLineFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean load() {
        try {
            TextIO<Menu> textIO = new TextIO<>();
            menus.clear();
            menus.addAll(textIO.readFromFile(FILENAME, new Menu()));
            return true;
        } catch (IOException | InvalidLineFormatException e) {
            e.printStackTrace();
            return false;
        }
    }
}
