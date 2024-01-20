package y2023

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import util.readFileLines
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("Year 2023")
class Y2023Test {

    @Nested
    @DisplayName("Day 1")
    inner class Day1Test {

        private lateinit var lines: List<String>

        @BeforeTest
        fun setup() {
            lines = readFileLines("d1test", true)
        }

        @Test
        fun testPart1() {
            assertEquals(231, y2023.day1.solvePartOne(lines))
        }

        @Test
        fun testPart2() {
            assertEquals(281, y2023.day1.solvePartTwo(lines))
        }
    }

    @Nested
    @DisplayName("Day 2")
    inner class Day2Test {

        private lateinit var lines: List<String>

        @BeforeTest
        fun setup() {
            lines = readFileLines("d2test", true)
        }

        @Test
        fun testPart1() {
            assertEquals(8, y2023.day2.solvePartOne(lines))
        }

        @Test
        fun testPart2() {
            assertEquals(2286, y2023.day2.solvePartTwo(lines))
        }
    }

    @Nested
    @DisplayName("Day 3")
    inner class Day3Test {

        private lateinit var lines: List<String>

        @BeforeTest
        fun setup() {
            lines = readFileLines("d3test", true)
        }

        @Test
        fun testPart1() {
            assertEquals(4361, y2023.day3.solvePartOne(lines))
        }

        @Test
        fun testPart2() {
            assertEquals(467835, y2023.day3.solvePartTwo(lines))
        }
    }

    @Nested
    @DisplayName("Day 4")
    inner class Day4Test {

        private lateinit var lines: List<String>

        @BeforeTest
        fun setup() {
            lines = readFileLines("d4test", true)
        }

        @Test
        fun testPart1() {
            assertEquals(13, y2023.day4.solvePartOne(lines))
        }

        @Test
        fun testPart2() {
            assertEquals(30, y2023.day4.solvePartTwo(lines))
        }
    }

    @Nested
    @DisplayName("Day 5")
    inner class Day5Test {

        private lateinit var lines: List<String>

        @BeforeTest
        fun setup() {
            lines = readFileLines("d5test", true)
        }

        @Test
        fun testPart1() {
            assertEquals(35L, y2023.day5.solvePartOne(lines))
        }

        @Test
        fun testPart2() {
            assertEquals(46L, y2023.day5.solvePartTwo(lines))
        }
    }

    @Nested
    @DisplayName("Day 6")
    inner class Day6Test {

        private lateinit var lines: List<String>

        @BeforeTest
        fun setup() {
            lines = readFileLines("d6test", true)
        }

        @Test
        fun testPart1() {
            assertEquals(288L, y2023.day6.solvePartOne(lines))
        }

        @Test
        fun testPart2() {
            assertEquals(71503L, y2023.day6.solvePartTwo(lines))
        }
    }

    @Nested
    @DisplayName("Day 7")
    inner class Day7Test {

        private lateinit var lines: List<String>

        @BeforeTest
        fun setup() {
            lines = readFileLines("d7test", true)
        }

        @Test
        fun testPart1() {
            assertEquals(6440L, y2023.day7.solvePartOne(lines))
        }

        @Test
        fun testPart2() {
            assertEquals(5905L, y2023.day7.solvePartTwo(lines))
        }
    }

    @Nested
    @DisplayName("Day 8")
    inner class Day8Test {

        private lateinit var lines: List<String>

        @BeforeTest
        fun setup() {
            lines = readFileLines("d8test", true)
        }

        @Test
        fun testPart1() {
            assertEquals(6, y2023.day8.solvePartOne(lines))
        }

        @Test
        fun testPart2() {
            assertEquals(6L, y2023.day8.solvePartTwo(lines))
        }
    }

    @Nested
    @DisplayName("Day 9")
    inner class Day9Test {

        private lateinit var lines: List<String>

        @BeforeTest
        fun setup() {
            lines = readFileLines("d9test", true)
        }

        @Test
        fun testPart1() {
            assertEquals(114L, y2023.day9.solvePartOne(lines))
        }

        @Test
        fun testPart2() {
            assertEquals(2L, y2023.day9.solvePartTwo(lines))
        }
    }

    @Nested
    @DisplayName("Day 10")
    inner class Day10Test {

        private lateinit var linesP1: List<String>
        private lateinit var linesP2: List<String>

        @BeforeTest
        fun setup() {
            linesP1 = readFileLines("d10p1test", true)
            linesP2 = readFileLines("d10p2test", true)
        }

        @Test
        fun testPart1() {
            assertEquals(8, y2023.day10.solvePartOne(linesP1))
        }

        @Test
        fun testPart2() {
            assertEquals(10, y2023.day10.solvePartTwo(linesP2))
        }
    }
}