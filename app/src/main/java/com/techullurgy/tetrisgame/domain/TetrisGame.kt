package com.techullurgy.tetrisgame.domain

import androidx.compose.runtime.mutableStateOf
import com.techullurgy.tetrisgame.ui.components.NUM_COLS
import com.techullurgy.tetrisgame.ui.components.NUM_ROWS
import kotlin.random.Random

val shapes = listOf(
    OShape(), TShape(), LShape(), IShape()
)

class TetrisGame {
    private var currentShape: TetrisShape = shapes.random()
    private var currentPoint: Point = Point(0, NUM_COLS/2)

    val board = List(NUM_ROWS) {
        List(NUM_COLS) {
            mutableStateOf(Cell.Empty)
        }
    }

    fun update() {
        if(currentShape.collidesAt(currentPoint, board)) {
            fixToWell()
            nextPiece()
        }

        currentPoint = Point(currentPoint.row+1, currentPoint.col)
        place()
    }

    fun goLeft() {
        if(currentShape.canGoLeftFrom(currentPoint, board)) {
            currentPoint = currentPoint.copy(col = currentPoint.col - 1)
            place()
        }
    }

    fun goRight() {
        if(currentShape.canGoRightFrom(currentPoint, board)) {
            currentPoint = currentPoint.copy(col = currentPoint.col + 1)
            place()
        }
    }

    fun goDown() {
        currentPoint = currentShape.getDownPointFrom(currentPoint, board)
        place()
    }

    private fun place() {
        reset()
        currentShape.placeShapeAt(currentPoint, board)
    }

    private fun reset() {
        for (i in board.indices) {
            for (j in board[0].indices) {
                if(board[i][j].value == Cell.Block) {
                    board[i][j].value = Cell.Empty
                }
            }
        }
    }

    private fun nextPiece() {
        currentPoint = Point(0, Random.nextInt(board[0].size - 10))
        currentShape = shapes.random()
    }

    private fun fixToWell() {
        for (i in board.indices) {
            for (j in board[0].indices) {
                if(board[i][j].value == Cell.Block) {
                    board[i][j].value = Cell.Filled
                }
            }
        }

        removeRowsIfNeeded()
    }

    private fun removeRowsIfNeeded() {
        if(!canRemoveRows()) return

        val removableRows = getRemovableRows()

        removableRows.forEach {
            for(j in board[0].indices) {
                board[it][j].value = Cell.Done
            }
        }

        adjustRows()

        removeRowsIfNeeded()
    }

    private fun adjustRows() {
        for(ii in board.indices.reversed()) {
            if(board[ii].first().value == Cell.Done) {
                for(i in ii downTo 1) {
                    for(j in board[0].indices) {
                        board[i][j].value = board[i-1][j].value
                    }
                }
                adjustRows()
                break
            }
        }
    }

    private fun getRemovableRows(): List<Int> {
        val rows = mutableListOf<Int>()
        for (i in board.indices.reversed()) {
            var can = true
            for (j in board[0].indices) {
                if(board[i][j].value != Cell.Filled) {
                    can = false
                }
            }
            if(can) {
                rows.add(i)
            }
        }
        return rows
    }

    private fun canRemoveRows(): Boolean {
        for (i in board.indices.reversed()) {
            var can = true
            for (j in board[0].indices) {
                if(board[i][j].value != Cell.Filled) {
                    can = false
                }
            }
            if(can) return true
        }
        return false
    }
}