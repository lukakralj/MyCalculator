import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Evaluator {
    public static final char SQRT = 'V';
    public static final char SQUARE = 'S';

    private static final char[] nonNumeric = {'+', '-', '/', '*', '%', '(', ')', SQRT, SQUARE};
    private BinaryTree tree;

    private String result;

    public Evaluator(String expression) {
        tree = new BinaryTree();

        String str = expression.replaceAll("sqrt", "" + SQRT);
        str = str.replaceAll("^2", "" + SQUARE);
        if (getOperator(str) == -1) {
            result = "" + extractNumber(str);
        }
        else {
            start(str);
        }

    }

    public String getResult() {
        if (result == null) {
            return "Err";
        }
        return result;

    }

    private double extractNumber(String str) {
        str = str.replace('(', ' ').replace(')', ' ').trim();
        return Double.parseDouble(str);
    }

    private void start(String expression) {
        int index = getOperator(expression);
    }

    private int getOperator(String str) {
        if (str.charAt(0) == '(' && str.charAt(str.length() -1) == ')') {
            str = str.substring(1, str.length() -1);
        }

        int bracketCounter = 0;
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') { bracketCounter++; }
            else if (c == ')') { bracketCounter--; }
            else if ((c == '+' || c == '-') && bracketCounter == 0) {
                indexes.add(i);
            }
        }

        if (indexes.size() > 0) {
            return indexes.get(indexes.size()-1);
        }

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') { bracketCounter++; }
            else if (c == ')') { bracketCounter--; }
            else if ((c == '*' || c == '/') && bracketCounter == 0) {
                indexes.add(i);
            }
        }

        if (indexes.size() > 0) {
            return indexes.get(indexes.size()-1);
        }

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') { bracketCounter++; }
            else if (c == ')') { bracketCounter--; }
            else if (isUnary(c) && bracketCounter == 0) {
                indexes.add(i);
            }
        }

        if (indexes.size() > 0) {
            return indexes.get(indexes.size()-1);
        }

        return -1;

    }

    private boolean isUnary(char operator) {
        return operator == '%' || operator == SQRT || operator == SQUARE;
    }

}
