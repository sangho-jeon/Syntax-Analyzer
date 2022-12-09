package Util;

import java.util.ArrayList;
import java.util.Iterator;

public class Function {
    private ArrayList<Statement> function;
    public Function(ArrayList<Statement> function){
        this.function = function;
    }

    public Iterator<Statement> iter() {
        return function.iterator();
    }

    public void out(){
        int count = 0;
        for (Statement state:
             function) {
            count++;
            System.out.println("statement no: " + count);
            state.out();
        }
    }
}
