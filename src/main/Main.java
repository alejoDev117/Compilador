package main;

import utils.loader.GrammarLoader;
import lexical.Lexer;
import lexical.Token;
import syntax.ASTNode;
import syntax.Parser;
import utils.exceptions.ParseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String relativePath = "src/data/ejemplo_gramatica.txt";
        StringBuilder content = new StringBuilder();

        // Cargar la gramática
        GrammarLoader grammarLoader = new GrammarLoader();
        try {
            grammarLoader.loadGrammar(relativePath);
        } catch (IOException e) {
            System.err.println("Error cargando la gramática: " + e.getMessage());
            return;
        }

        // Leer el archivo de entrada
        String inputFilePath = "src/data/entrada.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Archivo no encontrado: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Error de IO: " + e.getMessage());
        }

        // Tokenizar el contenido
        Lexer lexer = new Lexer(content.toString());
        List<Token> tokens = lexer.tokenize();

        for (Token token : tokens) {
            System.out.println(token);
        }

        // Analizar los tokens
        Parser parser = new Parser(tokens, grammarLoader);
        try {
            ASTNode ast = parser.parse();
            System.out.println("El análisis sintáctico fue exitoso.");
            System.out.println(ast);
        } catch (ParseException e) {
            System.err.println("Error en el análisis sintáctico: " + e.getMessage());
        }
    }
}
