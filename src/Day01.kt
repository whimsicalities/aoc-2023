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
        class WordAndNumber(word: String, number: Int) {
            val word = word
            val number = number
        }

        val wordsAndNumbers = listOf(
                WordAndNumber("one", 1),
                WordAndNumber("1", 1),
                WordAndNumber("two", 2),
                WordAndNumber("2", 2),
                WordAndNumber("three", 3),
                WordAndNumber("3", 3),
                WordAndNumber("four", 4),
                WordAndNumber("4", 4),
                WordAndNumber("five", 5),
                WordAndNumber("5", 5),
                WordAndNumber("six", 6),
                WordAndNumber("6", 6),
                WordAndNumber("seven", 7),
                WordAndNumber("7", 7),
                WordAndNumber("eight", 8),
                WordAndNumber("8", 8),
                WordAndNumber("nine", 9),
                WordAndNumber("9", 9)
        )
        var total = 0
        for (line in input) {
            var firstNumber = 0
            var firstNumberIndex = Int.MAX_VALUE
            var lastNumber = 0
            var lastNumberIndex = -1
            for (wordAndNumber in wordsAndNumbers) {
                var currentIndex = -1
                do {
                    currentIndex = line.indexOf(wordAndNumber.word, currentIndex+1)
                    if (currentIndex != -1) {
                        if (currentIndex < firstNumberIndex) {
                            firstNumberIndex = currentIndex
                            firstNumber = wordAndNumber.number
                        }
                        if (currentIndex > lastNumberIndex) {
                            lastNumberIndex = currentIndex
                            lastNumber = wordAndNumber.number
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
