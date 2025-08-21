package com.app.flagschallengeapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.flagschallengeapp.ChallengeViewModel
import com.app.flagschallengeapp.ui.theme.DarkGreyColor
import com.app.flagschallengeapp.ui.theme.PrimaryColor

@Composable
fun QuizFinishScreen(
    viewModel: ChallengeViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val  correctAnswerCount by viewModel.selectedOptionId.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onGameFinished()
    }

    if (!uiState.showScore) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "GAME OVER",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkGreyColor,
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Spacer(modifier = Modifier.height(40.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SCORE :",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = PrimaryColor,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "${correctAnswerCount}/${viewModel.questionsLength}",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = DarkGreyColor,
                    )
                }
            }
        }
    }
}