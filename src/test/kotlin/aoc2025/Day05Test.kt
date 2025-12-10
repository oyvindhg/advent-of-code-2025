package aoc2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day05Test {
    private val input = """
        3-5
        10-14
        16-20
        12-18
        
        1
        5
        8
        11
        17
        32
    """.trimIndent().lines()

    private val data = Day05.parse(input)

    @Test
    fun `part 1`() {
        assertEquals(3, Day05.solveFirst(data))
    }

    @Test
    fun `part 2`() {
        assertEquals(14, Day05.solveSecond(data))
    }
}
