package aoc2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test {
    private val input = """
        7,1
        11,1
        11,7
        9,7
        9,5
        2,5
        2,3
        7,3
    """.trimIndent().lines()

    private val data = Day09.parse(input)

    @Test
    fun `part 1`() {
        assertEquals(50, Day09.solveFirst(data))
    }

    @Test
    fun `part 2`() {
        assertEquals(24, Day09.solveSecond(data))
    }
}
