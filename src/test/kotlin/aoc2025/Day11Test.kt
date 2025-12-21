package aoc2025

import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {
    private val firstInput = """
        aaa: you hhh
        you: bbb ccc
        bbb: ddd eee
        ccc: ddd eee fff
        ddd: ggg
        eee: out
        fff: out
        ggg: out
        hhh: ccc fff iii
        iii: out
    """.trimIndent().lines()

    private val secondInput = """
        svr: aaa bbb
        aaa: fft
        fft: ccc
        bbb: tty
        tty: ccc
        ccc: ddd eee
        ddd: hub
        hub: fff
        eee: dac
        dac: fff
        fff: ggg hhh
        ggg: out
        hhh: out
    """.trimIndent().lines()

    private val firstData = Day11.parse(firstInput)
    private val secondData = Day11.parse(secondInput)

    @Test
    fun `part 1`() {
        assertEquals(5, Day11.solveFirst(firstData))
    }

    @Test
    fun `part 2`() {
        assertEquals(2, Day11.solveSecond(secondData))
    }
}
