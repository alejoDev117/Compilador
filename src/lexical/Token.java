package lexical;

import java.util.ArrayList;
import java.util.List;

public class Token {
        private TokenType type;
        private String lexeme;

        public Token(TokenType type, String lexeme) {
            this.type = type;
            this.lexeme = lexeme;
        }


        @Override
        public String toString() {
            return "Token{" +
                    "type=" + type +
                    ", lexeme='" + lexeme + '\'' +
                    '}';
        }

}
