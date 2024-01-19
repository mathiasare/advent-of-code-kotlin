package y2023.day9

import util.readFileLines

fun main() {
    day9()
}

fun day9() {
    val lines = readFileLines("inputD9")
    println("Part 1: " + solvePartOne(lines))
    println("Part 2: " + solvePartTwo(lines))
}

fun solvePartOne(lines: List<String>): Long {
    return lines
        .map(::readSequence)
        .map(::findNextValueInSequence)
        .sum()
}

fun solvePartTwo(lines: List<String>): Long {
    return lines
        .map(::readSequence)
        .map(::findPrevValueInSequence)
        .sum()
}

fun findNextValueInSequence(seq: List<Long>): Long {
    val lastValues = mutableListOf(seq.last())
    var curSeq = seq
    while (curSeq.any { it != 0L }) {
        curSeq = derivedSequence(curSeq)
        lastValues.add(curSeq.last())
    }
    return lastValues.sum()
}

fun findPrevValueInSequence(seq: List<Long>): Long {
    val firstValues = mutableListOf(seq.first())
    var curSeq = seq
    while (curSeq.any { it != 0L }) {
        curSeq = derivedSequence(curSeq)
        firstValues.add(curSeq.first())
    }

    return firstValues.foldRight(0) { l, acc ->
        l - acc
    }
}

fun derivedSequence(seq: List<Long>): List<Long> {
    val derived = mutableListOf<Long>()
    for (i in 0..<seq.size - 1) {
        derived.add(seq[i + 1] - seq[i])
    }
    return derived
}

fun readSequence(line: String) = line.split(" ").map { it.toLong() }