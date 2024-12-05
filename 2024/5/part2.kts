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

var reorderedUncomplyingUpdates =
    updates.stream().filter { update -> rules.any { rule -> !complies(rule, update) } }.toList()
        .map { update -> reorder(update, rules) }
        .map { update -> update.get(update.size / 2) }
        .toList()
        .sum()

print(reorderedUncomplyingUpdates)

class Rule(val before: Int, val after: Int)

fun complies(rule: Rule, update: MutableList<Int>): Boolean {

    if (update.indexOf(rule.before) == -1 || update.indexOf(rule.after) == -1) {
        return true
    }

    return update.indexOf(rule.before) < update.indexOf(rule.after)
}

fun reorder(update: MutableList<Int>, rules: MutableList<Rule>): MutableList<Int> {

    var rulesToApply = rules.filter { rule -> update.contains(rule.before) && update.contains(rule.before)}.toMutableList()

    var orderingRule = getOrderingRule(rulesToApply)

    return update.sortedBy { orderingRule.indexOf(it) }.toMutableList()
}

fun getOrderingRule(rules: MutableList<Rule>): Set<Int> {

    val allBefores = rules.map { it.before }
    val allAfters = rules.map { it.after }
    val pagesAtStart = allBefores.filter { it !in allAfters }.toSet()
    val pagesAtEnd = allAfters.filter { it !in allBefores }.toSet()

    val globalOrderingRule: MutableList<Int> = pagesAtStart.toMutableList()
    globalOrderingRule.addAll(pagesAtEnd)

    val allRemainingPages = rules.flatMap { rule -> mutableListOf(rule.after, rule.before) }.toMutableSet()
    allRemainingPages.removeAll(pagesAtEnd)
    allRemainingPages.removeAll(pagesAtStart)

    for (page in allRemainingPages) {

        val minIndexOfBeforePage = rules.filter { rule -> rule.after == page }.map { r -> r.before }
            .maxOf { b -> globalOrderingRule.indexOf(b) }
        val maxIndexOfAfterPage = rules.map { r -> r.after }.minOf { b -> globalOrderingRule.indexOf(b) }

        if (minIndexOfBeforePage != -1) {
            globalOrderingRule.add(minIndexOfBeforePage + 1, page)
        } else if (maxIndexOfAfterPage != -1) {
            globalOrderingRule.add(maxIndexOfAfterPage, page)
        }
    }

    rules.filter { !complies(it, globalOrderingRule) }
        .forEach { println("l'ordre ne respecte pas la règle ${it.before}, ${it.after}") }

    return globalOrderingRule.toSet()
}
