# Advent of Code 2025
Kotlin solutions to the [2025 Advent of Code](https://adventofcode.com/2025) problems.

## Running
Copy the puzzle input text into a file `src/main/resources/day{DD}.txt` where `{DD}` is the day number.

The solution can be run as follows:

```
./gradlew run --args={DD}
``` 
where `{DD}` is the day number.

For example, `./gradlew run --args=01` runs the solution for the first day.

## Testing
Run the solutions on test data:

```
/gradlew test --tests "*Day{DD}Test"
```
where `{DD}` is the day number.

For example, `./gradlew test --tests "*Day01Test"` runs the tests for the first day.

## Previous years

[Advent of Code 2024 (Go)](https://github.com/oyvindhg/advent-of-code-2024)

[Advent of Code 2022 (Rust)](https://github.com/oyvindhg/advent-of-code-2022)

[Advent of Code 2021 (Python)](https://github.com/oyvindhg/advent-of-code-2021)
