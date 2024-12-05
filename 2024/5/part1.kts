import java.io.File
import java.io.InputStream

val inputStream: InputStream = File("input.txt").inputStream()
var rules: MutableList<Rule> = mutableListOf()
var updates: MutableList<MutableList<Int>> = mutableListOf()
inputStream.bufferedReader().forEachLine {
    if (it.length == 5) {
        val a = it.split("|")[0].toInt()
        val b = it.split("|")[1].toInt()
        rules.add(Rule(a, b))
    } else if (it.isNotEmpty()) {
        updates.add(it.split(",").map { page -> page.toInt() }.toMutableList())
    }
}

var sumOfMiddlePagesOfComplyingUpdates =
    updates.stream().filter { update -> rules.all { rule -> complies(rule, update) } }
        .map { update -> update.get(update.size / 2) }.toList().sum()

print(sumOfMiddlePagesOfComplyingUpdates)

class Rule(val isBefore: Int, val isAfter: Int)

fun complies(rule: Rule, update: MutableList<Int>): Boolean {

    if (update.indexOf(rule.isBefore) == -1 || update.indexOf(rule.isAfter) == -1) {
        return true
    }

    return update.indexOf(rule.isBefore) < update.indexOf(rule.isAfter)
}
