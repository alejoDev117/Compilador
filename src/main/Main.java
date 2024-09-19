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
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//rutas src/data/ejemplo_gramatica.txt, src/data/entrada.txt

        // Solicitar la ruta de la gramática al usuario
        System.out.print("Ingrese la ruta del archivo de gramática: ");
        String grammarPath = scanner.nextLine();

        // Cargar la gramática
        GrammarLoader grammarLoader = new GrammarLoader();
        try {
            grammarLoader.loadGrammar(grammarPath);
        } catch (IOException e) {
            System.err.println("Error cargando la gramática: " + e.getMessage());
            return;
        }

        // Solicitar la ruta del archivo de entrada al usuario
        System.out.print("Ingrese la ruta del archivo a evaluar: ");
        String inputFilePath = scanner.nextLine();

        StringBuilder content = new StringBuilder();

        // Leer el archivo de entrada
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Archivo no encontrado: " + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("Error de IO: " + e.getMessage());
            return;
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
