package syntax;

import utils.exceptions.ParseException;
import utils.loader.GrammarLoader;
import lexical.Token;
import lexical.TokenType;

import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int current = 0;
    private GrammarLoader grammarLoader;

    public Parser(List<Token> tokens, GrammarLoader grammarLoader) {
        this.tokens = tokens;
        this.grammarLoader = grammarLoader;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) {
            return advance();
        }
        Token token = peek(); // Obtener el token que causó el error
        throw new ParseException(buildErrorMessage(token, message, type));
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    public ASTNode parse() {
        return parseNotification();
    }

    private ASTNode parseNotification() {
        String rule = grammarLoader.getRule("<Notification>");
        if (rule == null) throw new ParseException("Rule for <Notification> not found.");

        ASTNode node = new ASTNode(TokenType.NOTIFICATION, "Notification");
        consume(TokenType.NOTIFICATION, "Se esperaba 'NOTIFICATION'");
        consume(TokenType.LLAVE, "Se esperaba '{' después de 'NOTIFICATION'");

        parseIdentifier(node);
        parseAutor(node);
        parseTitle(node);
        parseContent(node);
        parseCreationDate(node);
        parseState(node);
        parseScheduledDate(node);
        parseRecipients(node);

        consume(TokenType.LLAVE, "Se esperaba '}' después de <Notification>");
        return node;
    }

    private void parseIdentifier(ASTNode node) {
        consume(TokenType.IDENTIFICADOR, "Se esperaba 'IDENTIFICADOR'");
        consume(TokenType.LLAVE, "Se esperaba '{' después de 'IDENTIFICADOR'");
        consume(TokenType.UUID, "Se esperaba 'UUID'");
        consume(TokenType.LLAVE, "Se esperaba '}' después de 'UUID'");
    }

    private void parseAutor(ASTNode node) {
        consume(TokenType.AUTOR, "Se esperaba 'AUTOR'");
        consume(TokenType.LLAVE, "Se esperaba '{' después de 'AUTOR'");
        node.addChild(parsePersona());
        consume(TokenType.LLAVE, "Se esperaba '}' después de 'persona'");
    }

    private void parseTitle(ASTNode node) {
        consume(TokenType.TITULO, "Se esperaba 'TITULO'");
        consume(TokenType.LLAVE, "Se esperaba '{' después de 'TITULO'");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "Se esperaba 'CADENA'").getLexeme()));
        consume(TokenType.LLAVE, "Se esperaba '}' después de 'CADENA'");
    }

    private void parseContent(ASTNode node) {
        consume(TokenType.CONTENIDO, "Se esperaba 'CONTENIDO'");
        consume(TokenType.LLAVE, "Se esperaba '{' después de 'CONTENIDO'");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "Se esperaba 'CADENA'").getLexeme()));
        consume(TokenType.LLAVE, "Se esperaba '}' después de 'CADENA'");
    }

    private void parseCreationDate(ASTNode node) {
        consume(TokenType.FECHACREACION, "Se esperaba 'FECHACREACION'");
        consume(TokenType.LLAVE, "Se esperaba '{' después de 'FECHACREACION'");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "Se esperaba 'CADENA'").getLexeme()));
        consume(TokenType.LLAVE, "Se esperaba '}' después de 'CADENA'");
    }

    private void parseState(ASTNode node) {
        consume(TokenType.ESTADO, "Se esperaba 'ESTADO'");
        consume(TokenType.LLAVE, "Se esperaba '{' después de 'ESTADO'");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "Se esperaba 'CADENA'").getLexeme()));
        consume(TokenType.LLAVE, "Se esperaba '}' después de 'CADENA'");
    }

    private void parseScheduledDate(ASTNode node) {
        consume(TokenType.FECHAPROGRAMADA, "Se esperaba 'FECHAPROGRAMADA'");
        consume(TokenType.LLAVE, "Se esperaba '{' después de 'FECHAPROGRAMADA'");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "Se esperaba 'CADENA'").getLexeme()));
        consume(TokenType.LLAVE, "Se esperaba '}' después de 'CADENA'");
    }

    private void parseRecipients(ASTNode node) {
        consume(TokenType.LLAVE, "Se esperaba '{' antes de <destinatarios>");
        while (check(TokenType.NOMBRE)) {
            node.addChild(parsePersona());
        }
        consume(TokenType.LLAVE, "Se esperaba '}' después de <destinatarios>");
    }

    private ASTNode parsePersona() {
        ASTNode node = new ASTNode(TokenType.PERSONA, "Persona");
        consume(TokenType.NOMBRE, "Se esperaba 'NOMBRE'");
        consume(TokenType.LLAVE, "Se esperaba '{' después de 'NOMBRE'");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "Se esperaba 'CADENA'").getLexeme()));
        consume(TokenType.LLAVE, "Se esperaba '}' después de 'CADENA'");

        consume(TokenType.CORREO, "Se esperaba 'CORREO'");
        consume(TokenType.LLAVE, "Se esperaba '{' después de 'CORREO'");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "Se esperaba 'CADENA'").getLexeme()));
        consume(TokenType.LLAVE, "Se esperaba '}' después de 'CADENA'");

        return node;
    }

    private String buildErrorMessage(Token token, String message, TokenType expectedType) {
        return String.format("Error de sintaxis en línea %d, columna %d: %s. Se esperaba '%s', pero se encontró '%s'.",
                token.getLine(), token.getColumn(), message, expectedType.name(), token.getLexeme());
    }
}
