package oop.ex6.main;

import java.io.*;

/**
 * this class represents a parser of a file.
 */
public class FileParser {

    /* a private buffer to rad a file with.*/
    private BufferedReader inputBuffer;

    /**
     * this constructor initialize the objects
     * @param filePath : a path to the given code file to process.
     * @throws InOutException: in case that the file wasn't found.
     */
    public FileParser(String filePath) throws InOutException {
        try {
            Reader inputFile = new FileReader(filePath);
            inputBuffer = new BufferedReader(inputFile);
        }
        catch (FileNotFoundException e) {
            throw new InOutException();
        }
    }

    /**
     * this method close the buffer of the parser.
     * @throws IOException : in case that the buffer can't be close.
     */
    public void closeParser() throws IOException{
        inputBuffer.close();
    }

    /**
     *
     */
    public void prepossessFile() throws IllegalCodeException, IOException{
        String line = inputBuffer.readLine();
        Scope mainScope = new Scope(null);
        while (line != null) {

        }
    }
}
