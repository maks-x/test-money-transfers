const val VK_PAY = "VK Pay"
const val MASTER_CARD = "MasterCard"
const val MAESTRO = "Maestro"
const val VISA = "Visa"
const val MIR = "Мир"

const val RUB = 100

const val AMOUNT = 10_000 * RUB

fun main() {
    moneyTransfer(amount = AMOUNT)
    moneyTransfer(MASTER_CARD, AMOUNT, 66_000 * RUB)
    moneyTransfer(MAESTRO, AMOUNT)
    moneyTransfer(MAESTRO, 299 * RUB)
    moneyTransfer(MIR, AMOUNT)
    moneyTransfer(VISA, 500 * RUB)
    moneyTransfer("some account", AMOUNT)
}

fun moneyTransfer(accountType: String = VK_PAY, amount: Int, monthAmount: Int = 0): Boolean {
    val fee = calcFee(accountType, amount, monthAmount)
    val result: Boolean
    when (fee) {
        -1 -> {
            result = false
            println("\nAccount type: $accountType\nUnknown account type")
        }
        else -> {
            result = true
            println(
                """
        
            Account type: $accountType
            Month amount: ${kopecksToRubles(monthAmount)} RUB.
            Transfer amount: ${kopecksToRubles(amount)} RUB.
            The commission will be: ${kopecksToRubles(fee)} RUB.
            """.trimIndent()
            )
        }
    }
    println(if (result) "PASS" else "FAIL")
    return result
}

fun calcFee(
    accountType: String = VK_PAY,
    amount: Int,
    monthAmount: Int = 0
): Int {
    return when (accountType) {
        VK_PAY -> 0
        MASTER_CARD -> masterCardFee(amount, monthAmount)
        MAESTRO -> maestroFee(amount, monthAmount)
        VISA -> visaFee(amount)
        MIR -> mirFee(amount)
        else -> -1
    }
}

fun masterCardFee(amount: Int, monthAmount: Int): Int {
    return when {
        amount >= 300 * RUB && (monthAmount + amount) <= 75_000 * RUB -> 0
        else -> (0.006 * amount + 20 * RUB).toInt()
    }
}

fun mirFee(amount: Int): Int {
    val possibleFee = (0.0075 * amount).toInt()
    val minFee = 35 * RUB
    return if (possibleFee > minFee) possibleFee else minFee
}

//вдруг в будущем мы захотим поменять логику для Maestro и Visa
fun maestroFee(amount: Int, monthAmount: Int): Int {
    return masterCardFee(amount, monthAmount)
}

fun visaFee(amount: Int): Int {
    return mirFee(amount)
}

fun kopecksToRubles(amount: Int): Double {
    return amount.toDouble() / RUB
}