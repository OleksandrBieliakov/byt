package byt_4_5

import java.lang.Exception

// Implementation of simple CLI calculator using the chain of responsibility design pattern

fun main() {
    testCalculator()
}

fun testCalculator() {
    val calculator = Calculator()
    var request = "1.2 + 2   "
    calculator.serveRequestAndPrintResult(request)

    request = "1   -   2.5"
    calculator.serveRequestAndPrintResult(request)

    request = "   3 * 2"
    calculator.serveRequestAndPrintResult(request)

    request = "1 / 2"
    calculator.serveRequestAndPrintResult(request)

    request = "1 / 0"
    calculator.serveRequestAndPrintResult(request)

    request = "1 ^ 2"
    calculator.serveRequestAndPrintResult(request)

    request = "1 + 2 4"
    calculator.serveRequestAndPrintResult(request)

    request = "1 + 2,4"
    calculator.serveRequestAndPrintResult(request)

    request = "1 + word"
    calculator.serveRequestAndPrintResult(request)
}

abstract class OperationHandler(var next: OperationHandler? = null) {
    abstract fun calculate(operation: String, numbers: Pair<Double, Double>): Double
    protected fun delegateToNext(operation: String, numbers: Pair<Double, Double>): Double {
        return when (next) {
            null -> throw Exception("Not supported operation")
            else -> next!!.calculate(operation, numbers)
        }
    }
}

class AdditionHandler : OperationHandler() {
    override fun calculate(operation: String, numbers: Pair<Double, Double>): Double {
        return when (operation) {
            "+" -> numbers.first + numbers.second
            else -> delegateToNext(operation, numbers)
        }
    }
}

class SubtractionHandler : OperationHandler() {
    override fun calculate(operation: String, numbers: Pair<Double, Double>): Double {
        return when (operation) {
            "-" -> numbers.first - numbers.second
            else -> delegateToNext(operation, numbers)
        }
    }
}

class MultiplicationHandler : OperationHandler() {
    override fun calculate(operation: String, numbers: Pair<Double, Double>): Double {
        return when (operation) {
            "*" -> numbers.first * numbers.second
            else -> delegateToNext(operation, numbers)
        }
    }
}

class DivisionHandler : OperationHandler() {
    override fun calculate(operation: String, numbers: Pair<Double, Double>): Double {
        return when (operation) {
            "/" -> {
                if (numbers.second == 0.0)
                    throw Exception("Cannot divide by zero")
                return numbers.first / numbers.second
            }
            else -> delegateToNext(operation, numbers)
        }
    }
}

class Calculator(private var firstHandler: OperationHandler? = null) {
    companion object {
        private const val PARTS_SIZE = 3
        private const val NUMBER1_INDEX = 0
        private const val NUMBER2_INDEX = 2
        private const val OPERATION_INDEX = 1
        private val DELIMITER_REGEX: Regex = "\\s+".toRegex()
    }

    init {
        val addition = AdditionHandler()
        val subtraction = SubtractionHandler()
        val multiplication = MultiplicationHandler()
        val division = DivisionHandler()
        multiplication.next = division
        subtraction.next = multiplication
        addition.next = subtraction
        firstHandler = addition
    }

    private fun calculate(operation: String, numbers: Pair<Double, Double>): Double {
        if (firstHandler == null)
            throw Exception("No calculation operations are supported")
        return firstHandler!!.calculate(operation, numbers)
    }

    private fun splitRequest(request: String): List<String> {
        return request.trim().split(DELIMITER_REGEX)
    }

    private fun serveCalculationRequest(request: String): String {
        val parts: List<String> = splitRequest(request)
        if (parts.size != PARTS_SIZE) {
            return "Incorrect input format, expected \"NUMBER OPERATION NUMBER\""
        }
        val number1: Double
        val number2: Double
        try {
            number1 = parts[NUMBER1_INDEX].toDouble()
            number2 = parts[NUMBER2_INDEX].toDouble()
        } catch (exception: IllegalArgumentException) {
            return "Incorrect input format, expected \"NUMBER OPERATION NUMBER\", NUMBER can be an Integer or a Dot-decimal"
        }
        val numbers = Pair(number1, number2)
        val operation = parts[OPERATION_INDEX]
        val result: Double
        try {
            result = calculate(operation, numbers)
        } catch (exception: Exception) {
            return exception.message.toString()
        }
        return result.toString()
    }

    fun serveRequestAndPrintResult(request: String) {
        val response = serveCalculationRequest(request)
        println("$request = $response")
    }
}
