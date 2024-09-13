package syntax;

import lexical.TokenType;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {
    private TokenType type;
    private String value;
    private List<ASTNode> children;

    public ASTNode(TokenType type, String value) {
        this.type = type;
        this.value = value;
        this.children = new ArrayList<>();
    }

    public void addChild(ASTNode child) {
        children.add(child);
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "ASTNode{" +
                "type=" + type +
                ", value='" + value + '\'' +
                ", children=" + children +
                '}';
    }
}
