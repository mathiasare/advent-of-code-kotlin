package y2023.day1

import util.readFileLines

fun main() {
    dayOne()
}

fun dayOne() {
    val lines = readFileLines("inputD1")
    println("Part 1: " + calibrateV1(lines))
    println("Part 2: " + calibrateV2(lines))
}

val numTable: Map<String, String> = hashMapOf(
    Pair("one", "1"),
    Pair("two", "2"),
    Pair("three", "3"),
    Pair("four", "4"),
    Pair("five", "5"),
    Pair("six", "6"),
    Pair("seven", "7"),
    Pair("eight", "8"),
    Pair("nine", "9")
)

fun calibrateV1(lines: List<String>): Int {
    return lines.sumOf { it ->
        val first = it.first { it.isDigit() }
        val last = it.last { it.isDigit() }
        (first + "" + last).toInt()
    }
}

fun calibrateV2(lines: List<String>): Int {
    return lines.sumOf { it ->
        var buff = ""
        var digits = ""
        for (c in it) {
            getNumMatch(buff)?.let {
                digits += numTable[it]
                buff = ""
            }

            if (c.isDigit()) {
                digits += c
                buff = ""
            } else {
                buff += c
            }
        }

        getNumMatch(buff)?.let {
            digits += numTable[it]
            buff = ""
        }

        (digits.first() + "" + digits.last()).toInt()
    }
}

fun getNumMatch(buff: String): String? {
    for (key in numTable.keys) {
        if (key in buff) {
            return key
        }
    }
    return null
}