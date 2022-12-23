package Analyzer;

import Util.*;

import java.util.*;

public class Interpreter {  // Syntax Analyzing class
    private Deque<ARI> programStack;
    private HashMap<String, Function> functionSet;

    public Interpreter(HashMap<String, Function> functionSet) {
        this.functionSet = functionSet;
        this.programStack = new ArrayDeque<>();
    }

    public void run() {
        pushFunction("main", null, null);
    }

    private void pushFunction(String functionName, String returnAddress, String dynamicLink) {
        hasFunction(functionName);
        ARI ari = new ARI(); // initializing ari in function
        if (returnAddress != null && dynamicLink != null) { // if function has return and dynamic links
            pushLinks(ari, returnAddress, dynamicLink);
        }
        Iterator<Statement> iterator = functionSet.get(functionName).iter();
        while (iterator.hasNext()) {
            Statement statement = iterator.next();
//            statement.out();
            if (statement.type == STATEMENT_TYPE.CALL) { // another function call
                programStack.push(ari);
                pushFunction(statement.getCall().getToken_value(), functionName + " " + statement.count, "null");
            } else if (statement.type == STATEMENT_TYPE.VAR_DEF) { // defining variables
                pushVariables(ari, statement);
            } else if (statement.type == STATEMENT_TYPE.PRINT) {
                programStack.push(ari);
                printAri();
            }
        }
    }

    private void pushLinks(ARI ari, String returnAddress, String dynamicLink) {
        ari.pushReturnAddress(returnAddress);
        ari.pushDynamicLink(dynamicLink);
    }

    private void pushVariables(ARI ari, Statement variableStatement) {
        for (Token T :
                variableStatement.getStatement()) {
            occupiedName(T.getToken_value());
            if (T.getToken_type() == NextToken.IDENT) {
                ari.pushLocalVariable(T.getToken_value());
            }
        }
    }


    // validation
    private void hasFunction(String functionName) {
        if (!functionSet.containsKey(functionName)) {
            throw new RuntimeException("No starting function.");
        }
    }

    private void occupiedName(String variableName) {
        if (functionSet.containsKey(variableName)) {
            throw new RuntimeException(ERROR_MESSAGE.DUPLICATE.getMessage() + variableName);
        }
    }

    public void out() { // logging
        for (String key :
                functionSet.keySet()) {
            System.out.println("function Name: " + key);
            functionSet.get(key).out();
            System.out.println("-----------------------------------");
        }
    }

    private void printAri() {
        for (ARI ari : programStack) {
            for (Word word : ari.wordStack) {
                word.out();
            }
        }
    }

}
