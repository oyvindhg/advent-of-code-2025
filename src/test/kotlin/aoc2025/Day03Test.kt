package aoc2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {
    private val input = """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
    """.trimIndent().lines()

    private val data = Day03.parse(input)

    @Test
    fun `part 1`() {
        assertEquals(357, Day03.solveFirst(data))
    }

    @Test
    fun `part 2`() {
        assertEquals(3121910778619, Day03.solveSecond(data))
    }
}
