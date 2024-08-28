package lexical;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private String source;
    private int position;
    private List<Token> tokens;

    public Lexer(String source) {
        this.source = source;
        this.position = 0;
        this.tokens = new ArrayList<>();
    }

    //Metodo principal donde se llaman los demas metodos y se realiza toda la logica de tokeninzacion del codigo de ejemplo
    public List<Token> tokenize() {
        while (position < source.length()) {
            char currentChar = source.charAt(position);
            //Si el caracter es espacio o salto de linea lo descarta
            if (Character.isWhitespace(currentChar)) {
                position++;
                continue;
            }
            switch (currentChar) {
                case '{':
                case '}':
                    tokens.add(new Token(TokenType.LLAVE, String.valueOf(currentChar)));
                    position++;
                    break;
                case '%':
                    if (isUUID()) {
                        tokens.add(new Token(TokenType.UUID, extractUUID()));
                    } else {
                        tokens.add(new Token(TokenType.PORCENTAJE, "%"));
                        position++;
                    }
                    break;
                case '"':
                    tokens.add(new Token(TokenType.CADENA, extractString()));
                    break;
                default://En caso de que el caracter actual no sea ninguna de las opciones anteriores se llama extractWord
                    if (Character.isLetter(currentChar)) {
                        String word = extractWord();
                        tokens.add(new Token(getTokenTypeForWord(word), word));
                    } else {
                        tokens.add(new Token(TokenType.DESCONOCIDO, Character.toString(currentChar)));
                        position++;
                    }
            }
        }

        return tokens;
    }

    private boolean isUUID() {
        int startPosition = position + 1;
        int endPosition = startPosition + 36;

        if (endPosition < source.length() && source.charAt(endPosition) == '%') {
            String potentialUUID = source.substring(startPosition, endPosition);
            return potentialUUID.matches("[0-9a-fA-F\\-]{36}");
        }

        return false;
    }
   //Se extrae todo el valor del uuid que esta entre porcentajes
    private String extractUUID() {
        position++;
        String uuid = source.substring(position, position + 36);
        position += 36;
        position++;
        return uuid;
    }
   //Se extrae el valor del string que está entre comillas
    private String extractString() {
        StringBuilder sb = new StringBuilder();
        position++;
        while (position < source.length() && source.charAt(position) != '"') {
            sb.append(source.charAt(position));
            position++;
        }
        position++;
        return sb.toString();
    }
    //Aqui se extraen palabras que no son reservadas, como fecha,contenido de string etc
    private String extractWord() {
        StringBuilder sb = new StringBuilder();
        while (position < source.length() && (Character.isLetter(source.charAt(position)) || source.charAt(position) == '.')) {
            sb.append(source.charAt(position));
            position++;
        }
        return sb.toString();
    }
    //Asigna desde el enum el valor del token de la palabra extraida
    private TokenType getTokenTypeForWord(String word) {
        switch (word) {
            //En caso de que la palabra sea reservada
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
                //Si no, debe ser un UUID y una fecha con estos REX
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
