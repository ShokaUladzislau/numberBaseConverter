package converter

import java.math.BigInteger
import java.util.*

object Main {
    private const val DIGITS = "0123456789abcdefghijklmnopqrstuvwxyz"
    @JvmStatic
    fun main(args: Array<String>) {
        val scanner = Scanner(System.`in`)
        while (true) {
            print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ")
            val parameters = scanner.nextLine().split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if ("/exit".equals(parameters[0], ignoreCase = true)) {
                return
            }
            val bases = Arrays.stream(parameters).mapToInt { s: String -> s.toInt() }.toArray()
            while (true) {
                System.out.printf(
                    "Enter number in base %d to convert to base %d (To go back type /back) ",
                    bases[0], bases[1]
                )
                val number = scanner.nextLine()
                if ("/back".equals(number, ignoreCase = true)) {
                    break
                }
                println("Conversion result: " + fromToRadix(number, bases[0], bases[1]))
            }
        }
    }

    fun fromToRadix(number: String, sourceBase: Int, targetBase: Int): String {
        val dotIndex = number.indexOf('.')
        if (dotIndex == -1) {
            return BigInteger(number, sourceBase).toString(targetBase)
        }
        val integerPart = number.substring(0, dotIndex)
        val fractionPart = number.substring(dotIndex + 1)
        val convertedIntegerPart = BigInteger(integerPart, sourceBase).toString(targetBase)
        var decimalFraction = 0.0
        var divider = sourceBase.toDouble()
        for (digit in fractionPart.toCharArray()) {
            decimalFraction += (DIGITS.indexOf(digit) / divider)
            divider *= sourceBase.toDouble()
        }
        val convertedFraction = StringBuilder()
        for (i in 0..4) {
            decimalFraction *= targetBase.toDouble()
            val index = decimalFraction.toInt()
            convertedFraction.append(DIGITS[index])
            decimalFraction -= index.toDouble()
        }
        return "$convertedIntegerPart.$convertedFraction"
    }
}