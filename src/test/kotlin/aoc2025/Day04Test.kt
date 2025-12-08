package aoc2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Test {
    private val input = """
        ..@@.@@@@.
        @@@.@.@.@@
        @@@@@.@.@@
        @.@@@@..@.
        @@.@@@@.@@
        .@@@@@@@.@
        .@.@.@.@@@
        @.@@@.@@@@
        .@@@@@@@@.
        @.@.@@@.@.
    """.trimIndent().lines()

    private val data = Day04.parse(input)

    @Test
    fun `part 1`() {
        assertEquals(13, Day04.solveFirst(data))
    }

    @Test
    fun `part 2`() {
        assertEquals(43, Day04.solveSecond(data))
    }
}
