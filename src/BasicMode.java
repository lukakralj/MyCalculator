import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

// TODO: add checking validity of the expression when clicking equals
// TODO: fix entering with keyboard
// TODO: allow leading minus for negative numbers.

/**
 * This class displays a simple calculator.
 *
 * @author Luka Kralj
 * @version 30 March 2018
 */
public class BasicMode extends KeyAdapter {

    private static final int BUTTON_WIDTH = 50;
    private static final int BUTTON_HEIGHT = 30;

    private static final char[] validChars = {0,1,2,3,4,5,6,7,8,9,'(',')','+','-','*','/', '%', Evaluator.SQUARE, Evaluator.SQRT};

    private JTextArea displayArea; // Results shown here.
    private JTextField inputField; // Expressions entered here.
    // on entering '(' increase by 1, on entering ')' decrease by 1; check if 0 before adding.
    // for expression to be valid, counter must equal 0
    private int bracketCounter;
    //  if a decimal point has just been entered we cannot enter it again until we reach the next operator
    private boolean decimalPointEntered;
    // used for undo; stores previous entries
    private Stack<String> previousExpressions;

    /**
     * Construct the basic calculator.
     */
    public BasicMode() {
        decimalPointEntered = false;
        bracketCounter = 0;
        previousExpressions = new Stack<>();

        JFrame frame = new JFrame("Basic mode");
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

        JScrollPane display = new JScrollPane(displayArea);
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
        frame.setFocusable(true);
        frame.addKeyListener(this);
        frame.setFocusableWindowState(true);
        frame.setAutoRequestFocus(true);
        //frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates all the buttons that are needed for the input.
     *
     * @return Panel with all the buttons to be added to the calculator.
     */
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

    /**
     * Clears the expression currently displayed.
     */
    private void clearInputField(){
        inputField.setText("");
        enableDecimalPoint();
    }

    /**
     * Adds a new character (number,...) to the expression.
     *
     * @param newCharacter To be added at the end of expression.
     */
    private void updateInputField(String newCharacter) {
        inputField.setText(inputField.getText() + newCharacter);
    }

    /**
     * Updates the display are to include the expression and its result.
     *
     * @param expression Expression to be added.
     * @param result Result of the expression.
     */
    private void updateDisplayField(String expression, String result) {
        displayArea.append(expression + "\n= " + result + "\n\n");
    }

    /**
     * Undo button was clicked. Set the expression to the last entered expression, if any.
     */
    private void undoClicked() {
        if (previousExpressions.isEmpty()) {
            clearInputField();
        }
        else {
            inputField.setText(previousExpressions.pop());
        }
    }

    /**
     * Delete the last character of the current expression.
     * TODO: add more checks what to delete.
     */
    private void deleteClicked() {
        // Delete the last character.
        String str = inputField.getText();
        if (!str.equals("")) {
            inputField.setText(str.substring(0, str.length() - 1));
        }
    }

    /**
     * Adds a square to the expression.
     */
    private void squareClicked() {
        if (canEnterSymbol()) {
            inputField.setText(inputField.getText() + "^2");
        }
        else {
            showWarning();
        }
    }

    /**
     * Adds the symbol for square root as "sqrt(" to the expression.
     */
    private void sqrtClicked() {
        if (!canEnterSymbol()) {
            inputField.setText(inputField.getText() + "sqrt(");
            bracketCounter++;
        }
        else {
            showWarning();
        }
    }

    /**
     * Decimal point was clicked. It is added to the expression if and only if the expression would still be valid.
     */
    private void decimalPointClicked() {
        char last = ' '; // placeholder
        if (!inputField.getText().equals("")) {
            last = inputField.getText().charAt(inputField.getText().length()-1);
        }
        if (!decimalPointEntered && !(last == ' ' || last == '+' || last == '-' || last == '/' || last == '*' || last == '(' || last == ')' || last == '%')) {
            decimalPointEntered = true;
            updateInputField(".");
        }
        else {
            showWarning();
        }
    }

    /**
     * Allows a decimal point to be entered.
     */
    private void enableDecimalPoint() {
        decimalPointEntered = false;
    }

    /**
     * Adds percentage sign to the expression. This sign takes the expression in front of it and
     * divides it by 100 when evaluated, for example: 25.4% = 0.254 and 25.4%% = 0.00254
     */
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
            result = "(0" + result + ")";
        }
        updateDisplayField(str, result);
        clearInputField();
        updateInputField(result);

    }

    /**
     * Add operator to the expression.
     *
     * @param newCharacter Operator to add.
     */
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

    /**
     * Add opening bracket to the expression.
     */
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

    /**
     * Adds closing bracket to the expression.
     */
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

    /**
     * Checks whether the symbol, such as operators and brackets can be added.
     * Needed to maintain validity of the expression.
     *
     * @return True if the symbol can be added.
     */
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

    /**
     * Whenever a user attempts to enter an invalid combination of symbols this warning is shown.
     */
    private void showWarning() {
        JOptionPane.showMessageDialog(null, "Invalid operation.", "Invalid operation.", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * This method is intended to accept inputs entered with the keyboard.
     * There is still quite a lot of problems with it. It seems that the key codes are not matching with the constants.
     * Also the focus is lost once the user clicks one of the buttons.
     *
     * TODO: debug
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == '+' ||
                c == '-' ||
                c == '*' ||
                c == '/') {
            operatorClicked("" + c);
        }
        else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            clearInputField();
        }
        else if (e.getKeyCode() == KeyEvent.VK_DECIMAL || c == '.') {
            decimalPointClicked();
        }
        else if (c == KeyEvent.VK_UNDO) {
            undoClicked();
        }
        else if (c == KeyEvent.VK_V) {
            sqrtClicked();
        }
        else if (e.getKeyChar() == Evaluator.SQUARE) {
            squareClicked();
        }
        else if (c == KeyEvent.VK_LEFT_PARENTHESIS) {
            openingBracketClicked();
        }
        else if (c == KeyEvent.VK_RIGHT_PARENTHESIS) {
            closingBracketClicked();
        }
        else if (c == KeyEvent.VK_ENTER) {
            equalsClicked();
        }
        else if (e.getKeyChar() == '%') {
            percentClicked();
        }
        else if (c == KeyEvent.VK_0 ||
                c == KeyEvent.VK_1 ||
                c == KeyEvent.VK_2 ||
                c == KeyEvent.VK_3 ||
                c == KeyEvent.VK_4 ||
                c == KeyEvent.VK_5 ||
                c == KeyEvent.VK_6 ||
                c == KeyEvent.VK_7 ||
                c == KeyEvent.VK_8 ||
                c == KeyEvent.VK_9 ||
                c == KeyEvent.VK_NUMPAD0 ||
                c == KeyEvent.VK_NUMPAD1 ||
                c == KeyEvent.VK_NUMPAD2 ||
                c == KeyEvent.VK_NUMPAD3 ||
                c == KeyEvent.VK_NUMPAD4 ||
                c == KeyEvent.VK_NUMPAD5 ||
                c == KeyEvent.VK_NUMPAD6 ||
                c == KeyEvent.VK_NUMPAD7 ||
                c == KeyEvent.VK_NUMPAD8 ||
                c == KeyEvent.VK_NUMPAD9) {
            updateInputField("" + e.getKeyChar());
        }
        else {
            // Do nothing.
        }

    }

}
