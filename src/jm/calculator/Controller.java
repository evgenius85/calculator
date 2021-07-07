package jm.calculator;

import java.util.Scanner;
import java.util.List;

public class Controller {

    public static final int INVALID_OPERATION = -1;
    public static final int MIN_VALUE = 1;
    public static final int MAX_VALUE = 10;

    public static String expression;

    public static int firstNum;
    public static int secondNum;

    public static boolean firstNumIsRoman;
    public static boolean secondNumIsRoman;

    public static char[] firstNumRoman;
    public static char[] secondNumRoman;

    public static int resultInt;
    public static String resultString;

    public static char[] arithmeticOperations = {'+', '-', '*', '/'};
    public static int operationPosition = INVALID_OPERATION;
    public static int typeOfOperation;
    public static boolean operationIsValid;

    public static void inputCalculation() {
        Scanner sc = new Scanner(System.in);
        expression = sc.nextLine();
        expression = expression.trim();
    }

    public static void calculation() {

        determineOperation();

        determineFirstNum();
        determineSecondNum();

        numeralsSimilar();
        numberIsInRange(firstNum);
        numberIsInRange(secondNum);

        makeCalculation();

        convertResult2string();
    }

    private static void determineOperation() {
        while (!operationIsValid) {
            try {
                operationPosition = expression.indexOf(arithmeticOperations[typeOfOperation]);
            } catch (Exception ex) {
                System.out.println("The arithmetic operation is not valid. Shutting down");
                System.exit(-1);
            }

            if (operationPosition != INVALID_OPERATION) {
                operationIsValid = true;
            } else {
                typeOfOperation++;
            }
        }
    }

    private static void determineSecondNum() {
        try {
            secondNum = Integer.parseInt(expression.substring(operationPosition + 1).trim());
        } catch (Exception ex) {
            secondNumRoman = expression.substring(operationPosition + 1).trim().toLowerCase().toCharArray();
            secondNum = convertRoman2arabic(secondNumRoman);
            secondNumIsRoman = true;
        }
    }

    private static void determineFirstNum() {
        try {
            firstNum = Integer.parseInt(expression.substring(0, operationPosition).trim());
        } catch (Exception ex) {
            firstNumRoman = expression.substring(0, operationPosition).trim().toLowerCase().toCharArray();
            firstNum = convertRoman2arabic(firstNumRoman);
            firstNumIsRoman = true;
        }
    }

    private static void numeralsSimilar() {
        if (firstNumIsRoman != secondNumIsRoman) {
            System.out.println("Both numbers must be Arabic or Roman. Shutting down");
            System.exit(-1);
        }
    }

    private static void numberIsInRange(int number) {
        try {
            if (number > MAX_VALUE || number < MIN_VALUE) {
                throw new Exception("The number must be in range %i to 10");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Shutting down");
            System.exit(-1);
        }
    }

    private static void makeCalculation() {
        switch (arithmeticOperations[typeOfOperation]) {
            case '+' -> resultInt = firstNum + secondNum;
            case '-' -> resultInt = firstNum - secondNum;
            case '*' -> resultInt = firstNum * secondNum;
            case '/' -> resultInt = firstNum / secondNum;
        }
    }

    public static String convertArabic2Roman(int number) {
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while (number > 0 && i < romanNumerals.size()) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }
        return sb.toString();
    }

    private static int convertRoman2arabic(char[] roman) {
        int arabic = 0;
        for (int i = 0; i < roman.length; i++) {
            try {
                switch (roman[i]) {
                    case 'i':
                        arabic++;
                        break;
                    case 'v':
                        try {
                            if (roman[i-1] == 'i') {
                                arabic += 3;
                            }
                        } catch (Exception ex) {
                            arabic += 5;
                    }
                        break;
                    case 'x':
                        try {
                            if (roman[i-1] == 'i') {
                                arabic += 8;
                            }
                        } catch (Exception ex) {
                            arabic += 10;
                        }
                        break;
                }
            } catch (Exception ex) {
                System.out.println("Roman number is invalid. Shutting down");
                System.exit(-1);
            }
        }
        return arabic;
    }

    private static void convertResult2string() {
        if (firstNumIsRoman && secondNumIsRoman) {
            resultString = convertArabic2Roman(resultInt);
        } else {
            resultString = Integer.toString(resultInt);
        }
    }
}