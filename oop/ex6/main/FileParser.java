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
    , CHAR ="char", COMMA=",", EQUAL="=", SPACE = "\\s+", RETURN = "return;"; // TODO DELETE UNUSED


    private final String MATCH_VARIABLE = "(final?)\\s*(int|double|char|String|boolean)\\s*(\\w)\\s*(=\\s*" +
            "([^>]*))?;";

    private final String MATCH_VARIABLE_SECCONDRY = "(\\w)\\s*(=\\s*([^>]*))?";

    private final Pattern VariablePattern = Pattern.compile(MATCH_VARIABLE);

    private final Pattern VariableSecconderyPattern = Pattern.compile(MATCH_VARIABLE_SECCONDRY);

    private final String MATCH_TYPE_PARAMETER = "((int|double|char|String|boolean)\\s+(([a-zA-Z])+\\s)|" +
            "(([a-zA-Z]|_)+\\s,\\s*))";

    private final String MATCH_TYPE_PARAMETER2 =  "(int|double|char|String|boolean)\\s+([a-zA-Z])+\\s*,?";

    private final Pattern typeParameterPattern = Pattern.compile(MATCH_TYPE_PARAMETER2);

    private final String MATCH_SCOPE = "void\\s+(\\b[a-zA-Z][_a-zA-Z0-9]*\\b)\\s*\\(" +
            "("+MATCH_TYPE_PARAMETER+")\\)\\s*\\{";

    private final String COMMENT_PATTERN = "(^\\/\\/).*";

    private final Pattern ScopePattern = Pattern.compile(MATCH_SCOPE);

    private Matcher scopeMatcher;

    private Matcher variableMatcher;

    /* the pattern object for comparing regex */
    private Pattern genericPatten;

    /* the matcher object for comparing regex */
    private Matcher genericMatcher;
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
     * this method parse the file for the first time. in this time, we look for global variables and
     * methods declaration, save those into the main Scope object.
     * @throws IllegalCodeException: in case the code has a problem that detected while the pre process.
     * @throws IOException: in case of I/O error.
     */
    public Scope preProcessFile() throws IllegalCodeException, IOException{
        String line = inputBuffer.readLine();
        Scope mainScope = new Scope(null,"main");
        Variable variable;
        String [] variableList;
        while (line != null) {
            // finding global variables
            variableList = line.split(",");
            variableMatcher = VariablePattern.matcher(variableList[0].trim());
            if (variableMatcher.matches()) {
                String type = variableMatcher.group(2);
                Boolean variableFinal = variableMatcher.group(1).equals(FINAL);
                String variableName = variableMatcher.group(3);
                String variableValue = variableMatcher.group(5);
                Boolean variableInitiated = variableList[0].contains(EQUAL);
                VariablesFactory.createVariable(type, variableFinal, variableName, variableValue, mainScope,
                        variableInitiated);
                for(int index = 1; index<variableList.length;index++){
                    variableMatcher = VariableSecconderyPattern.matcher(variableList[index].trim());
                    variableName = variableMatcher.group(3);
                    variableValue = variableMatcher.group(5);
                    variableInitiated = variableList[index].contains(EQUAL);
                    VariablesFactory.createVariable(type,variableFinal,variableName,variableValue,mainScope,
                            variableInitiated);
                }
            }
            scopeMatcher = ScopePattern.matcher(line);
            if(scopeMatcher.matches()){
               // this is passable new scope and we need to addvance the line to the end of the scope
                String scopeName = scopeMatcher.group(1);
                Scope scope = new Scope(mainScope,scopeName);
                String [] parametersList = scopeMatcher.group(2).split(",");
                String [] parametersType = new String[parametersList.length];
                int index = 0;
                for(String typeValueString: parametersList){
                    Matcher typeParamMatcher = typeParameterPattern.matcher(typeValueString);
                    String type = typeParamMatcher.group(1);
                    String variableName = typeParamMatcher.group(2);
                    VariablesFactory.createVariable(type, null, variableName,null,
                            scope,null);
                    parametersType[index] = type;
                    index++;
                }
                scope.setParameters(parametersType);
                // now we need to move to the end of the method by stack
            }


            line = inputBuffer.readLine();
        }
        return mainScope;
    }


    private void checkForComment(String line) throws IllegalCodeException {
        genericPatten = Pattern.compile(COMMENT_PATTERN);
        genericMatcher = genericPatten.matcher(line);
    }
}
