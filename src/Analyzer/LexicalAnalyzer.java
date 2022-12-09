package Analyzer;

import Util.NextToken;
import Util.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LexicalAnalyzer { // lexical analyzing class.

    private Path file;

    public LexicalAnalyzer() {

    }

    public void setFilePath(String path) {
        this.file = Paths.get(path);
    }

    public ArrayList<Token> analyze() { // returns Lexical analyzed tokens;
        try (BufferedReader reader = Files.newBufferedReader(this.file)) {
            ArrayList<Token> lexims = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, ";| +-*/{},", true);
                while (st.hasMoreElements()) {
                    String token = st.nextToken();
                    if (token.isBlank()) continue;

                    NextToken type;

                    type = getTokenType(token);
                    lexims.add(new Token(type, token));
                }
            }

            return lexims;
        } catch (IOException e) {
            System.out.println("File not Found");
            e.printStackTrace();
        }
        return null;
    }


    private NextToken getTokenType(String token) {
        switch (token) {
            case "variable":
                return NextToken.VARIABLE;
            case "{":
                return NextToken.LEFT_BRACE;
            case "}":
                return NextToken.RIGHT_BRACE;
            case "call":
                return NextToken.CALL;
            case "print_ari":
                return NextToken.PRINT;
            case ";":
                return NextToken.SEMI_COLON;
            case ",":
                return NextToken.COMMA;
            default:
                if (token.matches("^[a-zA-Z0-9]*$")) {
                    return NextToken.IDENT;
                }
                return NextToken.NOT_FOUND;
        }
    }
}
