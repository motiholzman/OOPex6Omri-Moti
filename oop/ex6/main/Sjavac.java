package oop.ex6.main;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sjavac {

    private String filePath;

    public Sjavac(String filePath) {
        this.filePath = filePath;
    }

    public int readFile() {
        try {
            File javaFile = new File(filePath);
            FileReader javaFileReader = new FileReader(javaFile);
            BufferedReader javaBufferReader = new BufferedReader(javaFileReader);
            // first parse
            String line;
            Pattern variable = Pattern.compile("");// TODO
            Pattern scope = Pattern.compile("");// TODO
            while ((line = (String)javaBufferReader.readLine()) != null) {
                Matcher variableMatch = variable.matcher(line);
                Matcher scopeMatch = scope.matcher(line);
                if(variableMatch.matches()){
                    //TODO check parameters and create variable
                }
                if(scopeMatch.matches()){
                    //TODO check parameters and create scope
                }


            }
                //closing the buffer
                javaBufferReader.close();
        } catch (FileNotFoundException e) {
            closeFile(javaBufferReader);
        } catch (IOException e) {
            closeFile(javaBufferReader);

        }

    }

    private void closeFile(BufferedReader br) {
        try {
            br.close();
        } catch (IOException e) {
            return;
        }
    }

}


