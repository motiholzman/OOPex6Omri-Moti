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
                parser.fileProcess();// creating the first parse of the file so we know all the
                // functions and global variables
                //FIXME add here more logic.
                parser.closeParser();
                System.out.println(Sjavac.LEGAL_CODE);
            }
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

//    public int readFile() {
//        try {
//            File javaFile = new File(filePath);
//            FileReader javaFileReader = new FileReader(javaFile);
//            BufferedReader javaBufferReader = new BufferedReader(javaFileReader);
//            // first parse
//            String line;
//            Pattern variable = Pattern.compile("");// TODO
//            Pattern scope = Pattern.compile("");// TODO
//            while ((line = (String)javaBufferReader.readLine()) != null) {
//                Matcher variableMatch = variable.matcher(line);
//                Matcher scopeMatch = scope.matcher(line);
//                if(variableMatch.matches()){
//                    //TODO check parameters and create variable
//                }
//                if(scopeMatch.matches()){
//                    //TODO check parameters and create scope
//                }
//
//
//            }
//                //closing the buffer
//                javaBufferReader.close();
//        } catch (FileNotFoundException e) {
//            closeFile(javaBufferReader);
//        } catch (IOException e) {
//            closeFile(javaBufferReader);
//
//        }
//
//    }
//
//    private void closeFile(BufferedReader br) {
//        try {
//            br.close();
//        } catch (IOException e) {
//            return;
//        }
//    }

}


