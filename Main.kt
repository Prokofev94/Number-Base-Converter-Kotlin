package converter

import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.pow

fun main() {
    while (true) {
        println("Enter two numbers in format: {source base} {target base} (To quit type /exit)")
        val bases = readln()
        if (bases == "/exit") break
        val (sourceBase, targetBase) = bases.split(" ").map { it.toInt() }
        while (true) {
            println("Enter number in base $sourceBase to convert to base $targetBase (To go back type /back)")
            val number = readln()
            if (number == "/back") break
            convent(number, sourceBase, targetBase)
            println()
        }
        println()
    }
}

fun convent(number: String, sourceBase: Int, targetBase: Int) {
    var integer = number
    var fraction = ""
    if (number.contains(".")) {
        integer = number.split(".")[0]
        fraction = number.split(".")[1]
    }
    var decInteger = BigInteger.ZERO
    var power = 0.0
    for (c in integer.reversed()) {
        decInteger += (sourceBase.toDouble().pow(power) * c.digitToInt(sourceBase)).toBigDecimal().toBigInteger()
        power++
    }
    if (fraction.isNotEmpty()) {
        var decFraction = BigDecimal.ZERO
        power = -1.0
        for (c in fraction) {
            decFraction += (sourceBase.toDouble().pow(power) * c.digitToInt(sourceBase)).toBigDecimal()
            power--
        }
        fraction = "."
        for (i in 0..4) {
            if (decFraction == BigDecimal.ZERO) break
            decFraction *= BigDecimal(targetBase)
            val reminder = (decFraction.divideToIntegralValue(BigDecimal.ONE)).toInt()
            fraction += reminder.toString(targetBase)
            decFraction %= BigDecimal.ONE
        }
    }
    println("Conversion result: ${decInteger.toString(targetBase)}$fraction")
}