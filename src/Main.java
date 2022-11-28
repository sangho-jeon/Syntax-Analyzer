import java.util.ArrayList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
        lexicalAnalyzer.setFilePath(args[0]);
        ArrayList<Queue<Token>> result = lexicalAnalyzer.Lexical();
        for (Queue<Token> line:
             result) {
            while(!line.isEmpty()){
                line.poll().out();
            }
            System.out.println("--------------------");
        }
    }
}
