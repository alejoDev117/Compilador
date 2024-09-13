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
        throw new ParseException(message);
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

        // Implement parsing based on the rule loaded
        ASTNode node = new ASTNode(TokenType.NOTIFICATION, "Notification");
        consume(TokenType.NOTIFICATION, "Expecting NOTIFICATION");
        consume(TokenType.LLAVE, "Expecting { after NOTIFICATION");

        // Implement parsing based on rule
        parseIdentifier(node);
        parseAutor(node);
        parseTitle(node);
        parseContent(node);
        parseCreationDate(node);
        parseState(node);
        parseScheduledDate(node);
        parseRecipients(node);

        consume(TokenType.LLAVE, "Expecting } after <Notification>");
        return node;
    }

    private void parseIdentifier(ASTNode node) {
        consume(TokenType.IDENTIFICADOR, "Expecting IDENTIFICADOR");
        consume(TokenType.LLAVE, "Expecting { after IDENTIFICADOR");
        consume(TokenType.UUID, "Expecting UUID");
        consume(TokenType.LLAVE, "Expecting } after UUID");
    }

    private void parseAutor(ASTNode node) {
        consume(TokenType.AUTOR, "Expecting AUTOR");
        consume(TokenType.LLAVE, "Expecting { after AUTOR");
        node.addChild(parsePersona());
        consume(TokenType.LLAVE, "Expecting } after persona");
    }

    private void parseTitle(ASTNode node) {
        consume(TokenType.TITULO, "Expecting TITULO");
        consume(TokenType.LLAVE, "Expecting { after TITULO");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "Expecting CADENA").getLexeme()));
        consume(TokenType.LLAVE, "Expecting } after CADENA");
    }

    private void parseContent(ASTNode node) {
        consume(TokenType.CONTENIDO, "Expecting CONTENIDO");
        consume(TokenType.LLAVE, "Expecting { after CONTENIDO");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "Expecting CADENA").getLexeme()));
        consume(TokenType.LLAVE, "Expecting } after CADENA");
    }

    private void parseCreationDate(ASTNode node) {
        consume(TokenType.FECHACREACION, "Expecting FECHACREACION");
        consume(TokenType.LLAVE, "Expecting { after FECHACREACION");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "Expecting CADENA").getLexeme()));
        consume(TokenType.LLAVE, "Expecting } after CADENA");
    }

    private void parseState(ASTNode node) {
        consume(TokenType.ESTADO, "Expecting ESTADO");
        consume(TokenType.LLAVE, "Expecting { after ESTADO");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "Expecting CADENA").getLexeme()));
        consume(TokenType.LLAVE, "Expecting } after CADENA");
    }

    private void parseScheduledDate(ASTNode node) {
        consume(TokenType.FECHAPROGRAMADA, "Expecting FECHAPROGRAMADA");
        consume(TokenType.LLAVE, "Expecting { after FECHAPROGRAMADA");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "Expecting CADENA").getLexeme()));
        consume(TokenType.LLAVE, "Expecting } after CADENA");
    }

    private void parseRecipients(ASTNode node) {
        consume(TokenType.LLAVE, "Expecting { before <destinatarios>");
        while (check(TokenType.NOMBRE)) {
            node.addChild(parsePersona());
        }
        consume(TokenType.LLAVE, "Expecting } after <destinatarios>");
    }

    private ASTNode parsePersona() {
        ASTNode node = new ASTNode(TokenType.PERSONA, "Persona");
        consume(TokenType.NOMBRE, "Expecting NOMBRE");
        consume(TokenType.LLAVE, "Expecting { after NOMBRE");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "Expecting CADENA").getLexeme()));
        consume(TokenType.LLAVE, "Expecting } after CADENA");

        consume(TokenType.CORREO, "Expecting CORREO");
        consume(TokenType.LLAVE, "Expecting { after CORREO");
        node.addChild(new ASTNode(TokenType.CADENA, consume(TokenType.CADENA, "Expecting CADENA").getLexeme()));
        consume(TokenType.LLAVE, "Expecting } after CADENA");

        return node;
    }
}
