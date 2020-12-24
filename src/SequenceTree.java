import java.util.ArrayList;
import java.util.List;

public class SequenceTree {

    private SequenceNode head; // Вершина дерева цепочек
    private Rule[] rules; // Введение правила

    public SequenceTree(Rule[] rules) {
        this.rules = rules;
    }

    public SequenceNode findSequence(String s) {
        head = new SequenceNode(rules[0].getNonTerminal()); // Создаем вершину
        List<SequenceNode> sequenceNodes = new ArrayList<>(); // Текущие созданные цепочки

        sequenceNodes.add(head); // Добавляем вершину
        int previousAddedCount = 1; // Количество прошлых сгенерированных цепочек
        int currentAddedCount = 0; // Количество только что сгенерированных цепочек

        // Пока текущее количество не равно прошлому, продолжать генерировать цепочки
        while (currentAddedCount != previousAddedCount) {
            previousAddedCount = currentAddedCount; // В каждой итерации цикла, прошлое количество становится текущим
            List<SequenceNode> tempSequenceNodes = new ArrayList<>(); // временный массив для генерируемых цепочек

            // Проходим по текущим цепочкам
            for (SequenceNode node : sequenceNodes) {
                String firstNonTerminal = findFirstNonTerminal(node.getValue()); // Находим первый попавшийся терминал
                String[] sequences = findSequencesByNonTerminal(firstNonTerminal); // По найденному терминалу находим всевозможные цепочки
                // Если такие цепочки существует, то идем дальше
                if (sequences == null) {
                    continue;
                }

                SequenceNode[] _sequenceNodes = new SequenceNode[sequences.length]; // Создаем массив для новых цепочек
                for (int i = 0; i < sequences.length; i++) {
                    sequences[i] = replaceByNonTerminal(node.getValue(), sequences[i], firstNonTerminal); // Заменить терминал на цепочку
                    // Если после замены длина получившейся цепочки меньше длины введеной цепочки
                    if (s.length() >= sequences[i].length()) {
                        _sequenceNodes[i] = new SequenceNode(sequences[i]); // Создаем новый цепочку (узел)
                        _sequenceNodes[i].setPreviousNode(node); // Устанавливаем родительский узел
                        node.addNextNode(_sequenceNodes[i]); // К узлу в качестве дочернего, добавляем этот узел
                        tempSequenceNodes.add(_sequenceNodes[i]); // Добавляем созданную цепочку, во временный массив
                    }

                    // Если значение текущей цепочки совпадает с введенной строкой, то останавливаем метод
                    // И возвращаем текущий узел
                    if (sequences[i].equals(s)) {
                        return _sequenceNodes[i];
                    }
                }
            }

            // Временный массив узлов теперь становится текущим
            currentAddedCount += tempSequenceNodes.size(); // После выполения всех манипуляций изменяем количество добавленных узлов
            sequenceNodes = tempSequenceNodes;
        }

        // Ищем узлы у которых дочерних узлов (т.е. цепочки без терминалов)
        List<SequenceNode> nodes = findLastSequenceNodes(head);
        // Возвращаем тот узел, у которого цепочка совпадает с введеной цепочкой
        for (SequenceNode node : nodes) {
            if (node.getValue().equals(s)) {
                return node;
            }
        }

        // Если такой узла нет (цепочки), то возвращаем null
        return null;
    }

    // Метод возвращающий завершенные цепочки (узлы без дочерных узлов)
    private List<SequenceNode> findLastSequenceNodes(SequenceNode node) {
        List<SequenceNode> nodes = new ArrayList<>();
        if (!node.hasNextNodes()) {
            nodes.add(node);
        }
        for (SequenceNode sequenceNode : node.getNextNodes()) {
            nodes.addAll(findLastSequenceNodes(sequenceNode));
        }
        return nodes;
    }

    // Метод заменяющий строку terminal в sequence1 на sequence1
    private String replaceByNonTerminal(String sequence1, String sequence2, String nonTerminal) {
        StringBuilder builder = new StringBuilder(sequence1);
        int index = builder.indexOf(nonTerminal);
        if (index != -1) {
            builder.insert(index + 1, sequence2);
            if (nonTerminal.length() == 1) {
                builder.deleteCharAt(index);
            } else {
                builder.delete(index, nonTerminal.length());
            }
        }
        return builder.toString();
    }

    // Метод возвращающий первый попавшийся нетерминал
    private String findFirstNonTerminal(String sequence) {
        for (int i = 0; i < sequence.length(); i++) {
            if (sequence.charAt(i) >= 65 && sequence.charAt(i) <= 90) {
                return "" + sequence.charAt(i);
            }
        }
        return null;
    }

    // Метод возвращающий цепочки по терминалу
    private String[] findSequencesByNonTerminal(String nonTerminal) {
        for (Rule rule : rules) {
            if (rule.getNonTerminal().equals(nonTerminal)) {
                return rule.getSequences();
            }
        }
        return null;
    }
}
