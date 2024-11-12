package semantic;

import utils.exceptions.SemanticException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SemanticAnalyzer {
    private String source;
    private String authorEmail;

    public SemanticAnalyzer(String source) {
        this.source = source;
        this.authorEmail = extractAuthorEmail();
    }

    // Extraer el correo electrónico del autor
    private String extractAuthorEmail() {
        int authorStart = source.indexOf("AUTOR{");
        if (authorStart == -1) return null;

        int emailStart = source.indexOf("correo{", authorStart);
        if (emailStart == -1) return null;

        emailStart += "correo{".length();
        int emailEnd = source.indexOf("}", emailStart);
        return source.substring(emailStart, emailEnd).trim();
    }

    // Analiza los destinatarios
    public void analyze() throws SemanticException {
        Set<String> recipientsEmails = new HashSet<>();
        int recipientStart = source.indexOf("{", source.indexOf("FECHAPROGRAMADA{"));
        int recipientEnd = source.lastIndexOf("}");

        if (recipientStart == -1 || recipientEnd == -1) {
            throw new SemanticException("No se encontraron destinatarios.");
        }

        String recipients = source.substring(recipientStart + 1, recipientEnd).trim();
        String[] individuals = recipients.split("(?=nombre\\{)");

        for (String person : individuals) {
            String email = extractEmailFromRecipient(person);
            if (email != null) {
                if (email.equals(authorEmail)) {
                    throw new SemanticException("Error en el análisis semántico: El autor no puede ser un destinatario.");
                }
                if (!recipientsEmails.add(email)) {
                    throw new SemanticException("Error en el análisis semántico: El destinatario con correo " + email + " se repite.");
                }
            }
        }
    }

    // Extraer el correo electrónico de un destinatario
    private String extractEmailFromRecipient(String recipient) {
        int emailStart = recipient.indexOf("correo{");
        if (emailStart == -1) return null;

        emailStart += "correo{".length();
        int emailEnd = recipient.indexOf("}", emailStart);
        return recipient.substring(emailStart, emailEnd).trim();
    }
    public String toHtml() {
        StringBuilder html = new StringBuilder();

        html.append("<html><head>");
        // CSS actualizado con una paleta de verdes
        html.append("<style>");
        html.append("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; margin: 40px; background: linear-gradient(135deg, #A8D5BA, #B9E4C9); color: #2F4F4F; }");
        html.append("h1 { color: #fff; font-size: 36px; text-align: center; padding: 20px 0; text-transform: uppercase; letter-spacing: 2px; background-color: #4CAF50; border-radius: 8px; }");
        html.append("h2 { color: #fff; font-size: 28px; margin-top: 30px; border-bottom: 2px solid #388E3C; padding-bottom: 5px; background-color: #388E3C; border-radius: 5px; padding: 10px; }");
        html.append("table { width: 80%; margin: 20px auto; border-collapse: collapse; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }");
        html.append("th, td { padding: 15px 20px; text-align: left; border: 1px solid #A5D6A7; border-radius: 8px; }");
        html.append("th { background-color: #388E3C; color: white; font-size: 16px; text-transform: uppercase; letter-spacing: 1px; }");
        html.append("tr:nth-child(even) { background-color: #f1f8f6; }");
        html.append("tr:nth-child(odd) { background-color: #ffffff; }");
        html.append("tr:hover { background-color: #C8E6C9; cursor: pointer; }");
        html.append("p { font-size: 16px; line-height: 1.6; margin: 10px 0; padding-left: 10px; }");
        html.append("strong { color: #388E3C; font-weight: bold; }");
        html.append(".container { width: 90%; max-width: 1200px; margin: 0 auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1); }");
        html.append("</style>");
        html.append("</head><body>");

        html.append("<div class='container'>");
        html.append("<h1>Notificación</h1>");
        html.append("<p><strong>Identificador:</strong> ").append(extractBetween("IDENTIFICADOR{", "}")).append("</p>");

        html.append("<h2>Autor</h2>");
        html.append("<p><strong>Nombre:</strong> ").append(extractBetween("nombre{", "}")).append("</p>");
        html.append("<p><strong>Correo:</strong> ").append(extractAuthorEmail()).append("</p>");

        html.append("<p><strong>Título:</strong> ").append(extractBetween("TITULO{", "}")).append("</p>");
        html.append("<p><strong>Contenido:</strong> ").append(extractBetween("CONTENIDO{", "}")).append("</p>");
        html.append("<p><strong>Fecha de Creación:</strong> ").append(extractBetween("FECHACREACION{", "}")).append("</p>");
        html.append("<p><strong>Estado:</strong> ").append(extractBetween("ESTADO{", "}")).append("</p>");
        html.append("<p><strong>Fecha Programada:</strong> ").append(extractBetween("FECHAPROGRAMADA{", "}")).append("</p>");

        html.append("<h2>Destinatarios</h2>");
        html.append("<table>");
        html.append("<tr><th>Nombre</th><th>Correo</th></tr>");

        // Procesar la lista de destinatarios
        int recipientStart = source.indexOf("{", source.indexOf("FECHAPROGRAMADA{"));
        int recipientEnd = source.lastIndexOf("}");
        String recipients = source.substring(recipientStart + 1, recipientEnd).trim();

        // Ajuste para extraer correctamente los destinatarios
        String[] individuals = recipients.split("(?=nombre\\{)");

        for (String person : individuals) {
            String name = extractBetween("nombre{", "}", person);
            String email = extractBetween("correo{", "}", person);
            if (!name.isEmpty() || !email.isEmpty()) {
                html.append("<tr><td>").append(name).append("</td><td>").append(email).append("</td></tr>");
            }
        }

        html.append("</table>");
        html.append("</div>");

        html.append("</body></html>");

        return html.toString();
    }



    // Guarda el HTML generado en un archivo
    public void saveToHtmlFile(String filename) {
        String htmlContent = toHtml();
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(htmlContent);
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo HTML: " + e.getMessage());
        }
    }

    // Extrae un valor específico entre dos delimitadores
    private String extractBetween(String start, String end) {
        return extractBetween(start, end, source);
    }

    // Extrae un valor específico entre dos delimitadores de un texto dado
    private String extractBetween(String start, String end, String text) {
        int startIndex = text.indexOf(start);
        if (startIndex == -1) return "";

        startIndex += start.length();
        int endIndex = text.indexOf(end, startIndex);
        if (endIndex == -1) return "";

        return text.substring(startIndex, endIndex).trim();
    }
}
