public class Rule {
    private String nonTerminal; // Терминал
    private String[] sequences; // Цепочка

    public Rule(String nonTerminal) {
        this.nonTerminal = nonTerminal;
    }

    public void setSequences(String[] sequences) {
        this.sequences = sequences;
    }

    public String[] getSequences() {
        String[] temp = new String[sequences.length];
        System.arraycopy(sequences, 0, temp, 0, sequences.length);
        return temp;
    }

    public String getNonTerminal() {
        return nonTerminal;
    }
}
