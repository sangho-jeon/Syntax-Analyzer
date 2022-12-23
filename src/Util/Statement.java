package Util;

import java.util.ArrayList;

public class Statement {
    public STATEMENT_TYPE type;
    public int count;
    private ArrayList<Token> statement;

    public Statement(STATEMENT_TYPE type, ArrayList<Token> statement, int count){
        this.type = type;
        this.statement = statement;
    }

    public void out(){
        System.out.println(type);
        for (Token t:
             statement) {
            t.out();
        }
    }

    public Token getCall(){
        return statement.get(0);
    }

    public ArrayList<Token> getStatement() {
        return statement;
    }
}
