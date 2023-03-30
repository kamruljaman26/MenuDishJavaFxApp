package practicumopdracht.IO;

import practicumopdracht.exceptions.InvalidLineFormatException;

/**

 A generic interface for objects that can be written to and read from a file.
 To use our TextIO features a model must be implemented Writeable<T>

 @param <T> the type of the object implementing the interface
 */
public interface Writeable<T> {

    /**

     Converts an object to a string that can be written to a file.
     @return a string representing the object
     */
    String toWrite();
    /**

     Converts a string from a file to an object of the implementing class.
     @param toRead the string to be converted to an object
     @return an object of the implementing class
     @throws InvalidLineFormatException if the string format is not valid for the implementing class
     */
    T toRead(String toRead) throws InvalidLineFormatException;
}
