import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

public class Interpreter {
    private Queue<Token> inputStream;
    public Interpreter(Queue<Token> inputStream){
        this.inputStream = inputStream;
    }

    private boolean hasMain(){
        
        for (Token t:
             inputStream) {
            if (Objects.equals(t.getToken_value(), "main")) {
                return true;
            }
        }
        return false;
    }
}
