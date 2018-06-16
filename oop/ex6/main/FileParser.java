package oop.ex6.main;

import oop.ex6.main.variables.*;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class represents a parser of a file.
 */
public class FileParser {

    /* a private buffer to rad a file with.*/
    private BufferedReader inputBuffer;

    public static final String INT="int",DOUBLE="double", STRING="String",FINAL="final", BOOLEAN="boolean"
    , CHAR="char", COMMA=",", EQUAL="=", SPACE = "\\s+", RETURN = "return;";


    private final String MATCH_VARIABLE = "(final?)(int|double|char|String|boolean)\\s*(\\w)\\s*(=\\s*" +
            "([^>]*))?;";

    private final String MATCH_VARIABLE_SECCONDRY = "(\\w)\\s*(=\\s*([^>]*))?";

    private final Pattern VariablePattern = Pattern.compile(MATCH_VARIABLE);

    private final Pattern VariableSecconderyPattern = Pattern.compile(MATCH_VARIABLE_SECCONDRY);

    private final String MATCH_SCOPE = "";//TODO



    private Matcher variableMatcher ;

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
     * this method adds a variable to the scope
     * @param type the type of the variable
     * @param variableFinal if the variable is final
     * @param variableName the variable name
     * @param variableValue the variable value
     * @param scope the scope we want to add the value to
     * @param variableInitiated if the value is initiated
     * @throws IllegalCodeException
     */
    private void variableFromLine(String type,Boolean variableFinal,String variableName,String
            variableValue,Scope scope,Boolean variableInitiated) throws IllegalCodeException {
        Variable variable;
        if (type.equals(INT)) {
            variable = new IntVariable(variableName, variableValue, variableFinal,variableInitiated);
        } else if (type.equals(DOUBLE)) {
            variable = new DoubleVariable(variableName, variableValue, variableFinal,variableInitiated);
        } else if (type.equals(CHAR)) {
            variable = new CharVariable(variableName, variableValue, variableFinal,variableInitiated);
        } else if (type.equals(STRING)) {
            variable = new StringVariable(variableName, variableValue, variableFinal,variableInitiated);
        } else if (type.equals(BOOLEAN)) {
            variable = new BooleanVariable(variableName, variableValue, variableFinal,variableInitiated);
        } else {// not a valid variable
            throw new IllegalCodeException();
        }
        scope.addVariable(variable);
    }



    /**
     *
     */
    public Scope preProssessFile() throws IllegalCodeException, IOException{
        String line = inputBuffer.readLine();
        Scope mainScope = new Scope(null);
        Variable variable;
        while (line != null) {
            // finding global variables
            String [] variableList = line.split(",");
            variableMatcher = VariablePattern.matcher(variableList[0].trim());
            if (variableMatcher.matches()) {
                String type = variableMatcher.group(2);
                Boolean variableFinal = variableMatcher.group(1).equals(FINAL);
                String variableName = variableMatcher.group(3);
                String variableValue = variableMatcher.group(5);
                Boolean variableInitiated = variableList[0].contains(EQUAL);
                variableFromLine(type,variableFinal,variableName,variableValue,mainScope,variableInitiated);
                for(int index = 1; index<variableList.length;index++){
                    variableMatcher = VariableSecconderyPattern.matcher(variableList[index].trim());
                    variableName = variableMatcher.group(3);
                    variableValue = variableMatcher.group(5);
                    variableInitiated = variableList[index].contains(EQUAL);
                    variableFromLine(type,variableFinal,variableName,variableValue,mainScope,variableInitiated);
                }
            }
            if(){
               // this is passable new scope and we need to addvance the line to the end of the scope
            }


            line = inputBuffer.readLine();
        }
        return mainScope;
    }
}
