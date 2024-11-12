package main;

import utils.exceptions.SemanticException;
import utils.loader.GrammarLoader;
import lexical.Lexer;
import lexical.Token;
import syntax.ASTNode;
import syntax.Parser;
import utils.exceptions.ParseException;
import semantic.SemanticAnalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //rutas src/data/ejemplo_gramatica.txt, src/data/entrada.txt
        Scanner scanner = new Scanner(System.in);

        // Solicitar la ruta de la gramática al usuario
        System.out.print("Ingrese la ruta del archivo de gramática: ");
        String grammarPath = scanner.nextLine();

        // Cargar la gramática
        GrammarLoader grammarLoader = new GrammarLoader();
        try {
            grammarLoader.loadGrammar(grammarPath);
            System.out.println("Gramática cargada correctamente: ");
            System.out.println(grammarLoader.getRule());
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
        } catch (IOException e) {
            System.err.println("Error leyendo el archivo: " + e.getMessage());
            return;
        }

        // Tokenizar el contenido
        Lexer lexer = new Lexer(content.toString());
        List<Token> tokens = lexer.tokenize();

        System.out.println("Tokens generados:");
        for (Token token : tokens) {
            System.out.println(token);
        }

        // Análisis sintáctico
        Parser parser = new Parser(tokens, grammarLoader);
        try {
            ASTNode ast = parser.parse();
            System.out.println("El análisis sintáctico fue exitoso. Cadena válida.");
            System.out.println(ast);

            // Análisis semántico
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(content.toString());
            semanticAnalyzer.analyze();
            System.out.println("El análisis semántico fue exitoso. No se encontraron errores.");

            // Guardar resultado en archivo HTML
            String htmlFilename = "Salida.html";
            semanticAnalyzer.saveToHtmlFile(htmlFilename);
            System.out.println("Archivo HTML generado y guardado como: " + htmlFilename);

        } catch (ParseException e) {
            System.err.println("Error en el análisis sintáctico: cadena inválida. " + e.getMessage());
        } catch (SemanticException e) {
            System.err.println("Error en el análisis semántico: cadena inválida. " + e.getMessage());
        }
    }
}
