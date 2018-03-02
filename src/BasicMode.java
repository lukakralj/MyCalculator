import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Stack;


public class BasicMode {

    private static final int BUTTON_WIDTH = 50;
    private static final int BUTTON_HEIGHT = 30;

    private JTextArea displayArea;
    private JTextField inputField;
    private JScrollPane display;
    // on entering '(' increase by 1, on entering ')' decrease by 1; check if 0 before adding.
    // for expression to be valid, counter must equal 0
    private int bracketCounter;
    //  if a decimal point has just been entered we cannot enter it again until we reach the next number
    private boolean decimalPointEntered;
    // used for undo; stores previous entries
    private Stack<String> previousExpressions;



    public BasicMode() {
        decimalPointEntered = false;
        bracketCounter = 0;
        previousExpressions = new Stack<>();

        final JFrame frame = new JFrame("Basic mode");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container contentPane = frame.getContentPane();

        JPanel all = new JPanel();
        all.setLayout(new BoxLayout(all, BoxLayout.PAGE_AXIS));
        all.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel labels = new JPanel(new BorderLayout());
        labels.setBorder(new LineBorder(Color.GRAY, 1));

        displayArea = new JTextArea();
        displayArea.setOpaque(true);
        displayArea.setBackground(Color.WHITE);
        displayArea.setLineWrap(false);
        displayArea.setEditable(false);

        display = new JScrollPane(displayArea);
        display.setPreferredSize(new Dimension(frame.getWidth(), 100));
        display.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        display.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        labels.add(display, BorderLayout.CENTER);

        inputField = new JTextField("");
        inputField.setPreferredSize(new Dimension(frame.getWidth(), 20));
        inputField.setBackground(Color.WHITE);
        inputField.setEditable(false);
        inputField.setHorizontalAlignment(SwingConstants.RIGHT);
        labels.add(inputField, BorderLayout.SOUTH);

        all.add(labels);
        all.add(Box.createVerticalStrut(10));

        JPanel buttons = createButtons();
        all.add(buttons);

        contentPane.add(all);
        frame.setLocationRelativeTo(null);
        //frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel createButtons() {

        JPanel allFlow = new JPanel(new FlowLayout());

        JPanel allAll = new JPanel();
        allAll.setLayout(new BoxLayout(allAll, BoxLayout.LINE_AXIS));

        JPanel all = new JPanel();
        all.setLayout(new BoxLayout(all, BoxLayout.PAGE_AXIS));


        JPanel topButtons = new JPanel();
        topButtons.setLayout(new GridLayout(3, 6, 3,3));


        // FIRST ROW:

        JButton seven = new JButton("7");
        seven.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        seven.setBackground(Color.WHITE);
        seven.addActionListener(e -> updateInputField("7"));
        topButtons.add(seven);

        JButton eight = new JButton("8");
        eight.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        eight.setBackground(Color.WHITE);
        eight.addActionListener(e -> updateInputField("8"));
        topButtons.add(eight);

        JButton nine = new JButton("9");
        nine.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        nine.setBackground(Color.WHITE);
        nine.addActionListener(e -> updateInputField("9"));
        topButtons.add(nine);

        JButton divide = new JButton("/");
        divide.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        divide.setBackground(Color.WHITE);
        divide.addActionListener(e -> operatorClicked("/"));
        topButtons.add(divide);

        JButton undo = new JButton();
        undo.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        undo.setBackground(Color.WHITE);
        ImageIcon undoIcon = new ImageIcon(getClass().getResource("undo_arrow.jpg"));
        undo.setIcon(undoIcon);
        undo.addActionListener(e -> undoClicked());
        topButtons.add(undo);

        JButton delete = new JButton();
        delete.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        delete.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        delete.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        delete.setBackground(Color.WHITE);
        ImageIcon deleteIcon = new ImageIcon(getClass().getResource("delete.png"));
        delete.setIcon(deleteIcon);
        delete.addActionListener(e -> deleteClicked());
        topButtons.add(delete);

        // SECOND ROW:

        JButton four = new JButton("4");
        four.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        four.setBackground(Color.WHITE);
        four.addActionListener(e -> updateInputField("4"));
        topButtons.add(four);

        JButton five = new JButton("5");
        five.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        five.setBackground(Color.WHITE);
        five.addActionListener(e -> updateInputField("5"));
        topButtons.add(five);

        JButton six = new JButton("6");
        six.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        six.setBackground(Color.WHITE);
        six.addActionListener(e -> updateInputField("6"));
        topButtons.add(six);

        JButton multiply = new JButton("*");
        multiply.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        multiply.setBackground(Color.WHITE);
        multiply.addActionListener(e -> operatorClicked("*"));
        topButtons.add(multiply);

        JButton openingBracket = new JButton("(");
        openingBracket.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        openingBracket.setBackground(Color.WHITE);
        openingBracket.addActionListener(e -> openingBracketClicked());
        topButtons.add(openingBracket);

        JButton closingBracket = new JButton(")");
        closingBracket.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        closingBracket.setBackground(Color.WHITE);
        closingBracket.addActionListener(e -> closingBracketClicked());
        topButtons.add(closingBracket);

        // THIRD ROW:

        JButton one = new JButton("1");
        one.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        one.setBackground(Color.WHITE);
        one.addActionListener(e -> updateInputField("1"));
        topButtons.add(one);

        JButton two = new JButton("2");
        two.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        two.setBackground(Color.WHITE);
        two.addActionListener(e -> updateInputField("2"));
        topButtons.add(two);

        JButton three = new JButton("3");
        three.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        three.setBackground(Color.WHITE);
        three.addActionListener(e -> updateInputField("3"));
        topButtons.add(three);

        JButton minus = new JButton("-");
        minus.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        minus.setBackground(Color.WHITE);
        minus.addActionListener(e -> operatorClicked("-"));
        topButtons.add(minus);

        JButton square = new JButton("<html>x<sup>2</sup></html>");
        square.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        square.setBackground(Color.WHITE);
        square.addActionListener(e -> squareClicked());
        topButtons.add(square);

        JButton sqrt = new JButton("R");
        sqrt.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        sqrt.setBackground(Color.WHITE);
        sqrt.addActionListener(e -> sqrtClicked());
        topButtons.add(sqrt);

        all.add(topButtons);



        JPanel bottomRow = new JPanel();
        bottomRow.setLayout(new FlowLayout());

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(1, 4, 3, 3));

