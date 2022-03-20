import org.junit.Test

import org.junit.Assert.*

class MainKtTest {

//    @Test
//    fun moneyTransfer_default() {
//        val transferAmount = 1_000 * RUB
//
//        val result = moneyTransfer(
//            amount = transferAmount,
//        )
//        assertEquals(true, result)
//    }

    @Test
    fun moneyTransfer_fail() {
        val someAccount = "some account"
        val transferAmount = 1_000 * RUB
        val totalAmount = 100_000 * RUB

        val result = moneyTransfer(
            accountType = someAccount,
            amount = transferAmount,
            monthAmount = totalAmount
        )
        assertEquals(false, result)
    }

    @Test
    fun moneyTransfer_pass() {
        val accountList = listOf(VK_PAY, MAESTRO, MASTER_CARD, VISA, MIR)
        val transferAmount = 1_000 * RUB
        val totalAmount = 100_000 * RUB
        var result = false

        for (item in accountList) {
            result = moneyTransfer(
                accountType = item,
                amount = transferAmount,
                monthAmount = totalAmount
            )
            if (!result) break
        }
        assertEquals(true, result)
    }

//    @Test
//    fun calcFee_default() {
//        val transferAmount = 1_000 * RUB
//
//        val result = calcFee(
//            amount = transferAmount,
//        )
//        assertEquals(0, result)
//    }

    @Test
    fun calcFee_unknownAccount() {
        val someAccount = "unknown"
        val transferAmount = 1_000 * RUB
        val totalAmount = 100_000 * RUB

        val result = calcFee(
            accountType = someAccount,
            amount = transferAmount,
            monthAmount = totalAmount
        )

        assertEquals(-1, result)
    }

    @Test
    fun masterCardFee_noFee() {
        val transferAmount = 1_000 * RUB
        val totalAmount = 74_000 * RUB

        val result = masterCardFee(
            amount = transferAmount,
            monthAmount = totalAmount
        )

        assertEquals(0, result)
    }

    @Test
    fun masterCardFee_hasFee() {
        val transferAmount = 299 * RUB
        val totalAmount = 75_000 * RUB

        val result = masterCardFee(
            amount = transferAmount,
            monthAmount = totalAmount
        )

        assertEquals(2179, result)
    }

    @Test
    fun mirFee_minFee() {
        val transferAmount = 466_799

        val result = mirFee(
            amount = transferAmount
        )

        assertEquals(3500, result)
    }

    @Test
    fun mirFee_maxFee() {
        val transferAmount = 466_800

        val result = mirFee(
            amount = transferAmount
        )

        assertEquals(3501, result)
    }

//    @Test
//    fun kopecksToRubles() {
//        val transferAmount = 466_800
//
//        val result = kopecksToRubles(
//            amount = transferAmount
//        )
//
//        assertEquals(4668.00, result, 0.01)
//    }
}