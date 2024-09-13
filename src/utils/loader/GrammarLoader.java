package utils.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GrammarLoader {
    private Map<String, String> rules = new HashMap<>();

    public void loadGrammar(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.startsWith("#")) {
                    String[] parts = line.split("->");
                    if (parts.length == 2) {
                        rules.put(parts[0].trim(), parts[1].trim());
                    }
                }
            }
        }
    }

    public String getRule(String nonTerminal) {
        return rules.get(nonTerminal);
    }
}
