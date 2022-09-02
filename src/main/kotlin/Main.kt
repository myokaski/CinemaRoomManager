package cinema

import java.lang.Exception

const val SMALL_ROOM = 60
const val HIGH_PRICE = 10
const val LOW_PRICE = 8

fun showSeats(seats: MutableList<MutableList<Char>>) {
    val rows = seats.size
    val seatsInRow = seats[0].size
    print("\nCinema:\n  ")
    repeat(seatsInRow) {
        print("${it + 1} ")
    }
    println("")
    repeat(rows) {
        println("${it + 1} " + seats[it].joinToString(" "))
    }
}

fun getPrice(row: Int, seats: MutableList<MutableList<Char>>): Int {
    val rows = seats.size
    val seatsInRow = seats[0].size
    if (row < 1 || row > rows) throw Exception("Incorrect row")
    val totalSeats = rows * seatsInRow
    val expensiveRows = if (totalSeats <= SMALL_ROOM) rows else rows / 2
    //val cheapRows = rows - expensiveRows
    //val income = (cheapRows * LOW_PRICE + expensiveRows * HIGH_PRICE) * seatsInRow
    //println("Total income:\n\$$income")
    return if (row <= expensiveRows) HIGH_PRICE else LOW_PRICE
}

fun showStatistics(seats: MutableList<MutableList<Char>>) {
    val rows = seats.size
    val seatsInRow = seats[0].size
    val totalSeats = rows * seatsInRow
    val purchased = seats.sumOf { row -> row.count { seat -> seat == 'B' }}
    println("Number of purchased tickets: $purchased")
    val percentage = purchased * 100.0 / totalSeats
    println("Percentage: %.2f%%".format(percentage))
    var currentIncome = 0
    var totalIncome = 0
    repeat(rows) {
        val currentRow = it + 1
        currentIncome += getPrice(currentRow, seats) * seats[it].count { seat -> seat == 'B' }
        totalIncome += getPrice(currentRow, seats) * seats[it].count()
    }
    println("Current income: \$$currentIncome")
    println("Total income: \$$totalIncome")
}

fun buyTicket(seats: MutableList<MutableList<Char>>) {
    val rows = seats.size
    val seatsInRow = seats[0].size
    var row: Int
    var seat: Int
    var inputOk = false
    do {
        println("\nEnter a row number:")
        row = readln().toInt()

        println("Enter a seat number in that row:")
        seat = readln().toInt()
        println("")
        if (row < 1 || row > rows || seat < 1 || seat > seatsInRow) {
            println("Wrong input!")
        } else if (seats[row - 1][seat - 1] == 'B') {
            println("That ticket has already been purchased!")
        } else inputOk = true
    } while (!inputOk)

    //val totalSeats = rows * seatsInRow
    //val expensiveRows = if (totalSeats <= SMALL_ROOM) rows else rows / 2
    //val cheapRows = rows - expensiveRows
    //val income = (cheapRows * LOW_PRICE + expensiveRows * HIGH_PRICE) * seatsInRow
    //println("Total income:\n\$$income")
    val price = getPrice(row, seats)
    println("Ticket price: \$$price")

    seats[row - 1][seat - 1] = 'B'
}

fun main() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsInRow = readln().toInt()
    val seats = MutableList(rows) { MutableList(seatsInRow) { 'S' } }

    var exit = false
    do {
        println("")
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        when (readln()) {
            "1" -> showSeats(seats)
            "2" -> buyTicket(seats)
            "3" -> showStatistics(seats)
            "0" -> exit = true
            else -> println("Wrong input")
        }
    } while (!exit)
}