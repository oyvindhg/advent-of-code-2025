package aoc2025

fun main(args: Array<String>) {
    val dayNumber = args.firstOrNull()?.toIntOrNull()

    when (dayNumber) {
        1 -> Day01.solve()
        2 -> Day02.solve()
        3 -> Day03.solve()
        4 -> Day04.solve()
        5 -> Day05.solve()
        else -> throw IllegalArgumentException("No solution for day number $dayNumber")
    }
}
