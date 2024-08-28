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
                "\t\t nombre{\"Francisco Cadavid Orjuela\"}correo{\"Francisco.Cadavida34788@uco.net.co\"}}\n" +
                "}";

        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        for (Token token : tokens) {
            System.out.println(token);
        }
        /* ESTO ES LO QUE DEBE IMPRIMIR
            Token{type=NOTIFICATION, lexeme='NOTIFICATION'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=IDENTIFICADOR, lexeme='IDENTIFICADOR'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=UUID, lexeme='8c4ac2a2-e397-4052-b5fc-911277ab100e'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=AUTOR, lexeme='AUTOR'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=NOMBRE, lexeme='nombre'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=CADENA, lexeme='Alejandro Gomez Orjuela'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=CORREO, lexeme='correo'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=CADENA, lexeme='alejandro.gomez8170@uco.net.co'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=TITULO, lexeme='TITULO'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=CADENA, lexeme='COMPILADORES'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=CONTENIDO, lexeme='CONTENIDO'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=CADENA, lexeme='Hola este es una notificacion para la materia compiladores'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=FECHACREACION, lexeme='FECHACREACION'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=CADENA, lexeme='23/12/2023'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=ESTADO, lexeme='ESTADO'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=CADENA, lexeme='ENVIADA'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=FECHAPROGRAMADA, lexeme='FECHAPROGRAMADA'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=CADENA, lexeme='23/12/2023'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=NOMBRE, lexeme='nombre'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=CADENA, lexeme='Maria Paula Marin Diaz'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=CORREO, lexeme='correo'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=CADENA, lexeme='Maria.marin6788@uco.net.co'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=NOMBRE, lexeme='nombre'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=CADENA, lexeme='Daniel Felipe'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=CORREO, lexeme='correo'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=CADENA, lexeme='Daniel.Felipe6788@uco.net.co'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=NOMBRE, lexeme='nombre'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=CADENA, lexeme='Marcela Marin Orjuela'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=CORREO, lexeme='correo'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=CADENA, lexeme='Marcela.orjuela6788@uco.net.co'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=NOMBRE, lexeme='nombre'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=CADENA, lexeme='Marcela Marin Orjuela'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=CORREO, lexeme='correo'}
            Token{type=LLAVE, lexeme='{'}
            Token{type=CADENA, lexeme='Marcela.orjuela6788@uco.net.co'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=LLAVE, lexeme='}'}
            Token{type=LLAVE, lexeme='}'}
            */
    }
    }

