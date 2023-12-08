import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()

val cardRanks = mapOf(
    'J' to 10,
    '2' to 11,
    '3' to 12,
    '4' to 13,
    '5' to 14,
    '6' to 15,
    '7' to 16,
    '8' to 17,
    '9' to 18,
    'T' to 19,
    'Q' to 20,
    'K' to 21,
    'A' to 22
)

enum class TypeHandEnum(val strength: Int) {
    FIVE_OF_A_KIND(7),
    FOUR_OF_A_KIND(6),
    FULL_HOUSE(5),
    THREE_OF_A_KIND(4),
    TWO_PAIRS(3),
    ONE_PAIR(2),
    HIGH_CARD(1)
}

class Hand(val bid: Int, var cards: List<Int>) {

    private val type: TypeHandEnum = computeType(cards)

    val value: Long = computeValue(type, cards)

    private fun computeValue(type: TypeHandEnum, cards: List<Int>): Long {
        var valueStringified = type.strength.toString()
        for (card in cards) {
            valueStringified += card.toString()
        }
        return valueStringified.toLong()
    }

    private fun computeType(cards: List<Int>): TypeHandEnum {
        val numberOfJokers = cards.filter { c -> c == 10 }.size
        val cardsForTest = cards.toMutableList()
        cardsForTest.removeIf { c -> c == 10 }
        return when (val maxCopies = getMaxCopies(cardsForTest) + numberOfJokers) {
            1 -> TypeHandEnum.HIGH_CARD
            4 -> TypeHandEnum.FOUR_OF_A_KIND
            5 -> TypeHandEnum.FIVE_OF_A_KIND
            else -> computeRemainingTypes(cardsForTest, maxCopies)
        }
    }

    private fun computeRemainingTypes(cards: List<Int>, maxCopies: Int): TypeHandEnum = when (cards.toSet().size) {
        2 -> TypeHandEnum.FULL_HOUSE
        3 -> if (maxCopies == 3) TypeHandEnum.THREE_OF_A_KIND else TypeHandEnum.TWO_PAIRS
        4 -> TypeHandEnum.ONE_PAIR
        else -> throw Exception("Whoops")
    }

    private fun getMaxCopies(cards: List<Int>): Int {
        var maxCopies = 0
        for (card in cards) {
            val copies = cards.filter { c -> c == card }.size
            if (copies > maxCopies) {
                maxCopies = copies
            }
        }
        return maxCopies
    }
}

val hands = mutableListOf<Hand>()

inputStream.bufferedReader().forEachLine { line ->
    val cards = line.split(" ")[0].toCharArray().map { char -> cardRanks[char]!! }.toList()
    hands.add(Hand(bid = line.split(" ")[1].toInt(), cards = cards))
}

hands.sortWith(compareBy { it.value })

var totalWinnings = hands.mapIndexed { i, hand ->
    hand.bid * (i + 1)
}.sum()

println(totalWinnings)