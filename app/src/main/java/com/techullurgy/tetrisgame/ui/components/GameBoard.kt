package com.techullurgy.tetrisgame.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techullurgy.tetrisgame.domain.TetrisGame
import kotlinx.coroutines.delay

const val NUM_ROWS = 45
const val NUM_COLS = 30

@Preview
@Composable
fun GameBoard(
    modifier: Modifier = Modifier
) {

    val tetrisGame = remember {
        TetrisGame()
    }

    LaunchedEffect(key1 = Unit) {
        while (true) {
            delay(100)
            tetrisGame.update()
        }
    }

    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val leftInteraction = remember { MutableInteractionSource() }
        val rightInteraction = remember { MutableInteractionSource() }

        val leftPressed by leftInteraction.collectIsPressedAsState()
        val rightPressed by rightInteraction.collectIsPressedAsState()

        LaunchedEffect(key1 = leftPressed) {
            while(leftPressed) {
                tetrisGame.goLeft()
                delay(50)
            }
        }

        LaunchedEffect(key1 = rightPressed) {
            while(rightPressed) {
                tetrisGame.goRight()
                delay(50)
            }
        }

        TetrisBoard(
            board = tetrisGame.board,
            modifier = Modifier.background(Color.Black)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.Magenta)
                    .clickable(interactionSource = leftInteraction, indication = null) {},
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(32.dp))
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.Magenta)
                    .clickable { tetrisGame.goDown() },
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
            }
            Spacer(modifier = Modifier.width(32.dp))
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color.Magenta)
                    .clickable(interactionSource = rightInteraction, indication = null) {},
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
        }
    }
}