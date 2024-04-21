package com.techullurgy.tetrisgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.techullurgy.tetrisgame.ui.components.GameBoard
import com.techullurgy.tetrisgame.ui.theme.TetrisGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TetrisGameTheme {
                GameBoard()
            }
        }
    }
}