package lexical;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String source;
    private int position;
    private int line;
    private int column;
    private List<Token> tokens;

    public Lexer(String source) {
        this.source = source;
        this.position = 0;
        this.line = 1; // Comienza en la primera línea
        this.column = 1; // Comienza en la primera columna
        this.tokens = new ArrayList<>();
    }

    public List<Token> tokenize() {
        while (position < source.length()) {
            char currentChar = source.charAt(position);

            // Manejar espacios en blanco, incluidos espacios y tabulaciones
            if (Character.isWhitespace(currentChar)) {
                if (currentChar == '\n') {
                    line++; // Incrementar la línea
                    column = 1; // Reiniciar la columna al inicio de la nueva línea
                } else {
                    column++; // Contar cada espacio o tabulación como un avance de columna
                }
                position++;
                continue;
            }

            int tokenStartCol = column;

            switch (currentChar) {
                case '{':
                case '}':
                    tokens.add(new Token(TokenType.LLAVE, String.valueOf(currentChar), line, column));
                    position++;
                    column++;
                    break;

                case '%':
                    if (isUUID()) {
                        tokens.add(new Token(TokenType.UUID, extractUUID(), line, tokenStartCol));
                    } else {
                        tokens.add(new Token(TokenType.PORCENTAJE, "%", line, column));
                        position++;
                        column++;
                    }
                    break;

                case '"':
                    tokens.add(new Token(TokenType.CADENA, extractString(), line, tokenStartCol));
                    break;

                default:
                    if (Character.isLetter(currentChar)) {
                        String word = extractWord();
                        tokens.add(new Token(getTokenTypeForWord(word), word, line, tokenStartCol));
                    } else {
                        tokens.add(new Token(TokenType.DESCONOCIDO, Character.toString(currentChar), line, column));
                        position++;
                        column++;
                    }
            }
        }

        // Añadir el token EOF al final
        tokens.add(new Token(TokenType.EOF, "", line, column));
        return tokens;
    }

    // Verificar si el token es un UUID
    private boolean isUUID() {
        int startPosition = position + 1;
        int endPosition = startPosition + 36;

        if (endPosition < source.length() && source.charAt(endPosition) == '%') {
            String potentialUUID = source.substring(startPosition, endPosition);
            return potentialUUID.matches("[0-9a-fA-F\\-]{36}");
        }

        return false;
    }

    // Extraer el UUID del texto
    private String extractUUID() {
        position++;
        String uuid = source.substring(position, position + 36);
        position += 36;
        column += 38; // UUID tiene 36 caracteres más dos símbolos de porcentaje
        position++;
        return uuid;
    }

    // Extraer una cadena entre comillas
    private String extractString() {
        StringBuilder sb = new StringBuilder();
        position++;
        column++;
        while (position < source.length() && source.charAt(position) != '"') {
            sb.append(source.charAt(position));
            position++;
            column++;
        }
        position++;
        column++;
        return sb.toString();
    }

    // Extraer una palabra del texto
    private String extractWord() {
        StringBuilder sb = new StringBuilder();
        while (position < source.length() && (Character.isLetter(source.charAt(position)) || source.charAt(position) == '.')) {
            sb.append(source.charAt(position));
            position++;
            column++;
        }
        return sb.toString();
    }

    // Obtener el tipo de token basado en la palabra extraída
    private TokenType getTokenTypeForWord(String word) {
        switch (word) {
            case "NOTIFICATION":
                return TokenType.NOTIFICATION;
            case "IDENTIFICADOR":
                return TokenType.IDENTIFICADOR;
            case "AUTOR":
                return TokenType.AUTOR;
            case "TITULO":
                return TokenType.TITULO;
            case "CONTENIDO":
                return TokenType.CONTENIDO;
            case "FECHACREACION":
                return TokenType.FECHACREACION;
            case "ESTADO":
                return TokenType.ESTADO;
            case "FECHAPROGRAMADA":
                return TokenType.FECHAPROGRAMADA;
            case "nombre":
                return TokenType.NOMBRE;
            case "correo":
                return TokenType.CORREO;
            default:
                if (word.matches("[0-9a-fA-F\\-]{36}")) {
                    return TokenType.UUID;
                } else if (word.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    return TokenType.FECHA;
                } else {
                    return TokenType.DESCONOCIDO;
                }
        }
    }
}
