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

        String str = expression.replaceAll("sqrt", "" + SQRT);
        str = str.replaceAll("^2", "" + SQUARE);
        System.out.println(str);
        if (getOperator(str) == -1) {
            result = "" + extractNumber(str);
        }
        else {
            makeTree(str);
            result = "" + evaluateTree(tree.root());
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

    private void makeTree(String expression) {
        int index = getOperator(expression);
        if (index == -1) {
            double value = extractNumber(expression);
            tree = new BinaryTree(new Node(value, null, null));
        }
        else {
            char c = expression.charAt(index);
            if (isUnary(c)) {
                String newExpression = "";
                if (c == SQRT) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else {
                    newExpression = expression.substring(0, index);
                }
                tree = new BinaryTree(new Node(c, getNewChild(newExpression), null));
            }
            else {
                String leftExpression = expression.substring(0, index);
                String rightExpression = expression.substring(index + 1, expression.length());
                tree = new BinaryTree(new Node(c, getNewChild(leftExpression), getNewChild(rightExpression)));
            }
        }
    }

    private int getOperator(String str) {
        if (str.length() > 2 && str.charAt(0) == '(' && str.charAt(str.length() -1) == ')') {
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
        bracketCounter = 0;
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

        bracketCounter = 0;
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

    private Node getNewChild(String expression) {
        int index = getOperator(expression);
        if (index == -1) {
            double value = extractNumber(expression);
            return new Node(value, null, null);
        }
        else {
            char c = expression.charAt(index);
            if (isUnary(c)) {
                String newExpression = "";
                if (c == SQRT) {
                    newExpression = expression.substring(index + 1, expression.length());
                }
                else {
                    newExpression = expression.substring(0, index);
                }
                return new Node(c, getNewChild(newExpression), null);
            }
            else {
                String leftExpression = expression.substring(0, index);
                String rightExpression = expression.substring(index + 1, expression.length());
                return new Node(c, getNewChild(leftExpression), getNewChild(rightExpression));
            }
        }
    }

    private double evaluateTree(Node v) {
        if (tree.isInternal(v)) {
            char op = (char)v.element();
            if (isUnary(op)) {
                if (op == SQRT) {
                    return Math.sqrt(evaluateTree(v.getLeft()));
                }
                else if (op == SQUARE) {
                    double i = evaluateTree(v.getLeft());
                    return i*i;
                }
                else if (op == '%') {
                    return evaluateTree(v.getLeft())/100;
                }
            }
            else {
                if (op == '+') {
                    return evaluateTree(v.getLeft()) + evaluateTree(v.getRight());
                }
                else if (op == '-') {
                    return evaluateTree(v.getLeft()) - evaluateTree(v.getRight());
                }
                else if (op == '*') {
                    return evaluateTree(v.getLeft()) * evaluateTree(v.getRight());
                }
                else if (op == '/') {
                    return evaluateTree(v.getLeft()) / evaluateTree(v.getRight());
                }
            }
        }
        return (double)v.element();
    }

}
