package oop.ex6.main;

import oop.ex6.main.variables.Variable;
import oop.ex6.main.variables.VariablesFactory;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
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

    public static final String INT = "int", DOUBLE = "double", STRING = "String", FINAL = "final", BOOLEAN = "boolean", CHAR = "char", COMMA = ",", EQUAL = "=", SPACE = "\\s+", RETURN = "return;"; //TODO check for unused.

    private final String MATCH_NAME = "([a-zA-Z]|_)+\\w*";

    private final String MATCH_VARIABLE = "(final\\s+)?(int|double|char|String|boolean)\\s+([^>]*?)\\s*" +
            "(=\\s*([^>]*))?;";

    private final String MATCH_VARIABLE_SECCONDRY = "(\\w*)\\s*(=\\s*([^>]*))?";

    private final String MATCH_BRACKET = "(if|while|void)\\s*[^\\{\\}]*\\{";

    private final Pattern VariablePattern = Pattern.compile(MATCH_VARIABLE);

    private final Pattern VariableSecconderyPattern = Pattern.compile(MATCH_VARIABLE_SECCONDRY);

    private final String MATCH_TYPE_PARAMETER2 = "(final)?\\s*(int|double|char|String|boolean)" +
            "\\s*(\\w+)\\s*";

    private final Pattern typeParameterPattern = Pattern.compile(MATCH_TYPE_PARAMETER2);

    private final String MATCH_SCOPE = "void\\s+(\\b[a-zA-Z][_a-zA-Z0-9]*\\b)\\s*\\((\\s*" +
            MATCH_TYPE_PARAMETER2 +")?(\\s*,"+MATCH_TYPE_PARAMETER2+"\\s*)*\\)\\s*\\{";

    private final Pattern ScopePattern = Pattern.compile(MATCH_SCOPE);

    private Matcher scopeMatcher;

    private Matcher variableMatcher;

    private String filePath;

    private final String MATCH_EMPTY_LINE = "\\s*";

    private final String MATCH_COMMENT = "(^\\/\\/).*";

    private final Pattern commentPattern = Pattern.compile(MATCH_COMMENT);

    private final Pattern emptyLinePattern = Pattern.compile(MATCH_EMPTY_LINE);

    private final String MATCH_FUNC_CALL = "([a-zA-Z][_a-zA-Z0-9]*)\\s*\\(((\\s*[^>]*)?(\\s*,\\s*([^>]*)*)" +
            "\\))\\s*;";

    private final Pattern funcCallPattern = Pattern.compile(MATCH_FUNC_CALL);

    private final String MATCH_IF_WHILE_CALL = "(if|while)\\s*\\((\\s*[^>]*\\s*(\\s*(&&|\\|\\|)" +
            "\\s*-[^>]*\\s*)*)\\)\\s*\\{";

    private final Pattern ifWhilePattern = Pattern.compile(MATCH_IF_WHILE_CALL);

    private final String MATCH_RETURN = "\\s*return\\s*;";

    private final Pattern returnPattern = Pattern.compile(MATCH_RETURN);

    private final String MATCH_ASSIGN = MATCH_NAME + "\\s*=\\s*" + MATCH_NAME + "(," +
            "\\s*" + MATCH_NAME + "\\s*=\\s*" + MATCH_NAME + ")*;";

    private final Pattern assignPattern = Pattern.compile(MATCH_ASSIGN);

    private final String MATCH_CLOSE_PARENTHESES = "[}]";

    private final Pattern closeParenthesesPattern = Pattern.compile(MATCH_CLOSE_PARENTHESES);



    /* the matcher object for comparing regex */
    private Matcher genericMatcher;

    //will save the lines from the file into array
    private LinkedList<String> fileLines;


    /**
     * this constructor initialize the objects
     *
     * @param filePath : a path to the given code file to process.
     * @throws InOutException: in case that the file wasn't found.
     */
    public FileParser(String filePath) throws InOutException {
        try {
            this.filePath = filePath;
            Reader inputFile = new FileReader(filePath);
            inputBuffer = new BufferedReader(inputFile);
            fileLines = new LinkedList<>();
        } catch (FileNotFoundException e) {
            throw new InOutException();
        }
    }

    /**
     * this method close the buffer of the parser.
     *
     * @throws IOException : in case that the buffer can't be close.
     */
    public void closeParser() throws IOException {
        inputBuffer.close();
    }


    /**
     * this method pre process the file in order to get its methods and global variables
     * @return Scope the main scope
     * @throws IllegalCodeException - if there is a problem with the variables or the methods
     * @throws IOException - if there is a problem with the buffer reader of the file
     */
    public Scope preProcessFile() throws IllegalCodeException, IOException {
        Stack<String> bracket = new Stack<>();
        String line = inputBuffer.readLine();
        fileLines.add(line);
        Scope mainScope = new Scope(null, "main");
        String[] variableList;
        while (line != null) {
            // finding global variables
            variableList = line.split(",");
            genericMatcher = VariablePattern.matcher(variableList[0].trim());
            if (genericMatcher.matches()) {
                createGlobalVariable(mainScope, variableList);
            }
            scopeMatcher = ScopePattern.matcher(line);
            if (scopeMatcher.matches()) {
                line = handleNewScope(bracket, mainScope);
            }
            else {
                line = inputBuffer.readLine();
                fileLines.add(line);
            }
        }
        return mainScope;
    }

    /**
     * this method handles a new scope and create its signature
     * @param bracket - a Stack that holds the number of bracket to check if its legal
     * @param mainScope - the main scope of the function
     * @throws IllegalCodeException-if there is a problem with the variables or the methods
     * @throws IOException - if there is a problem with the buffer reader of the file
     */
    private String handleNewScope(Stack<String> bracket, Scope mainScope) throws IllegalCodeException,
            IOException {
        String line;
        bracket.push("{");//add bracket to the stack because we opened new scope
        // this is passable new scope and we need to advance the line to the end of the scope
        String scopeName = scopeMatcher.group(1);
        Scope scope = new Scope(mainScope, scopeName);
        String args = scopeMatcher.group(2);
        if(args != null) {
            String[] parametersList = scopeMatcher.group(2).split(",");
            for (String typeValueString : parametersList) {
                Matcher typeParamMatcher = typeParameterPattern.matcher(typeValueString);
                if (typeParamMatcher.matches()) {
                    Boolean variableFinal = variableMatcher.group(1).equals(FINAL);
                    String type = typeParamMatcher.group(2);
                    String variableName = typeParamMatcher.group(3);
                    VariablesFactory.createVariable(type, variableFinal, variableName, null, scope,
                            null);
                }
            }
        }
        Scopes.add(scope);
        // now we need to move to the end of the method by stack
        //checking that every bracket that opens is also closing
        line = inputBuffer.readLine();
        fileLines.add(line);
        while (!bracket.empty() && line != null) {
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
            fileLines.add(line);
        }
        if (!bracket.empty()) {
            throw new IllegalCodeException();
        }
        return line;
    }

    /**
     * this method create and add a new variable to the global variables list
     * @param mainScope - the main scope of the function
     * @param variableList - a list of variables to add
     * @throws IllegalCodeException - if a variable assignment isnt legal
     */
    private void createGlobalVariable(Scope mainScope, String[] variableList) throws IllegalCodeException {
        String type = genericMatcher.group(2);
        Boolean variableFinal =  genericMatcher.group(1) != null;
        String variableName = genericMatcher.group(3);
        String variableValue = genericMatcher.group(5);
        Boolean variableInitiated = variableList[0].contains(EQUAL);
        VariablesFactory.createVariable(type, variableFinal, variableName, variableValue, mainScope,
                variableInitiated);
        for (int index = 1; index < variableList.length; index++) {
            variableMatcher = VariableSecconderyPattern.matcher(variableList[index].trim());
            variableName = variableMatcher.group(3);
            variableValue = variableMatcher.group(5);
            variableInitiated = variableList[index].contains(EQUAL);
            VariablesFactory.createVariable(type, variableFinal, variableName, variableValue, mainScope,
                    variableInitiated);
        }
    }

    /**
     * this method will return the scope with the matching name
     * @param scopeName - the name of the socpe we want
     * @return - matching scope
     * @throws IllegalCodeException - if there is no scope with this name
     */
    private Scope bringScope(String scopeName) throws IllegalCodeException {
        for (Scope currentScope : Scopes) {
            if (currentScope.getName().equals(scopeName)) {
                return currentScope;
            }
        }
        throw new BadCodeException("Error: method doesn't exist");
    }

    /**
     * the main method of FilePearser it will parse the file and check for illegal coding lines if it find
     * one it will throw exception
     * @throws IllegalCodeException - if the code is illegal
     * @throws IOException - if there is a problem with the file reading
     */
    public void fileProcess() throws IllegalCodeException, IOException {
        Scope currentScope = preProcessFile();
        String line;
        line = fileLines.pop();
        while (line != null){
            boolean matchFlag = false;
            genericMatcher = emptyLinePattern.matcher(line.trim());
            if (genericMatcher.matches()) {
                matchFlag = true;
            }
            genericMatcher = VariablePattern.matcher(line.trim());
            if (genericMatcher.matches()) {
                matchFlag = true;
                if (!currentScope.getName().equals("main")) {
                    // if the current scope isnt main we need to know those variables
                    String[] variableList = line.split(",");
                    createGlobalVariable(currentScope, variableList);
                }
            }
            genericMatcher = ScopePattern.matcher(line.trim());
            if (genericMatcher.matches()) {
                matchFlag = true;
                String scopeName = genericMatcher.group(1);
                currentScope = bringScope(scopeName);
            }
            genericMatcher = closeParenthesesPattern.matcher(line.trim());
            if (genericMatcher.matches()) {
                matchFlag = true;
                currentScope = currentScope.getOuterScope();
            }
            genericMatcher = assignPattern.matcher(line.trim());
            if (genericMatcher.matches()) {
                matchFlag = true;
                String[] variableList = line.split(",");
                for (String assigment : variableList) {
                    String[] variables = assigment.split("=");
                    String variable1 = variables[0];
                    String value = variables[1];
                    Variable var = currentScope.getVariable(variable1.trim());
                    if (var == null || var.getFinal()) {
                        throw new BadCodeException("Error: cannot assign to this variable variable");
                    }
                    var.checkVariable(value.trim());
                }
                line =  fileLines.pop();
            }
            genericMatcher = ifWhilePattern.matcher(line.trim());
            if(genericMatcher.matches()){
                checkForUnsupportedMainOperation(currentScope);
                matchFlag = true;
                String scopeType = genericMatcher.group(1);
                String [] parameters = genericMatcher.group(2).split("&&|\\|\\|");
                currentScope = new IfWhile(currentScope,scopeType);
                currentScope.checkSignature(parameters);
            }
			 genericMatcher = commentPattern.matcher(line);
            if (genericMatcher.matches()) {
                matchFlag = true;
            }
            genericMatcher = returnPattern.matcher(line.trim());
            if (genericMatcher.matches()){
                matchFlag = true;
                checkForUnsupportedMainOperation(currentScope);
                line =  fileLines.pop();
                if (line == null) {
                    throw new BadCodeException("Error: a return statement in EOF with no brackets.");
                }
                genericMatcher = closeParenthesesPattern.matcher(line.trim());
                if (genericMatcher.matches()) {
                    currentScope = currentScope.getOuterScope();
                }
            }
            genericMatcher = funcCallPattern.matcher(line.trim());
            if (genericMatcher.matches()) {
                matchFlag = true;
                checkForUnsupportedMainOperation(currentScope);
                Scope functionScope = bringScope(scopeMatcher.group(1));
                String [] parametersList = scopeMatcher.group(2).split(",");
                functionScope.checkSignature(parametersList);
            }
            if(!matchFlag) {
                throw new BadCodeException("Error: Unsupported line of code.");
            }
			line = fileLines.pop();

        }
    }
    /**
     * this method checks if some operations occurred in the main Scope. in that case, throws an exception.
     * @param currentScope: the current scope of the program.
     * @throws BadCodeException: in case an operations (such as method call) occurred in the main scope.
     */
    private void checkForUnsupportedMainOperation(Scope currentScope) throws BadCodeException {
        if (currentScope.getName().equals("main")) {
            throw new BadCodeException("Error: there is a return statement in the main scope.");
        }
    }
}
