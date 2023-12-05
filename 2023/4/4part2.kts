import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()

class Scratchcard(val cardNumber: Int, val winningNumbers: List<Int>, val yourNumbers: List<Int>, var bingos: Int = 0, var copies: Int = 1)

val scratchcards = mutableListOf<Scratchcard>()

inputStream.bufferedReader().forEachLine {
    val cardNumber = it.split(":")[0].split("\\s+".toRegex())[1].trim().toInt()
    val winningNumbers = it.split(":")[1].split("|")[0].split("\\s+".toRegex()).filter { l -> l.isNotBlank() }.map { number -> number.toInt() }.toList()
    val yourNumbers = it.split(":")[1].split("|")[1].split("\\s+".toRegex()).filter { l -> l.isNotBlank() }.map { number -> number.toInt() }.toList()
    scratchcards.add(Scratchcard(cardNumber, winningNumbers, yourNumbers))
}

var totalPoints = 0

for (card in scratchcards) {
    repeat(card.yourNumbers.filter { number -> card.winningNumbers.any { it == number } }.size) {
        ++card.bingos
    }

    for (i in 1..card.copies) {
        for (y in 1..card.bingos) {
            val cardToCopy = scratchcards.find { test -> test.cardNumber == card.cardNumber + y }
            if (cardToCopy != null) {
                ++cardToCopy.copies
            }
        }
    }
}

println(scratchcards.sumOf { it.copies })