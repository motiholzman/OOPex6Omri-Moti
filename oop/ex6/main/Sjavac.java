package oop.ex6.main;

import java.io.*;

public class Sjavac {

    /* this variable represents that the code is legal. */
    private static final int LEGAL_CODE = 0;

    /* this variable represents that the code is legal. */
    private static final int ILLEGAL_CODE = 1;

    /* this variable represents that IO error occurred. */
    private static final int IO_ERROR = 2;

    /**
     * this method runs the Sjavac verifier.
     * @param args : a list of arguments.
     */
    public static void main(String [] args) {
        try{
            if (args.length != 1) {
                throw new InOutException();
            }
            String filePath = args[0];
            FileParser parser  = null;
            try {
                parser = new FileParser(filePath);
                parser.fileProcess();// will process the file and check if the code is legal
                parser.closeParser();
                System.out.println(Sjavac.LEGAL_CODE);
            }
            // the code is illegal
            catch (IOException e) { // clean up
                if (parser != null) {
                    parser.closeParser();
                }
                throw new IOException();
            }
        }
        catch (IOException e) {
            System.out.println(Sjavac.IO_ERROR);
        }
        catch (IllegalCodeException e) {
            System.out.println(Sjavac.ILLEGAL_CODE);
        }
    }

}


