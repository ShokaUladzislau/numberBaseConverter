package converter;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final String DIGITS = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            String[] parameters = scanner.nextLine().split("\\s+");
            if ("/exit".equalsIgnoreCase(parameters[0])) {
                return;
            }
            int[] bases = Arrays.stream(parameters).mapToInt(Integer::parseInt).toArray();

            while (true) {
                System.out.printf("Enter number in base %d to convert to base %d (To go back type /back) ",
                        bases[0], bases[1]);

                String number = scanner.nextLine();
                if ("/back".equalsIgnoreCase(number)) {
                    break;
                }
                System.out.println("Conversion result: " + fromToRadix(number, bases[0], bases[1]));
            }
        }
    }

    public static String fromToRadix(String number, int sourceBase, int targetBase) {
        int dotIndex = number.indexOf('.');

        if (dotIndex == -1) {
            return new BigInteger(number, sourceBase).toString(targetBase);
        }

        String integerPart = number.substring(0, dotIndex);
        String fractionPart = number.substring(dotIndex + 1);
        String convertedIntegerPart = new BigInteger(integerPart, sourceBase).toString(targetBase);

        double decimalFraction = 0.0;
        double divider = (double) sourceBase;

        for (char digit : fractionPart.toCharArray()) {
            decimalFraction += (DIGITS.indexOf(digit) / divider);
            divider *= sourceBase;
        }

        StringBuilder convertedFraction = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            decimalFraction *= targetBase;
            int index = (int) decimalFraction;
            convertedFraction.append(DIGITS.charAt(index));
            decimalFraction -= index;
        }
        return convertedIntegerPart + "." + convertedFraction;
    }
}