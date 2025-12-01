package aoc2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {
    private val input = """
        L68
        L30
        R48
        L5
        R60
        L55
        L1
        L99
        R14
        L82
    """.trimIndent().lines()

    private val data = Day01.parse(input)

    @Test
    fun `part 1`() {
        assertEquals(3, Day01.solveFirst(data))
    }

    @Test
    fun `part 2`() {
        assertEquals(6, Day01.solveSecond(data))
    }
}
