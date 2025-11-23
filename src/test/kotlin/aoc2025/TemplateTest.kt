package aoc2025

import kotlin.test.Test
import kotlin.test.assertEquals

class TemplateTest {
    private val input = """
        test input line 1
        test input line 2
        test input line 3
    """.trimIndent().lines()

    private val data = Template.parse(input)

    @Test
    fun `part 1`() {
        assertEquals(0, Template.solveFirst(data))
    }

    @Test
    fun `part 2`() {
        assertEquals(0, Template.solveSecond(data))
    }
}
