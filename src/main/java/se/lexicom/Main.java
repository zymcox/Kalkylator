package se.lexicom;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Välkommen till kalkylatorn!");
        System.out.println("Skriv ett matematiskt uttryck, eller 'exit' för att avsluta.");
        System.out.println("3 + 5 * (2 - 8) eller något annat.");

        while (true) {
            System.out.print("Ange ditt uttryck: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Avslutar kalkylatorn. Tack för att du använde den!");
                break;
            }

            try {
                double result = evaluate(input);
                System.out.println("Resultat: " + result);
            } catch (Exception e) {
                System.out.println("Fel i uttrycket: " + e.getMessage());
            }
        }

        scanner.close();
    }

    public static double evaluate(String expression) {
        List<String> postfix = toPostfix(expression);
        return evaluatePostfix(postfix);
    }

    private static List<String> toPostfix(String expression) {
        Stack<Character> operators = new Stack<>();
        List<String> output = new ArrayList<>();
        StringBuilder numberBuffer = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                // Bygg upp ett tal (inklusive decimaler)
                numberBuffer.append(c);
            } else {
                if (!numberBuffer.isEmpty()) {
                    // Lägg till färdigt tal till output
                    output.add(numberBuffer.toString());
                    numberBuffer.setLength(0);
                }

                if (c == '(') {
                    operators.push(c);
                } else if (c == ')') {
                    // Hämta alla operatorer tills vi hittar '('
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        output.add(String.valueOf(operators.pop()));
                    }
                    if (operators.isEmpty()) {
                        throw new IllegalArgumentException("Obalanserade parenteser.");
                    }
                    operators.pop(); // Ta bort '('
                } else if (isOperator(c)) {
                    // Hantera operatorer med prioritet
                    while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                        output.add(String.valueOf(operators.pop()));
                    }
                    operators.push(c);
                }
            }
        }

        // Lägg till sista talet i bufferten
        if (!numberBuffer.isEmpty()) {
            output.add(numberBuffer.toString());
        }

        // Töm stacken på kvarvarande operatorer
        while (!operators.isEmpty()) {
            char op = operators.pop();
            if (op == '(' || op == ')') {
                throw new IllegalArgumentException("Obalanserade parenteser.");
            }
            output.add(String.valueOf(op));
        }

        return output;
    }

    private static double evaluatePostfix(List<String> postfix) {
        Stack<Double> stack = new Stack<>();

        for (String token : postfix) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token.charAt(0))) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Felaktigt uttryck.");
                }
                double b = stack.pop();
                double a = stack.pop();
                switch (token.charAt(0)) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/':
                        if (b == 0) {
                            throw new ArithmeticException("Division med noll är inte tillåten.");
                        }
                        stack.push(a / b);
                        break;
                }
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Felaktigt uttryck.");
        }

        return stack.pop();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int precedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            default -> 0;
        };
    }

    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
