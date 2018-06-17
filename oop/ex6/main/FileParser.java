package oop.ex6.main;

import oop.ex6.main.variables.VariablesFactory;

import java.io.*;
import java.util.HashSet;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * this class represents a parser of a file.
 */
public class FileParser {

    private HashSet<Scope> Scopes = new HashSet<>();

    /* a private buffer to rad a file with.*/
    private BufferedReader inputBuffer;

    public static final String INT="int",DOUBLE="double", STRING="String",FINAL="final", BOOLEAN="boolean"
            , CHAR="char", COMMA=",", EQUAL="=", SPACE = "\\s+", RETURN = "return;"; //TODO check for unused.


    private final String MATCH_VARIABLE = "(final?)\\s*(int|double|char|String|boolean)\\s*(\\w)\\s*(=\\s*" +
            "([^>]*))?;";

    private final String MATCH_VARIABLE_SECCONDRY = "(\\w)\\s*(=\\s*([^>]*))?";

    private final String MATCH_BRACKET = "(if|while|void)\\s*[^\\{\\}]*\\{";

    private final Pattern VariablePattern = Pattern.compile(MATCH_VARIABLE);

    private final Pattern VariableSecconderyPattern = Pattern.compile(MATCH_VARIABLE_SECCONDRY);

    private final String MATCH_TYPE_PARAMETER = "((int|double|char|String|boolean)\\s+(([a-zA-Z])+\\s)|" +
            "(([a-zA-Z]|_)+\\s,\\s*))";

    private final String MATCH_TYPE_PARAMETER2 =  "(int|double|char|String|boolean)\\s+([a-zA-Z])+\\s*,?";

    private final Pattern typeParameterPattern = Pattern.compile(MATCH_TYPE_PARAMETER2);

    private final String MATCH_SCOPE = "void\\s+(\\b[a-zA-Z][_a-zA-Z0-9]*\\b)\\s*\\(" +
            "("+MATCH_TYPE_PARAMETER+")\\)\\s*\\{";

    private final Pattern ScopePattern = Pattern.compile(MATCH_SCOPE);

    private Matcher scopeMatcher;

    private Matcher variableMatcher;

    private String filePath;

    private final String MATCH_EMPTY_LINE = "\\s*";

    private final String MATCH_COMMENT = "(^\\/\\/).*";

    private final Pattern commentPattern = Pattern.compile(MATCH_COMMENT);

    private final Pattern emptyLinePattern = Pattern.compile(MATCH_EMPTY_LINE);

    private final String MATCH_FUNC_CALL = "([a-zA-Z][_a-zA-Z0-9]*)\\s*\\((\\s*(final)?(\\s*[\\w*=])*" +
            "(\\s*,\\s*(final)?(\\s*[\\w*=])*)*)\\)\\s*;";

    private final Pattern funcCallPattern = Pattern.compile(MATCH_FUNC_CALL);

    private final String MATCH_IF_WHILE_CALL = "(if|while)\\s*\\((\\w*(\\s*(&&|\\|\\|)\\s*\\w*)*)\\)\\s*\\{";

    private final Pattern ifWhilePattern = Pattern.compile(MATCH_IF_WHILE_CALL);

    private final String MATCH_RETURN = "\\s*return\\s*;";

    private final Pattern returnPattern = Pattern.compile(MATCH_RETURN);

    private final String MATCH_ASSIGN = "\\w*\\s*=\\s*\\w*(,\\w*\\s*=\\s*\\w*)*;";

    private final Pattern assignPattern = Pattern.compile(MATCH_ASSIGN);

    private final String MATCH_CLOSE_PARENTHESES = "[}]";

    private final Pattern closeParenthesesPattern = Pattern.compile(MATCH_CLOSE_PARENTHESES);



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
            this.filePath = filePath;
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
    public Scope preProcessFile() throws IllegalCodeException, IOException{
        Stack<String> bracket = new Stack<>();
        String line = inputBuffer.readLine();
        Scope mainScope = new Scope(null,"main");
        String [] variableList;
        while (line != null) {
            // finding global variables
            variableList = line.split(",");
            variableMatcher = VariablePattern.matcher(variableList[0].trim());
            if (variableMatcher.matches()) {
                createGlobalVariable(mainScope, variableList);
            }
            scopeMatcher = ScopePattern.matcher(line);
            if(scopeMatcher.matches()){
                handleNewScope(bracket, mainScope);
            }
            line = inputBuffer.readLine();
        }
        return mainScope;
    }

    private void handleNewScope(Stack<String> bracket, Scope mainScope) throws IllegalCodeException, IOException {
        String line;
        bracket.push("{");//add bracket to the stack because we opened new scope
        // this is passable new scope and we need to advance the line to the end of the scope
        String scopeName = scopeMatcher.group(1);
        Scope scope = new Scope(mainScope,scopeName);
        String [] parametersList = scopeMatcher.group(2).split(",");
        String [] parametersType = new String[parametersList.length];
        int index = 0;
        for(String typeValueString: parametersList){
            Matcher typeParamMatcher = typeParameterPattern.matcher(typeValueString);
            String type = typeParamMatcher.group(1);
            String variableName = typeParamMatcher.group(2);
            VariablesFactory.createVariable(type,null, variableName,null, scope,
                    null);
            parametersType[index] = type;
            index++;
        }
        scope.setParameters(parametersType);
        Scopes.add(scope);
        // now we need to move to the end of the method by stack
        //checking that every bracket that opens is also closing
        line = inputBuffer.readLine();
        while(!bracket.empty() && line != null) {
            if (line.trim().matches(MATCH_BRACKET)) {
                bracket.push("{");
            } else if (line.trim().equals("}")) {
                try {
                    bracket.pop();
                } catch (NullPointerException e) {
                    throw new IllegalCodeException();
                }
            }
            line = inputBuffer.readLine();
        }
        if(!bracket.empty()){
            throw new IllegalCodeException();
        }
    }

    private void createGlobalVariable(Scope mainScope, String[] variableList) throws IllegalCodeException {
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
            VariablesFactory.createVariable(type, variableFinal, variableName, variableValue, mainScope,
                    variableInitiated);
        }
    }

    public void fileProcess()throws IllegalCodeException, IOException{
        Scope currentScope = preProcessFile();
        Reader inputFile = new FileReader(filePath);
        inputBuffer = new BufferedReader(inputFile);
        String line;
        line = inputBuffer.readLine();
        while (line != null){


            line = inputBuffer.readLine();
        }


    }

}
