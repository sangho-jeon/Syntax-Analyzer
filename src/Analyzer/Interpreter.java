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
        pushFunction("main", null, String.valueOf(programStack.size()));
    }

    private void pushFunction(String functionName, String returnAddress, String dynamicLink) {
        hasFunction(functionName);
        ARI ari = new ARI(functionName); // initializing ari in function
        int nextDynamic = getDynamicLink();
        if (returnAddress != null && dynamicLink != null) { // if function has return and dynamic links
            pushLinks(ari, returnAddress, dynamicLink);
        }
        Iterator<Statement> iterator = functionSet.get(functionName).iter();
        while (iterator.hasNext()) {
            Statement statement = iterator.next();
            if (statement.type == STATEMENT_TYPE.CALL) { // another function call
                programStack.push(ari);
                pushFunction(statement.getCall().getToken_value(), functionName + " " + (statement.count + 1), String.valueOf(nextDynamic));
            } else if (statement.type == STATEMENT_TYPE.VAR_DEF) { // defining variables
                pushVariables(ari, statement);
            } else if (statement.type == STATEMENT_TYPE.REFERENCE) { // reference finding
                System.out.println(functionName + ":" + statement.getStatement().get(0).getToken_value() + "=>" + findReference(statement));
            } else if (statement.type == STATEMENT_TYPE.PRINT) { // printing ari stack
                programStack.push(ari);
                printAri();
            }
        }
        programStack.pop();
    }

    private void pushLinks(ARI ari, String returnAddress, String dynamicLink) {
        ari.pushReturnAddress(returnAddress);
        ari.pushDynamicLink(dynamicLink);
    }

    private String findReference(Statement statement) {
        String targetVariable = statement.getStatement().get(0).getToken_value();
        Iterator<ARI> iterator = programStack.iterator();
        int recursiveCount = 0;
        while (iterator.hasNext()) {
            recursiveCount++;
            ARI ari = iterator.next();
            int offset = 0;
            for (Word W :
                    ari.wordStack) {
                offset++;
                if (Objects.equals(W.value, targetVariable)) {
                    return recursiveCount + "," + offset;
                }
            }
        }
        System.out.println("[ERROR] variable" + targetVariable + "not declared!!");
        return null;
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

//    public void out() { // logging
//        for (String key :
//                functionSet.keySet()) {
//            System.out.println("function Name: " + key);
//            functionSet.get(key).out();
//            System.out.println("-----------------------------------");
//        }
//    }

    private void printAri() {
        for (ARI ari : programStack) {
            System.out.println(ari.functionName + ":");
            for (Word word : ari.wordStack) {
                word.out();
            }
            System.out.println(" \n");
        }
    }

    private int getDynamicLink() {
        int count = 0;
        for (ARI ari :
                programStack) {
            count += ari.wordStack.size();
        }
        return count;
    }

}
