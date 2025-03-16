package com.company;

import java.util.*;

public class Main {

    // List to store the history of calculations
    private static List<String> history = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueCalculating = true;


        System.out.println("Welcome to the Calculator");



        // Main loop to keep the calculator running until the user decides to exit
        while (continueCalculating) {
            System.out.print("Please enter your arithmetic expression: ");
            String input = scanner.nextLine();

            try {
                // Evaluate the expression and get the result
                double result = evaluateExpression(input);
                System.out.println("Result: " + result);
                // Add the expression and result to the history
                history.add(input + " = " + result);
            } catch (Exception e) {
                // Handle any errors that occur during evaluation
                System.out.println("Error: " + e.getMessage());
            }

            // Ask the user if they want to continue
            System.out.print("Do you want to continue? (yes/no): ");
            String choice = scanner.nextLine().toLowerCase();
            if (!choice.equals("yes")) {
                continueCalculating = false;
            }
        }

        // Display the calculation history
        System.out.println("Calculation History:");
        for (String entry : history) {
            System.out.println(entry);
        }

        System.out.println("Thank you for using the Calculator!");
        scanner.close();
    }

    // Method to evaluate the arithmetic expression
    private static double evaluateExpression(String expression) throws Exception {
        // Remove all whitespace from the expression
        expression = expression.replaceAll("\\s+", "");
        // Tokenize the expression into individual components (numbers, operators, functions)
        List<String> tokens = tokenize(expression);
        // Convert the infix expression to postfix (Reverse Polish Notation)
        List<String> postfix = infixToPostfix(tokens);
        // Evaluate the postfix expression and return the result
        return evaluatePostfix(postfix);
    }

    // Method to tokenize the expression into numbers, operators, and functions
    private static List<String> tokenize(String expression) throws Exception {
        List<String> tokens = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // If the character is a digit or a decimal point, add it to the current number
            if (Character.isDigit(c) || c == '.') {
                sb.append(c);
            }
            // Handle negative numbers
            else if (c == '-' && (i == 0 || !Character.isDigit(expression.charAt(i - 1)))) {
                if (sb.length() > 0) {
                    tokens.add(sb.toString());
                    sb.setLength(0);
                }
                sb.append(c);
            }
            // Handle operators, parentheses, and functions
            else {
                if (sb.length() > 0) {
                    tokens.add(sb.toString());
                    sb.setLength(0);
                }
                if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '(' || c == ')') {
                    tokens.add(String.valueOf(c));
                }
                // Handle the "abs" function
                else if (c == 'a' && expression.startsWith("abs", i)) {
                    tokens.add("abs");
                    i += 2; // Skip 'b' and 's'
                }
                // Handle the "sqrt" function
                else if (c == 's' && expression.startsWith("sqrt", i)) {
                    tokens.add("sqrt");
                    i += 3; // Skip 'q', 'r', 't'
                }
                // Handle the "round" function
                else if (c == 'r' && expression.startsWith("round", i)) {
                    tokens.add("round");
                    i += 4; // Skip 'o', 'u', 'n', 'd'
                }
                // Handle the "power" function
                else if (c == 'p' && expression.startsWith("power", i)) {
                    tokens.add("power");
                    i += 4; // Skip 'o', 'w', 'e', 'r'
                }
                // Handle invalid characters
                else {
                    throw new Exception("Invalid character: " + c);
                }
            }
        }

        // Add the last number if there is one
        if (sb.length() > 0) {
            tokens.add(sb.toString());
        }

        return tokens;
    }

    // Method to convert infix expression to postfix notation
    private static List<String> infixToPostfix(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();
        // Define the precedence of operators and functions
        Map<String, Integer> precedence = new HashMap<>();
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
        precedence.put("%", 2);
        precedence.put("power", 3);
        precedence.put("sqrt", 3);
        precedence.put("abs", 3);
        precedence.put("round", 3);

        // Process each token
        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) { // If the token is a number
                output.add(token);
            } else if (token.equals("(")) { // If the token is an opening parenthesis
                operators.push(token);
            } else if (token.equals(")")) { // If the token is a closing parenthesis
                // Pop operators from the stack to the output until an opening parenthesis is found
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                operators.pop(); // Remove the opening parenthesis
            } else if (token.equals("abs") || token.equals("sqrt") || token.equals("round") || token.equals("power")) {
                // If the token is a function, push it onto the stack
                operators.push(token);
            } else { // If the token is an operator
                // Pop higher precedence operators from the stack to the output
                while (!operators.isEmpty() && precedence.getOrDefault(operators.peek(), 0) >= precedence.getOrDefault(token, 0)) {
                    output.add(operators.pop());
                }
                operators.push(token);
            }
        }

        // Pop any remaining operators from the stack to the output
        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
    }

    // Method to evaluate the postfix expression
    private static double evaluatePostfix(List<String> postfix) throws Exception {
        Stack<Double> stack = new Stack<>();

        // Process each token in the postfix expression
        for (String token : postfix) {
            if (token.matches("-?\\d+(\\.\\d+)?")) { // If the token is a number
                stack.push(Double.parseDouble(token));
            } else if (token.equals("+")) { // Addition
                double b = stack.pop();
                double a = stack.pop();
                stack.push(a + b);
            } else if (token.equals("-")) { // Subtraction
                double b = stack.pop();
                double a = stack.pop();
                stack.push(a - b);
            } else if (token.equals("*")) { // Multiplication
                double b = stack.pop();
                double a = stack.pop();
                stack.push(a * b);
            } else if (token.equals("/")) { // Division
                double b = stack.pop();
                if (b == 0) {
                    throw new Exception("Division by zero is not allowed.");
                }
                double a = stack.pop();
                stack.push(a / b);
            } else if (token.equals("%")) { // Modulus
                double b = stack.pop();
                double a = stack.pop();
                stack.push(a % b);
            } else if (token.equals("power")) { // Power function
                double exponent = stack.pop();
                double base = stack.pop();
                stack.push(Math.pow(base, exponent));
            } else if (token.equals("sqrt")) { // Square root function
                double number = stack.pop();
                stack.push(Math.sqrt(number));
            } else if (token.equals("abs")) { // Absolute value function
                double number = stack.pop();
                stack.push(Math.abs(number));
            } else if (token.equals("round")) { // Round function
                double number = stack.pop();
                stack.push((double) Math.round(number));
            } else {
                throw new Exception("Invalid token: " + token);
            }
        }

        // The final result should be the only element left in the stack
        if (stack.size() != 1) {
            throw new Exception("Invalid expression.");
        }

        return stack.pop();
    }
}