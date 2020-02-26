package main;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionEvaluator {
    // Constants which represent logical operations
    private static final String notSymbol = "!";
    private static final String andSymbol = "&&";
    private static final String orSymbol = "||";

    // Regex pattern that exactly matches a token (an atomic word/ operand, operators and
    // parantheses.
    private static final Pattern tokenPattern =
            Pattern.compile("!|&&|\\(|\\)|\\|\\||[a-zA-Z0-9']+");

    private final InvertedIndexTable table;

    public ExpressionEvaluator(InvertedIndexTable table) {
        this.table = table;
    }

    /**
     * Evaluate a logical expression with words as CBTuples which are pseudo-truth values.
     * It does this by keeping track of the operators and operands in two different stacks
     * and applying the operations when necessary.
     * @param expression expression to be evaluated
     * @return tuple with truth values as entries
     */
    public CBTuple evaluate(String expression) {
        expression = expression.toLowerCase();
        Matcher tokenMatcher = tokenPattern.matcher(expression);
        Stack<String> operatorStack = new Stack<>();
        Stack<CBTuple> operandStack = new Stack<>();

        while (tokenMatcher.find()) {
            String token = tokenMatcher.group();
            switch (token) {
                case notSymbol:
                case "(":
                    operatorStack.push(token);
                    break;
                case andSymbol:
                case orSymbol:
                    // Apply all operations until the enclosing paranthesis
                    while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                        applyTop(operatorStack, operandStack);
                    }

                    operatorStack.push(token);
                    break;
                case ")":
                    while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                        applyTop(operatorStack, operandStack);
                    }

                    if (operatorStack.peek().equals("(")) {
                        operatorStack.pop();
                    } else {
                        System.out.println("ERROR: Expression is not correctly paranthesized.");
                        System.exit(2);
                    }
                    break;
                default:
                    operandStack.push(table.get(token));
                    break;

            }
        }

        // Apply the last operators
        while (!operatorStack.isEmpty()) {
            applyTop(operatorStack, operandStack);
        }

        return operandStack.peek();
    }

    /**
     * Apply the top operator to the top operands and push the result on the operand stack.
     */
    private static void applyTop(Stack<String> operatorStack, Stack<CBTuple> operandStack) {
        String operator = operatorStack.pop();

        switch (operator) {
            case notSymbol:
                CBTuple notOperand = operandStack.pop();
                operandStack.push(CBTuple.not(notOperand));
                break;
            case andSymbol:
                CBTuple andOperand2 = operandStack.pop();
                CBTuple andOperand1= operandStack.pop();
                operandStack.push(CBTuple.and(andOperand1, andOperand2));
                break;
            case orSymbol:
                CBTuple orOperand2 = operandStack.pop();
                CBTuple orOperand1 = operandStack.pop();
                operandStack.push(CBTuple.or(orOperand1, orOperand2));
                break;
            default:
                System.out.println("ERROR: OPERATOR DOES NOT EXIST");
                System.exit(1);
                break;
        }
    }
}
