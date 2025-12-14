package aoc2025

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.time.measureTimedValue


object Day09 {
    private const val INPUT_FILENAME = "day09.txt"

    fun solve() {
        println("===Day 09===\n")
        val input = INPUT_FILENAME.readPuzzle()
        val tiles = parse(input)

        println("---Task 1---")
        val (firstSolution, firstTimeTaken) = measureTimedValue { solveFirst(tiles) }
        println("Solution: $firstSolution")
        println("Time: ${firstTimeTaken.inWholeMilliseconds} ms\n")

        println("---Task 2---")
        val (secondSolution, secondTimeTaken) = measureTimedValue { solveSecond(tiles) }
        println("Solution: $secondSolution")
        println("Time: ${secondTimeTaken.inWholeMilliseconds} ms")
    }

    internal fun parse(input: List<String>): List<Point> {
        return input.map { line ->
            val (x, y) = line.split(',').map { it.toInt() }
            Point(x, y)
        }
    }

    internal fun solveFirst(tiles: List<Point>): Long {
        val areas = tiles.flatMapIndexed { firstTileNumber, firstTile ->
            (firstTileNumber + 1 until tiles.size).map { secondTileNumber ->
                val secondTile = tiles[secondTileNumber]
                Square(
                    min(firstTile.x, secondTile.x),
                    max(firstTile.x, secondTile.x),
                    min(firstTile.y, secondTile.y),
                    max(firstTile.y, secondTile.y)
                ).findArea()
            }
        }
        return areas.max()
    }

    internal fun solveSecond(tiles: List<Point>): Long {
        var maxArea = 0L
        tiles.forEachIndexed { firstTileNumber, firstTile ->
            (firstTileNumber + 1 until tiles.size).forEach { secondTileNumber ->
                val secondTile = tiles[secondTileNumber]

                val square = Square(
                    min(firstTile.x, secondTile.x),
                    max(firstTile.x, secondTile.x),
                    min(firstTile.y, secondTile.y),
                    max(firstTile.y, secondTile.y)
                )
                val area = square.findArea()

                if (area > maxArea && checkTilesSurroundingSquare(tiles, square)) {
                    maxArea = max(area, maxArea)
                }
            }
        }
        return maxArea
    }

    private fun checkTilesSurroundingSquare(
        tiles: List<Point>,
        square: Square
    ): Boolean {
        var leftBelowCrossings = 0
        var leftAboveCrossings = 0
        var upperLeftCrossings = 0
        var upperRightCrossings = 0
        var rightBelowCrossings = 0
        var rightAboveCrossings = 0
        var lowerLeftCrossings = 0
        var lowerRightCrossings = 0

        tiles.forEachIndexed { toTileNumber, toTile ->
            val fromTile = if (toTileNumber == 0) {
                tiles.last()
            } else {
                tiles[toTileNumber - 1]
            }

            val xLineMin = min(fromTile.x, toTile.x)
            val xLineMax = max(fromTile.x, toTile.x)
            val yLineMin = min(fromTile.y, toTile.y)
            val yLineMax = max(fromTile.y, toTile.y)

            assert(xLineMin == xLineMax || yLineMin == yLineMax) { "Connecting tiles not on same row or column" }

            if (xLineMin == square.xMin &&
                xLineMax == square.xMax &&
                yLineMin == square.yMin &&
                yLineMax == square.yMax
            ) {
                return true
            }

            if (xLineMin <= square.xMin && xLineMax > square.xMin) {
                if (yLineMin >= square.yMax) {
                    leftAboveCrossings += 1
                }
                if (yLineMin <= square.yMin) {
                    leftBelowCrossings += 1
                }
                if (yLineMin in (square.yMin + 1)..<square.yMax) {
                    return false
                }
            }
            if (yLineMin < square.yMax && yLineMax >= square.yMax) {
                if (xLineMin >= square.xMax) {
                    upperRightCrossings += 1
                }
                if (xLineMin <= square.xMin) {
                    upperLeftCrossings += 1
                }
                if (xLineMin in (square.xMin + 1)..<square.xMax) {
                    return false
                }
            }
            if (xLineMin < square.xMax && xLineMax >= square.xMax) {
                if (yLineMin >= square.yMax) {
                    rightAboveCrossings += 1
                }
                if (yLineMin <= square.yMin) {
                    rightBelowCrossings += 1
                }
                if (yLineMin in (square.yMin + 1)..<square.yMax) {
                    return false
                }
            }
            if (yLineMin <= square.yMin && yLineMax > square.yMin) {
                if (xLineMin <= square.xMin) {
                    lowerLeftCrossings += 1
                }
                if (xLineMin >= square.xMax) {
                    lowerRightCrossings += 1
                }
                if (xLineMin in (square.xMin + 1)..<square.xMax) {
                    return false
                }
            }
        }

        if (leftBelowCrossings % 2 != 1
            || leftAboveCrossings % 2 != 1
            || upperLeftCrossings % 2 != 1
            || upperRightCrossings % 2 != 1
            || rightBelowCrossings % 2 != 1
            || rightAboveCrossings % 2 != 1
            || lowerLeftCrossings % 2 != 1
            || lowerRightCrossings % 2 != 1
        ) {
            return false
        }
        return true
    }

    internal data class Point(val x: Int, val y: Int)

    private data class Square(val xMin: Int, val xMax: Int, val yMin: Int, val yMax: Int)

    private fun Square.findArea(): Long {
        return abs(xMax - xMin + 1).toLong() * abs(yMax - yMin + 1).toLong()
    }
}
