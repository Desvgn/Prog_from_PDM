import java.util.ArrayList;
import java.util.List;

public class SequenceNode {
    private String value; // Цепочка находящаяся в узле
    private SequenceNode previousNode; // Родительский узел
    private List<SequenceNode> nextNodes; // Дочерние узлы

    public SequenceNode(String value) {
        this.value = value;
        nextNodes = new ArrayList<>();
    }

    public boolean hasNextNodes(){
        return nextNodes.size() != 0;
    }

    public void addNextNode(SequenceNode node) {
        nextNodes.add(node);
    }

    public SequenceNode[] getNextNodes() {
        return nextNodes.toArray(new SequenceNode[0]);
    }

    public void setPreviousNode(SequenceNode node) {
        this.previousNode = node;
    }

    public String getValue() {
        return value;
    }

    public SequenceNode getPreviousNode(){
        return previousNode;
    }
}
