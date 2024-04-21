package com.techullurgy.tetrisgame.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.techullurgy.tetrisgame.domain.Cell

@Composable
fun TetrisBoard(
    modifier: Modifier = Modifier,
    board: List<List<MutableState<Cell>>>
) {
    val flattenedBoard = board.flatten()

    Layout(
        contents = flattenedBoard.map {
            @Composable {
                Spacer(
                    modifier = Modifier
                        .background(
                            when (it.value) {
                                Cell.Empty -> Color.Black
                                Cell.Block -> Color.Cyan
                                Cell.Filled -> Color.Magenta
                                Cell.Done -> Color.Green
                            }
                        )
                        .run {
                            if(it.value != Cell.Empty) {
                                border(color = Color.Black, width = 1.dp)
                            } else this
                        }
                )
            }
        },
        modifier = modifier,
    ) { measurables, constraints ->
        val totalWidth = constraints.maxWidth

        val cellSize = totalWidth / NUM_COLS

        val placeables = measurables.map {
            it.map { measurable ->
                measurable.measure(
                    Constraints.fixed(width = cellSize, height = cellSize)
                )
            }
        }

        val measuredWidth = cellSize * NUM_COLS
        val measuredHeight = cellSize * NUM_ROWS

        layout(measuredWidth, measuredHeight) {
            placeables.mapIndexed { index, it ->
                val x = (index % NUM_COLS) * cellSize
                val y = (index / NUM_COLS) * cellSize
                it.map { placeable ->
                    placeable.place(x,y)
                }
            }
        }
    }
}