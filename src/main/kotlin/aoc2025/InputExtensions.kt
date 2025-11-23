package aoc2025

fun String.readPuzzle(): List<String> {
    val resource = {}.javaClass.getResource(this) ?: error("File '$this' not found")
    return resource.readText().trim().lines()
}
