package y2023

import y2023.day1.dayOne
import y2023.day2.dayTwo
import y2023.day3.dayThree
import y2023.day4.dayFour
import y2023.day5.dayFive
import y2023.day6.daySix
import y2023.day7.daySeven
import java.time.Duration
import java.time.Instant


fun main() {
    println("*".repeat(20))
    println("ADVENT OF CODE 2023")
    println("Author: Mathias Are")
    println("Language: Kotlin")
    println("*".repeat(20))

    runAllDaysWithTime(
        { dayOne() },
        { dayTwo() },
        { dayThree() },
        { dayFour() },
        { dayFive() },
        { daySix() },
        { daySeven() })
}

fun runAllDaysWithTime(vararg fns: () -> Unit) {
    for ((day, fn) in fns.withIndex()) {
        println("Day ${day + 1}")

        val start = Instant.now()
        fn.invoke()
        val end = Instant.now()

        val mills = Duration.between(start, end).toMillis()
        println("Executed in: ${mills}ms")
        println("*".repeat(20))
    }
}