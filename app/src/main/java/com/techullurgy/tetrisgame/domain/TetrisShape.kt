package com.techullurgy.tetrisgame.domain

import androidx.compose.runtime.MutableState

abstract class TetrisShape {
    abstract val coordinates: Array<Array<Cell>>

    fun canGoRightFrom(point: Point, board: List<List<MutableState<Cell>>>): Boolean {
        if((point.col + coordinates[0].size - 1) < board[0].size) {
            if((point.row + coordinates.size - 1) < board.size) {
                for (i in coordinates.indices) {
                    for (j in coordinates[0].indices) {
                        val rightIndex = point.col + (j+1)
                        if(rightIndex >= board[0].size || board[point.row + i][rightIndex].value == Cell.Filled) {
                            return false
                        }
                    }
                }
                return true
            }
        }
        return false
    }

    fun canGoLeftFrom(point: Point, board: List<List<MutableState<Cell>>>): Boolean {
        if((point.col + coordinates[0].size - 1) < board[0].size) {
            if((point.row + coordinates.size - 1) < board.size) {
                for (i in coordinates.indices) {
                    for (j in coordinates[0].indices) {
                        val leftIndex = point.col + (j-1)
                        if(leftIndex < 0 || board[point.row + i][leftIndex].value == Cell.Filled) {
                            return false
                        }
                    }
                }
                return true
            }
        }
        return false
    }

    fun placeShapeAt(point: Point, board: List<List<MutableState<Cell>>>) {
        if((point.col + coordinates[0].size - 1) < board[0].size) {
            if((point.row + coordinates.size - 1) < board.size) {
                for (i in coordinates.indices) {
                    for (j in coordinates[0].indices) {
                        if(board[point.row + i][point.col + j].value != Cell.Filled) {
                            board[point.row + i][point.col + j].value = coordinates[i][j]
                        }
                    }
                }
            }
        }
    }

    fun collidesAt(point: Point, board: List<List<MutableState<Cell>>>): Boolean {
        if(point.row + coordinates.size == board.size) return true

        for (i in coordinates.indices) {
            for (j in coordinates[0].indices) {
                if(coordinates[i][j] != Cell.Empty) {
                    if (board[point.row + i + 1][point.col + j].value == Cell.Filled) {
                        return true
                    }
                }
            }
        }

        return false
    }

    fun getDownPointFrom(point: Point, board: List<List<MutableState<Cell>>>): Point {
        var temp = point
        while (!collidesAt(temp, board)) {
            temp = temp.copy(row = temp.row + 1)
        }
        return temp
    }
}

class OShape: TetrisShape() {
    override val coordinates: Array<Array<Cell>>
        get() = arrayOf(
            arrayOf(Cell.Block, Cell.Block),
            arrayOf(Cell.Block, Cell.Block),
        )
}

class TShape: TetrisShape() {
    override val coordinates: Array<Array<Cell>>
        get() = arrayOf(
            arrayOf(Cell.Block, Cell.Block, Cell.Block),
            arrayOf(Cell.Empty, Cell.Block, Cell.Empty)
        )
}

class LShape: TetrisShape() {
    override val coordinates: Array<Array<Cell>>
        get() = arrayOf(
            arrayOf(Cell.Block, Cell.Empty),
            arrayOf(Cell.Block, Cell.Empty),
            arrayOf(Cell.Block, Cell.Block),
        )
}

class IShape: TetrisShape() {
    override val coordinates: Array<Array<Cell>>
        get() = arrayOf(
            arrayOf(Cell.Block),
            arrayOf(Cell.Block),
            arrayOf(Cell.Block),
            arrayOf(Cell.Block),
        )
}