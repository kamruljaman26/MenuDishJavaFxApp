package practicumopdracht.exceptions;

/**
 * Custom exception for converting string to menu or dish object
 */
public class InvalidLineFormatException extends Exception{
    public InvalidLineFormatException(){
        super("InvalidLineFormatException:"+" inculpated file!");
    }
}
