import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()

class Scratchcard(val cardNumber: Int, val winningNumbers: List<Int>, val yourNumbers: List<Int>)

val scratchcards = mutableListOf<Scratchcard>()

inputStream.bufferedReader().forEachLine {
    val cardNumber = it.split(":")[0].split("\\s+".toRegex())[1].trim().toInt()
    val winningNumbers = it.split(":")[1].split("|")[0].split("\\s+".toRegex()).filter{l -> l.isNotBlank()}.map { number -> number.toInt() }.toList()
    val yourNumbers = it.split(":")[1].split("|")[1].split("\\s+".toRegex()).filter{l -> l.isNotBlank()}.map { number -> number.toInt() }.toList()
    scratchcards.add(Scratchcard(cardNumber, winningNumbers, yourNumbers))
}

var totalPoints = 0

scratchcards.forEach{ card ->
    var cardPoints = 0
    card.yourNumbers.filter { number -> card.winningNumbers.any { it == number } }.forEach {
        if (cardPoints == 0) {
            cardPoints = 1
        } else {
            cardPoints += cardPoints
        }
    }
    totalPoints += cardPoints
}

println(totalPoints)