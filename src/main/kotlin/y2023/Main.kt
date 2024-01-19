package y2023

import y2023.day1.day1
import y2023.day2.day2
import y2023.day3.day3
import y2023.day4.day4
import y2023.day5.day5
import y2023.day6.day6
import y2023.day7.day7
import y2023.day8.day8
import y2023.day9.day9
import java.time.Duration
import java.time.Instant


fun main() {
    println("*".repeat(20))
    println("ADVENT OF CODE 2023")
    println("Author: Mathias Are")
    println("Language: Kotlin")
    println("*".repeat(20))

    runAllDaysWithTime(
        { day1() },
        { day2() },
        { day3() },
        { day4() },
        { day5() },
        { day6() },
        { day7() },
        { day8() },
        { day9() })
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