import Analyzer.Interpreter;
import Analyzer.LexicalAnalyzer;
import Analyzer.SyntaxAnalyzer;
import Util.Token;

import java.util.ArrayList;
import java.util.Queue;

public class Application {
    public static void main(String[] args) {
        for (String arg :
                args) {
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
            lexicalAnalyzer.setFilePath(arg);
            SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer.analyze());
            Interpreter interpreter = new Interpreter(syntaxAnalyzer.analyze());
            interpreter.out();
            interpreter.run();
        }
    }
}
