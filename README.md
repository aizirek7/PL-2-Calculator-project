# Calculator Application

## Overview
The **Calculator Application** is a simple command-line arithmetic calculator that evaluates user-entered mathematical expressions. It supports basic arithmetic operations, functions, and maintains a history of calculations.

## Features
- Supports basic arithmetic operations: addition (`+`), subtraction (`-`), multiplication (`*`), division (`/`), and modulus (`%`).
- Includes mathematical functions: absolute value (`abs`), square root (`sqrt`), rounding (`round`), and exponentiation (`power`).
- Implements an infix-to-postfix conversion for expression evaluation.
- Handles parentheses for order of operations.
- Provides a calculation history.
- Ensures proper error handling for invalid input and division by zero.

## Installation
1. Clone or download the repository.
2. Ensure you have Java installed (Java 8 or later).
3. Compile the program using:
   ```sh
   javac Main.java
   ```
4. Run the program:
   ```sh
   java Main
   ```

## Usage
1. Run the program and enter arithmetic expressions when prompted.
2. The calculator evaluates the expression and displays the result.
3. The program will ask if you want to continue; enter `yes` to proceed or `no` to exit.
4. Upon exit, the full history of calculations is displayed.

### Example Inputs and Outputs
```
Welcome to the Calculator
Please enter your arithmetic expression: 5 + 3 * 2
Result: 11.0
Do you want to continue? (yes/no): yes
Please enter your arithmetic expression: power(2, 3)
Result: 8.0
Do you want to continue? (yes/no): no
Calculation History:
5 + 3 * 2 = 11.0
power(2, 3) = 8.0
Thank you for using the Calculator!
```

## Code Structure
- **Main Class**: Manages user interaction and program flow.
- **evaluateExpression()**: Parses and evaluates expressions.
- **tokenize()**: Splits expressions into tokens (numbers, operators, functions).
- **infixToPostfix()**: Converts infix expressions to postfix notation.
- **evaluatePostfix()**: Computes the final result from a postfix expression.

## Error Handling
- Invalid characters result in an error message.
- Division by zero is prevented with a warning message.
- Improperly formatted expressions are detected and rejected.

## Future Enhancements
- Implement support for trigonometric functions (sin, cos, tan).
- Add support for logarithmic and exponential functions.
- Develop a GUI version for a better user experience.


