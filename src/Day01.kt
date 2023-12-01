fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        for (line in input) {
            val onlyNumericRegex = Regex("[^0-9]")
            val strippedLine = onlyNumericRegex.replace(line, "")
            val firstCharacter = strippedLine[0]
            val lastCharacter = strippedLine[strippedLine.length-1]
            val lineValue = "$firstCharacter$lastCharacter"
            total += lineValue.toInt()
        }
        return total
    }

    fun part2(input: List<String>): Int {
        val wordsToNumbers = mapOf(
                "one" to 1,
                "1" to 1,
                "two" to 2,
                "2" to 2,
                "three" to 3,
                "3" to 3,
                "four" to 4,
                "4" to 4,
                "five" to 5,
                "5" to 5,
                "six" to 6,
                "6" to 6,
                "seven" to 7,
                "7" to 7,
                "eight" to 8,
                "8" to 8,
                "nine" to 9,
                "9" to 9,
        )
        var total = 0
        for (line in input) {
            var firstNumber = 0
            var firstNumberIndex = Int.MAX_VALUE
            var lastNumber = 0
            var lastNumberIndex = -1
            for (possibleNumbers in wordsToNumbers.entries) {
                var currentIndex = -1
                do {
                    currentIndex = line.indexOf(possibleNumbers.key, currentIndex+1)
                    if (currentIndex != -1) {
                        if (currentIndex<firstNumberIndex) {
                            firstNumberIndex = currentIndex
                            firstNumber = possibleNumbers.value
                        }
                        if (currentIndex>lastNumberIndex) {
                            lastNumberIndex = currentIndex
                            lastNumber = possibleNumbers.value
                        }
                    }
                } while (currentIndex != -1)
            }
            total += "$firstNumber$lastNumber".toInt()
        }
        return total
    }

    val testInput1 = readInput("Day01_test_part1")
    check(part1(testInput1) == 142)
    val testInput2 = readInput("Day01_test_part2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
