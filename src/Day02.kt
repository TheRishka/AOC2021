fun main() {
    fun part1(input: List<String>): Int {
        var resultDepth = 0
        var resultPosition = 0
        input.forEach {
            val commandWithValue = it.getCommandWithValue()
            when (commandWithValue.first) {
                Command.FORWARD -> {
                    resultPosition += commandWithValue.second
                }
                Command.DOWN -> {
                    resultDepth += commandWithValue.second
                }
                Command.UP -> {
                    resultDepth -= commandWithValue.second
                }
            }
        }
        return resultDepth * resultPosition
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day02_test")
//    check(part1(testInput) == 8)
//    check(part2(testInput) == 8)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

enum class Command {
    FORWARD,
    DOWN,
    UP;
}

fun String.getCommandWithValue() = when {
    contains("forward") -> Command.FORWARD to takeLast(2).trim().toInt()
    contains("down") -> Command.DOWN to takeLast(2).trim().toInt()
    contains("up") -> Command.UP to takeLast(2).trim().toInt()
    else -> error("Unknown command!")
}
