package practicumopdracht.data;

import practicumopdracht.IO.TextIO;
import practicumopdracht.exceptions.InvalidLineFormatException;
import practicumopdracht.models.Dish;

import java.io.IOException;
import java.util.List;

public class TextDishDAO extends DishDAO {
    private static final String FILENAME = "dish.txt";

    @Override
    public boolean save() {
        try {
            TextIO<Dish> textIO = new TextIO<>();
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
            TextIO<Dish> textIO = new TextIO<>();
            dishes.clear();
            dishes.addAll(textIO.readFromFile(FILENAME, new Dish()));
            return true;
        } catch (IOException | InvalidLineFormatException e) {
            e.printStackTrace();
            return false;
        }
    }
}
