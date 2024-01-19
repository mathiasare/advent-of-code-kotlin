package y2023.day7

import util.readFileLines


val cardToValMapV1 = mapOf(
    Pair('T', 10),
    Pair('J', 11),
    Pair('Q', 12),
    Pair('K', 13),
    Pair('A', 14)
)

val cardToValMapV2 = mapOf(
    Pair('J', 1),
    Pair('T', 10),
    Pair('Q', 12),
    Pair('K', 13),
    Pair('A', 14)
)

fun main() {
    day7()
}

fun day7() {
    val lines = readFileLines("inputD7")
    println("Part 1: " + solvePartOne(lines))
    println("Part 2: " + solvePartTwo(lines))
}

fun solvePartOne(lines: List<String>): Long {
    return lines.asSequence().map(::readHandV1)
        .sorted()
        .withIndex()
        .map { (rank, hand) -> (rank + 1) * hand.bid }
        .sum().toLong()
}

fun solvePartTwo(lines: List<String>): Long {
    return lines.asSequence().map(::readHandV2)
        .sorted()
        .withIndex()
        .map { (rank, hand) -> (rank + 1) * hand.bid }
        .sum().toLong()
}

fun readHandData(line: String, cardToValMap: Map<Char, Int>): Pair<List<Int>, Int> {
    val (cardsStr, bidStr) = line.split(" ")

    val cards = mutableListOf<Int>()
    for (c in cardsStr) {
        if (c.isDigit()) {
            cards.add(c.digitToInt())
        } else {
            cards.add(cardToValMap[c]!!)
        }
    }
    return Pair(cards, bidStr.toInt())
}

fun readHandV1(line: String): Hand {
    val (cards, bid) = readHandData(line, cardToValMapV1)
    return Hand(cards, bid)
}

fun readHandV2(line: String): HandV2 {
    val (cards, bid) = readHandData(line, cardToValMapV2)
    return HandV2(cards, bid)
}

enum class Combination {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_KIND,
    FULL_HOUSE,
    FOUR_OF_KIND,
    FIVE_OF_KIND
}

class HandV2(cards: List<Int>, bid: Int) : Hand(cards, bid) {

    override fun getCombination(): Combination {
        val combination = super.getCombination()

        if (!cards.contains(1)) {
            return combination
        }

        val jackCount = cards.count { it == 1 }

        return when (combination) {
            Combination.HIGH_CARD -> Combination.ONE_PAIR
            Combination.ONE_PAIR -> Combination.THREE_OF_KIND
            Combination.TWO_PAIR -> if (jackCount == 2) Combination.FOUR_OF_KIND else Combination.FULL_HOUSE
            Combination.THREE_OF_KIND -> if (jackCount == 2) Combination.FIVE_OF_KIND else Combination.FOUR_OF_KIND
            Combination.FULL_HOUSE -> Combination.FIVE_OF_KIND
            Combination.FOUR_OF_KIND -> Combination.FIVE_OF_KIND
            Combination.FIVE_OF_KIND -> Combination.FIVE_OF_KIND
        }
    }
}

open class Hand(val cards: List<Int>, val bid: Int) : Comparable<Hand> {

    open fun getCombination(): Combination {
        val distincts = cards.distinct()
        val distinctCount = cards.distinct().size

        return when (distinctCount) {
            1 -> Combination.FIVE_OF_KIND
            2 -> if (maxOccurences(distincts, cards) == 4) Combination.FOUR_OF_KIND else Combination.FULL_HOUSE
            3 -> if (maxOccurences(distincts, cards) == 3) Combination.THREE_OF_KIND else Combination.TWO_PAIR
            4 -> Combination.ONE_PAIR
            else -> Combination.HIGH_CARD
        }
    }

    private fun maxOccurences(distincts: List<Int>, all: List<Int>): Int {
        return distincts.map { el -> all.count { it == el } }.max()
    }

    override fun compareTo(other: Hand): Int {
        val c1 = this.getCombination()
        val c2 = other.getCombination()

        if (c1 == c2) {
            for ((card1, card2) in this.cards.zip(other.cards)) {
                if (card1 == card2) {
                    continue
                }
                return card1.compareTo(card2)
            }
            return 0
        }
        return c1.compareTo(c2)
    }

    override fun toString(): String {
        return "{ cards: ${cards}, bid: ${bid}}"
    }
}