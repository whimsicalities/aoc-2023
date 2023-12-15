fun main() {
    fun numbersStringToNumbersList(numberString: String): List<Int> {
        val split = numberString.split(" ").filter { it.isNotEmpty() }
        return split.map { stringNumber: String -> stringNumber.toInt() }
    }

    fun part1(input: List<String>): Int {
        var totalPoints = 0
        for (line in input) {
            val parts = line.split(": ", " | ")
            check(parts.size == 3) // Titles, winning numbers, your numbers
            val winningNumbers = numbersStringToNumbersList(parts[1])
            var points = 0
            var nextWinnings = 1
            val your_numbers = numbersStringToNumbersList(parts[2])
            for (number in your_numbers) {
                if (winningNumbers.contains(number)) {
                    points = nextWinnings
                    nextWinnings *= 2
                }
            }
            totalPoints += points
        }
        return totalPoints
    }

    fun part2(input: List<String>): Int {
        // find wins per card
        val winsOnCards = IntArray(input.size)
        for (i in input.indices) {
            val parts = input[i].split(": ", " | ")
            val winningNumbers = numbersStringToNumbersList(parts[1])
            val your_numbers = numbersStringToNumbersList(parts[2])
            for (number in your_numbers) {
                if (winningNumbers.contains(number)) {
                    winsOnCards[i]++
                }
            }
        }
        // repeat cards
        val cardRepeats = IntArray(input.size) {1}
        for (i in winsOnCards.indices) {
            for (j in 1..winsOnCards[i]) { // for each subsequent card that is added to
                if (i+j < cardRepeats.size) {
                    cardRepeats[i+j] = cardRepeats[i+j] + cardRepeats[i]
                }
            }
        }
        // find total no. of cards
        return cardRepeats.reduce { acc, next -> acc + next }
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
