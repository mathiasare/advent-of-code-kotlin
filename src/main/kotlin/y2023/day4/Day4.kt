package y2023.day4

import util.readFileLines
import java.util.*

fun main() {
    dayFour()
}

fun dayFour() {
    val lines = readFileLines("inputD4")
    println("Part 1: " + solvePartOne(lines))
    println("Part 2: " + solvePartTwo(lines))
}

data class LottoCard(val cardNum: Int, val givenNumbers: List<String>, val winningNumbers: List<String>)

fun solvePartOne(lines: List<String>): Int {
    return lines.map(::readLottoCard).map(::getLottoCardValue).sum()
}

fun solvePartTwo(lines: List<String>): Int {
    return countLottoCardsP2(lines)
}

fun readLottoCard(line: String): LottoCard {
    return line.split(":").run {
        val cardNum = first().filter { it.isDigit() }.toInt()
        last()
            .split(" | ")
            .run {
                LottoCard(
                    cardNum,
                    first()
                        .split(" ")
                        .filter { it.isNotBlank() },
                    last()
                        .split(" ")
                        .filter { it.isNotBlank() })
            }
    }
}


fun getLottoCardValue(lottoCard: LottoCard): Int {
    var score = 0
    val winningSet = lottoCard.winningNumbers.toSet()

    for (givenNumber in lottoCard.givenNumbers) {
        if (winningSet.contains(givenNumber)) {
            score = incrementScore(score)
        }
    }
    return score
}

fun countLottoCardsP2(lines: List<String>): Int {
    var winsCount = lines.size
    val lottoWinsMap = mutableMapOf<Int, Int>()
    val queue: Queue<Int> = LinkedList()

    for (line in lines) {
        val card = readLottoCard(line)
        val wins = getLottoCardWinCount(card)
        winsCount += wins
        lottoWinsMap[card.cardNum] = wins
        for (i in 1..wins) {
            queue.add(card.cardNum + i)
        }
    }

    while (queue.isNotEmpty()) {
        val el = queue.poll()
        lottoWinsMap[el]?.let {
            winsCount += it
            for (i in 1..it) {
                queue.add(el + i)
            }
        }
    }

    return winsCount
}

fun getLottoCardWinCount(lottoCard: LottoCard): Int {
    val winningSet = lottoCard.winningNumbers.toSet()
    val givenSet = lottoCard.givenNumbers.toSet()
    return givenSet.intersect(winningSet).size
}

fun incrementScore(score: Int) = if (score == 0) 1 else score * 2