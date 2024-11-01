package semantic;

import utils.exceptions.SemanticException;
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
        return source.substring(emailStart + 1, emailEnd - 1); // Extrae el correo sin las llaves
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

        // Dividir los destinatarios
        String[] individuals = recipients.split("(?<=})"); // Usa el cierre de llaves como separador

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
        return recipient.substring(emailStart + 1, emailEnd - 1); // Extrae el correo sin las llaves
    }
}
