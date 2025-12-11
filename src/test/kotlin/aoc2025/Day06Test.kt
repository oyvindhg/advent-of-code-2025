package aoc2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {
    private val input = """
        123 328  51 64 
         45 64  387 23 
          6 98  215 314
        *   +   *   +  
    """.trimIndent().lines()

    private val data = Day06.parse(input)

    @Test
    fun `part 1`() {
        assertEquals(4_277_556, Day06.solveFirst(data))
    }

    @Test
    fun `part 2`() {
        assertEquals(3_263_827, Day06.solveSecond(data))
    }
}
