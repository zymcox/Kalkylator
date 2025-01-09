package se.lexicom;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Välkommen till kalkylatorn!");
        System.out.println("Välj en operation: +, -, *, / eller skriv 'exit' för att avsluta.");

        while (true) {
            System.out.print("Ange operation: ");
            String operation = scanner.nextLine();

            if (operation.equalsIgnoreCase("exit")) {
                System.out.println("Avslutar kalkylatorn. Tack för att du använde den!");
                break;
            }

            System.out.print("Ange första talet: ");
            double num1 = scanner.nextDouble();

            System.out.print("Ange andra talet: ");
            double num2 = scanner.nextDouble();

            // Rensa scanner-buffer
            scanner.nextLine();

            double result;
            switch (operation) {
                case "+":
                    result = num1 + num2;
                    System.out.println("Resultat: " + result);
                    break;
                case "-":
                    result = num1 - num2;
                    System.out.println("Resultat: " + result);
                    break;
                case "*":
                    result = num1 * num2;
                    System.out.println("Resultat: " + result);
                    break;
                case "/":
                    if (num2 != 0) {
                        result = num1 / num2;
                        System.out.println("Resultat: " + result);
                    } else {
                        System.out.println("Fel: Division med noll är inte tillåten.");
                    }
                    break;
                default:
                    System.out.println("Ogiltig operation. Försök igen.");
            }
        }

        scanner.close();
    }
}
