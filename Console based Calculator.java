import java.util.Scanner;

public class EnhancedCalculator {

    static Scanner sc = new Scanner(System.in);

    static double add(double a, double b) {
        return a + b;
    }

    static double subtract(double a, double b) {
        return a - b;
    }

    static double multiply(double a, double b) {
        return a * b;
    }

    static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return a / b;
    }

    static double squareRoot(double a) {
        if (a < 0) {
            throw new ArithmeticException("Square root of negative number not allowed");
        }
        return Math.sqrt(a);
    }

    static double power(double a, double b) {
        return Math.pow(a, b);
    }

    static double celsiusToFahrenheit(double c) {
        return (c * 9 / 5) + 32;
    }

    static double fahrenheitToCelsius(double f) {
        return (f - 32) * 5 / 9;
    }

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n====== ENHANCED CALCULATOR ======");
            System.out.println("1. Addition");
            System.out.println("2. Subtraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("5. Square Root");
            System.out.println("6. Power");
            System.out.println("7. Celsius to Fahrenheit");
            System.out.println("8. Fahrenheit to Celsius");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter two numbers: ");
                        System.out.println("Result = " + add(sc.nextDouble(), sc.nextDouble()));
                        break;

                    case 2:
                        System.out.print("Enter two numbers: ");
                        System.out.println("Result = " + subtract(sc.nextDouble(), sc.nextDouble()));
                        break;

                    case 3:
                        System.out.print("Enter two numbers: ");
                        System.out.println("Result = " + multiply(sc.nextDouble(), sc.nextDouble()));
                        break;

                    case 4:
                        System.out.print("Enter two numbers: ");
                        System.out.println("Result = " + divide(sc.nextDouble(), sc.nextDouble()));
                        break;

                    case 5:
                        System.out.print("Enter a number: ");
                        System.out.println("Result = " + squareRoot(sc.nextDouble()));
                        break;

                    case 6:
                        System.out.print("Enter base and exponent: ");
                        System.out.println("Result = " + power(sc.nextDouble(), sc.nextDouble()));
                        break;

                    case 7:
                        System.out.print("Enter temperature in Celsius: ");
                        System.out.println("Fahrenheit = " + celsiusToFahrenheit(sc.nextDouble()));
                        break;

                    case 8:
                        System.out.print("Enter temperature in Fahrenheit: ");
                        System.out.println("Celsius = " + fahrenheitToCelsius(sc.nextDouble()));
                        break;

                    case 9:
                        System.out.println("Thank you for using the calculator!");
                        break;

                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 9);

        sc.close();
    }
}
