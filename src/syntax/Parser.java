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
        consume(TokenType.NOTIFICATION, "'NOTIFICATION'");
        consume(TokenType.LLAVE, "'{' después de 'NOTIFICATION'");

        parseIdentifier(node);
        parseAutor(node);
        parseTitle(node);
        parseContent(node);
        parseCreationDate(node);
        parseState(node);
        parseScheduledDate(node);
        parseRecipients(node);

        consume(TokenType.LLAVE, "'}' después de <Notification>");
        return node;
    }

    private void parseIdentifier(ASTNode node) {
        consume(TokenType.IDENTIFICADOR, "'IDENTIFICADOR'");
        consume(TokenType.LLAVE, "'{' después de 'IDENTIFICADOR'");
        consume(TokenType.UUID, "'UUID'");
        consume(TokenType.LLAVE, "'}' después de 'UUID'");
    }

    private void parseAutor(ASTNode node) {
        consume(TokenType.AUTOR, "'AUTOR'");
        consume(TokenType.LLAVE, "'{' después de 'AUTOR'");
        node.addChild(parsePersona());
        consume(TokenType.LLAVE, "'}' después de 'persona'");
    }

    private void parseTitle(ASTNode node) {
        consume(TokenType.TITULO, "'TITULO'");
        consume(TokenType.LLAVE, "'{' después de 'TITULO'");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "'CADENA'").getLexeme()));
        consume(TokenType.LLAVE, "'}' después de 'CADENA'");
    }

    private void parseContent(ASTNode node) {
        consume(TokenType.CONTENIDO, "'CONTENIDO'");
        consume(TokenType.LLAVE, "'{' después de 'CONTENIDO'");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "'CADENA'").getLexeme()));
        consume(TokenType.LLAVE, "'}' después de 'CADENA'");
    }

    private void parseCreationDate(ASTNode node) {
        consume(TokenType.FECHACREACION, "'FECHACREACION'");
        consume(TokenType.LLAVE, "'{' después de 'FECHACREACION'");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "'CADENA'").getLexeme()));
        consume(TokenType.LLAVE, "'}' después de 'CADENA'");
    }

    private void parseState(ASTNode node) {
        consume(TokenType.ESTADO, "'ESTADO'");
        consume(TokenType.LLAVE, "'{' después de 'ESTADO'");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "'CADENA'").getLexeme()));
        consume(TokenType.LLAVE, "'}' después de 'CADENA'");
    }

    private void parseScheduledDate(ASTNode node) {
        consume(TokenType.FECHAPROGRAMADA, "'FECHAPROGRAMADA'");
        consume(TokenType.LLAVE, "'{' después de 'FECHAPROGRAMADA'");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "'CADENA'").getLexeme()));
        consume(TokenType.LLAVE, "'}' después de 'CADENA'");
    }

    private void parseRecipients(ASTNode node) {
        consume(TokenType.LLAVE, "'{' antes de <destinatarios>");
        while (check(TokenType.NOMBRE)) {
            node.addChild(parsePersona());
        }
        consume(TokenType.LLAVE, "'}' después de <destinatarios>");
    }

    private ASTNode parsePersona() {
        ASTNode node = new ASTNode(TokenType.PERSONA, "Persona");
        consume(TokenType.NOMBRE, "'NOMBRE'");
        consume(TokenType.LLAVE, "'{' después de 'NOMBRE'");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "'CADENA'").getLexeme()));
        consume(TokenType.LLAVE, "'}' después de 'CADENA'");

        consume(TokenType.CORREO, "'CORREO'");
        consume(TokenType.LLAVE, "'{' después de 'CORREO'");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "'CADENA'").getLexeme()));
        consume(TokenType.LLAVE, "'}' después de 'CADENA'");

        return node;
    }

    private String buildErrorMessage(Token token, String message, TokenType expectedType) {
        return String.format("Error de sintaxis en línea %d, columna %d: Se esperaba %s, pero se encontró '%s'.",
                token.getLine(), token.getColumn(), message, token.getLexeme());
    }
}
