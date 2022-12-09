package Analyzer;

import Util.*;

import java.lang.reflect.Array;
import java.util.*;

public class Interpreter {  // Syntax Analyzing class
    private Stack<ARI> programStack;
    private HashMap<String, Function> functionSet;

    public Interpreter(HashMap<String, Function> functionSet) {
        this.functionSet = functionSet;
        this.programStack = new Stack<>();
        hasMainFunction();
    }

    public void run() {
        pushFunction("main");
    }

    private void pushFunction(String functionName) {
        ARI ari = new ARI();
        Iterator<Statement> iterator = functionSet.get(functionName).iter();
        while (iterator.hasNext()) {
            Statement statement = iterator.next();
            if (statement.type == STATEMENT_TYPE.CALL) {
                pushSubprogram(statement.getCall().getToken_value());
            } else if (statement.type == STATEMENT_TYPE.VAR_DEF){

            }
        }
    }

    private void pushSubprogram(String subFunctionName) {

    }

    private void hasMainFunction() {
        if (!functionSet.containsKey("main")) {
            throw new RuntimeException("No starting function.");
        }
    }

    public void out() {
        for (String key :
                functionSet.keySet()) {
            System.out.println("function Name: " + key);
            functionSet.get(key).out();
            System.out.println("-----------------------------------");
        }
    }

}
