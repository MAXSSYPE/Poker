package my.project.poker

fun calculatePoints(bribes: Int, realBribes: Int): Int {
    val difference = realBribes - bribes
    when {
        difference == 0 && realBribes == 0 -> return 5
        difference == 0 -> return 10 * realBribes
        difference > 0 -> return realBribes
        difference < 0 -> return 10 * difference
    }
    return 0
}