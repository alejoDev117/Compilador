package main;

import lexical.Lexer;
import lexical.Token;

import java.util.List;

public class Main {
    /* GRAMATICA BASE
        <Notification> -> NOTIFICATION {IDENTIFICADOR{%UUID%}
				<autor>
				TITULO{"str"}
				CONTENIDO{"str"}
				FECHACREACION{"dd/mm/aaaa"}
				ESTADO{"str"}
				FECHAPROGRAMADA{"dd/mm/aaaa"}
				<destinatarios>
				}
<autor> -> PERSONA{<persona>}

<persona> -> nombre{"str"}correo{"str"}

<destinatarios> -> <persona><destinatarios>
		-> EPSILON
     */
    public static void main(String[] args) {
        //EJEMPLO
        String input = "NOTIFICATION {\n" +
                "\t\tIDENTIFICADOR{%8c4ac2a2-e397-4052-b5fc-911277ab100e%}\n" +
                "\t\tAUTOR{nombre{\"Alejandro Gomez Orjuela\"}correo{\"alejandro.gomez8170@uco.net.co\"}}\n" +
                "\t\tTITULO{\"COMPILADORES\"}\n" +
                "\t\tCONTENIDO{\"Hola este es una notificacion para la materia compiladores\"}\n" +
                "\t\tFECHACREACION{\"23/12/2023\"}\n" +
                "\t\tESTADO{\"ENVIADA\"}\n" +
                "\t\tFECHAPROGRAMADA{\"23/12/2023\"}\n" +
                "\t\t{nombre{\"Maria Paula Marin Diaz\"}correo{\"Maria.marin6788@uco.net.co\"}\n" +
                "\t\t nombre{\"Daniel Felipe\"}correo{\"Daniel.Felipe6788@uco.net.co\"}\n" +
                "\t\t nombre{\"Marcela Marin Orjuela\"}correo{\"Marcela.orjuela6788@uco.net.co\"}}\n" +
                "}";

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
    }

