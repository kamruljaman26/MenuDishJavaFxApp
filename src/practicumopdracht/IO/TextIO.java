package practicumopdracht.IO;

import practicumopdracht.data.DAO;
import practicumopdracht.data.MenuDAO;
import practicumopdracht.exceptions.InvalidLineFormatException;
import practicumopdracht.models.Menu;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will handle read write operation for our master model
 */
public class TextIO<T extends Writeable<T>> {

    public TextIO() {
    }

    // read line by line and convert to objects for given file path/name
    public List<T> readFromFile(String filename, Writeable<T> writer) throws InvalidLineFormatException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<T> objectsList = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            T object = writer.toRead(line);
            objectsList.add(object);
        }

        reader.close();

        return objectsList;
    }

    // write line by line given DAO objects, will be written in given file path/name
    public void writeMenuFile(String filename, DAO<T> dao) throws InvalidLineFormatException, IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Writeable<T> writeable : dao.getAll()) {
            writer.write(writeable.toWrite());
            writer.newLine();
        }

        writer.close();
    }

    // delete everything from file
    public void clear(String filename) throws InvalidLineFormatException, IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("");
    }
}
