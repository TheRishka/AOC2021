fun main() {
    fun part1(input: List<String>): Int {
        val resultList = input.mapIndexed { index, value ->
            when {
                index > 0 -> {
                    value.toInt() > (input[index - 1]).toInt()
                }
                else -> false
            }
        }
        return resultList.filter { it }.size
    }

    fun part2(input: List<String>): Int {
        val windowedList = input.windowed(3, step = 1).map {
            it.sumOf { stringValue ->
                stringValue.toInt()
            }
        }
        val resultList = windowedList.mapIndexed { index, value ->
            when {
                index > 0 -> {
                    value > (windowedList[index - 1])
                }
                else -> false
            }
        }
        return resultList.filter { it }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 8)
//    check(part2(testInput) == 8)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
