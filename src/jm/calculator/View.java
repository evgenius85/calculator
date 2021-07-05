package jm.calculator;

public class View {

    public static void showGreeting() {
        System.out.println("Hello! This is simple Arabic and Roman numerals calculator");
        System.out.println("You can add, subtract, multiply and divide by symbols +, -, * and /");
        System.out.println("Please enter expression in one line:");
    }

    public static void showResult(String result) {
        System.out.println(result);
    }
}
