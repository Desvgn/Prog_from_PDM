import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Rule[] rules;

    public static void main(String[] args) {
        System.out.println("Введите правила:");
        Scanner sc = new Scanner(System.in);
        List<Rule> tempRules = new ArrayList<>();
        String currentLine;

        // Формируем введенные правила
        while (!(currentLine = sc.nextLine()).equals("")) {
            try {
                Rule rule = createRule(currentLine);
                tempRules.add(rule);
            } catch (Exception ex) {
                System.err.println("Введено ошибочное правило.");
                System.exit(0);
            }
        }

        rules = tempRules.toArray(new Rule[0]);

        // Формируем цепочку которую нужно разобрать
        String initSequence = sc.nextLine();

        SequenceTree sequenceTree = new SequenceTree(Main.rules); // Создаем дерево цепочек
        SequenceNode node = sequenceTree.findSequence(initSequence); // Находим цепочку

        System.out.println();
        if (node == null) {
            System.out.println("Цепочка не пренадлежит введенной грамматике.");
            System.exit(0);
        }
        // Переменная смещения
        int offset = 20;
        if (initSequence.length() > offset) {
            offset = initSequence.length() + 3;
        }

        println(offset, "Текущее состояние", "Входная строка", "Содержимое магазина");

        // Формируем лист цепочек из возвращенного узла
        List<String> sequences = new ArrayList<>();
        while (node.getPreviousNode() != null) {
            sequences.add(node.getValue());
            node = node.getPreviousNode();
        }
        sequences.add(node.getValue());
        Collections.reverse(sequences);

        int countChars = 0; // Количество символов которые нужно обрезать
        for (String sequence : sequences) {
            String tmp = sequence.substring(countChars);
            println(offset, "q", sequence, tmp);
            while (sequence.length() > 0 && sequence.charAt(0) == tmp.charAt(0)) {
                if (sequence.length() > 1) {
                    tmp = tmp.substring(1);
                    sequence = sequence.substring(1);
                } else {
                    tmp = "";
                    sequence = "";
                }
                countChars++;
                if (sequence.length() > 0) {
                    println(offset, "q", sequence, tmp);
                } else {
                    println(offset, "q", "ℇ", "ℇ");
                }
            }
        }
        System.out.println();
        System.out.println("Цепочка пренадлежит введенной грамматике и принята полностью.");
    }

    private static Rule createRule(String str) throws Exception {
        String[] parts = str.split("->"); // Поделить строку

        if (parts.length == 0 ||
                parts.length > 2 ||
                parts[0].length() == 0 ||
                parts[1].length() == 0) {
            throw new Exception();
        }

        Rule rule = new Rule(parts[0].trim());
        String[] rights = parts[1].trim().split("\\|"); // Поделить правую часть строки части
        for (int i = 0; i < rights.length; i++) {
            rights[i] = rights[i].trim();
        }
        rule.setSequences(rights);
        return rule;
    }

    private static void println(int offset, String s1, String s2, String s3) {
        System.out.printf("%-" + offset + "s|%-" + offset + "s|%-" + offset + "s%n",
                s1,
                s2,
                s3);
    }
}