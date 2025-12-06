package aoc2025

fun main(args: Array<String>) {
    val dayNumber = args.firstOrNull()?.toIntOrNull()

    when (dayNumber) {
        1 -> Day01.solve()
        2 -> Day02.solve()
        else -> throw IllegalArgumentException("No solution for day number $dayNumber")
    }
}
