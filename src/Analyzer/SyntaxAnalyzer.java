package Analyzer;

import Util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SyntaxAnalyzer { // analyzing syntax for this code.
    private ArrayList<Token> tokenLines;
    private HashMap<String, Function> functionSet;

    public SyntaxAnalyzer(ArrayList<Token> tokenLines) {
        this.tokenLines = tokenLines;
        this.functionSet = new HashMap<>();
    }

    public HashMap<String, Function> analyze() {
        // function def
        Iterator<Token> iterator = tokenLines.iterator();
        while (iterator.hasNext()) {
            Token id = iterator.next();
            if (id.getToken_type() != NextToken.IDENT) {
                throw new IllegalArgumentException("Syntax ERROR");
            } else {
                if (functionSet.containsKey(id.getToken_value())) {
                    throw new IllegalArgumentException(ERROR_MESSAGE.DUPLICATE.getMessage() + id.getToken_value());
                }
                functionSet.put(id.getToken_value(), getFunction(iterator));
            }
        }
        return functionSet;
    }

    private Function getFunction(Iterator<Token> iterator) {
        ArrayList<Statement> function = new ArrayList<>();
        if (iterator.next().getToken_type() != NextToken.LEFT_BRACE) {
            throw new IllegalArgumentException("Syntax Error");
        } else {
            while (iterator.hasNext()) {
                Token nextToken = iterator.next();
                if (nextToken.getToken_type() == NextToken.VARIABLE) {
                    function.add(getVarDefinition(iterator));
                } else if (nextToken.getToken_type() == NextToken.CALL) {
                    function.add(getCallStatement(iterator));
                } else if (nextToken.getToken_type() == NextToken.IDENT) {
                    ArrayList<Token> result = new ArrayList<>();
                    result.add(nextToken);
                    function.add(new Statement(STATEMENT_TYPE.REFERENCE, result));
                } else if (nextToken.getToken_type() == NextToken.RIGHT_BRACE) {
                    break;
                } else if (nextToken.getToken_type() == NextToken.LEFT_BRACE) {
                    throw new IllegalArgumentException("Syntax ERROR");
                }
            }
        }
        return new Function(function);
    }

    private Statement getVarDefinition(Iterator<Token> iterator) {
        ArrayList<Token> stateLine = new ArrayList<>();
        while (true) {
            Token nextToken = iterator.next();
            if (nextToken.getToken_type() == NextToken.SEMI_COLON) {
                break;
            } else if (nextToken.getToken_type() == NextToken.IDENT) {
                if (stateLine.contains(nextToken)) {
                    throw new IllegalArgumentException(ERROR_MESSAGE.DUPLICATE.getMessage() + nextToken.getToken_value());
                }
                stateLine.add(nextToken);
            } else if (nextToken.getToken_type() == NextToken.CALL ||
                    nextToken.getToken_type() == NextToken.PRINT ||
                    nextToken.getToken_type() == NextToken.RIGHT_BRACE ||
                    nextToken.getToken_type() == NextToken.LEFT_BRACE) {
                throw new IllegalArgumentException("Syntax Error");
            }
        }
        return new Statement(STATEMENT_TYPE.VAR_DEF, stateLine);
    }

    private Statement getCallStatement(Iterator<Token> iterator) {
        ArrayList<Token> result = new ArrayList<>();
        result.add(iterator.next());
        return new Statement(STATEMENT_TYPE.CALL, result);
    }


}