        JButton zero = new JButton("0");
        zero.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        zero.setBackground(Color.WHITE);
        zero.addActionListener(e -> updateInputField("0"));
        buttons.add(zero);

        JButton decimalPoint = new JButton(".");
        decimalPoint.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        decimalPoint.setBackground(Color.WHITE);
        decimalPoint.addActionListener(e -> decimalPointClicked());
        buttons.add(decimalPoint);

        JButton percent = new JButton("%");
        percent.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        percent.setBackground(Color.WHITE);
        percent.addActionListener(e -> percentClicked());
        buttons.add(percent);

        JButton plus = new JButton("+");
        plus.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        plus.setBackground(Color.WHITE);
        plus.addActionListener(e -> operatorClicked("+"));
        buttons.add(plus);

        bottomRow.add(buttons);

        JButton equals = new JButton("=");
        equals.setPreferredSize(new Dimension(BUTTON_WIDTH*2 + 6, BUTTON_HEIGHT));
        equals.setBackground(new Color(190, 34, 34));
        equals.addActionListener(e -> equalsClicked());
        bottomRow.add(equals);

        all.add(bottomRow);

        allAll.add(all);
        allFlow.add(allAll);
        return allFlow;
    }

    private void clearInputField(){
        inputField.setText("");
        enableDecimalPoint();
    }

    private void updateInputField(String newCharacter) {
        inputField.setText(inputField.getText() + newCharacter);
    }

    private void updateDisplayField(String expression, String result) {
        displayArea.append(expression + "\n= " + result + "\n\n");
    }

    private void undoClicked() {
        if (previousExpressions.isEmpty()) {
            clearInputField();
        }
        else {
            inputField.setText(previousExpressions.pop());
        }
    }

    private void deleteClicked() {
        // Delete the last character.
        String str = inputField.getText();
        if (!str.equals("")) {
            inputField.setText(str.substring(0, str.length() - 1));
        }
    }

    private void squareClicked() {
        if (canEnterSymbol()) {
            inputField.setText(inputField.getText() + "^2");
        }
        else {
            showWarning();
        }
    }

    private void sqrtClicked() {
        if (!canEnterSymbol()) {
            inputField.setText(inputField.getText() + "sqrt(");
            bracketCounter++;
        }
        else {
            showWarning();
        }
    }

    private void decimalPointClicked() {
        char last = '+';
        if (!inputField.getText().equals("")) {
            last = inputField.getText().charAt(inputField.getText().length()-1);
        }
        if (!decimalPointEntered && !(last == '+' || last == '-' || last == '/' || last == '*' || last == '(' || last == ')' || last == '%')) {
            decimalPointEntered = true;
            updateInputField(".");
        }
        else {
            showWarning();
        }
    }

    private void enableDecimalPoint() {
        decimalPointEntered = false;
    }

    private void percentClicked() {
        if (canEnterSymbol()) {
            updateInputField("%");
            enableDecimalPoint();
        }
        else {
            showWarning();
        }
    }

    private void equalsClicked() {
        String str = inputField.getText();
        if (str.equals("")) {
            return;
        }
        previousExpressions.push(str);
        Evaluator evaluator = new Evaluator(str);
        String result = evaluator.getResult();
        if (result.startsWith("-")) {
            result = "(0-" + result + ")";
        }
        updateDisplayField(str, result);
        clearInputField();
        updateInputField(result);

    }

    private void operatorClicked(String newCharacter) {
        if(canEnterSymbol()) {
            updateInputField(newCharacter);
            enableDecimalPoint();
        }
        /*else if (newCharacter.equals("-") && (inputField.getText().equals("") || inputField.getText().charAt(inputField.getText().length()-1) == '(')) {
            updateInputField("-");
            enableDecimalPoint();
        }*/
        else {
            showWarning();
        }
    }

    private void openingBracketClicked() {
        String expression = inputField.getText();
        if (expression.equals("")) {
            updateInputField("(");
            bracketCounter++;
            enableDecimalPoint();
            return;
        }

        char last = expression.charAt(expression.length()-1);
        if (last == '+' || last == '-' || last == '/' || last == '*' || last == '(') {
            updateInputField("(");
            bracketCounter++;
            enableDecimalPoint();
        }
        else {
            showWarning();
        }
    }

    private void closingBracketClicked() {
        if (bracketCounter > 0 && canEnterSymbol()) {
            updateInputField(")");
            bracketCounter--;
            enableDecimalPoint();
        }
        else {
            showWarning();
        }
    }

    private boolean canEnterSymbol() {
        String expression = inputField.getText();
        if (expression.equals("")) {
            return false;
        }

        char last = expression.charAt(expression.length()-1);
        if (last == '+' || last == '-' || last == '/' || last == '*' || last == '(') {
            return false;
        }
        return true;
    }

    private void showWarning() {
        JOptionPane.showMessageDialog(null, "Invalid operation.", "Invalid operation.", JOptionPane.WARNING_MESSAGE);
    }
}
