package com.app.flagschallengeapp.data.model

data class QuizUiState(
    val currentQuestionIndex: Int = 0,
    val currentTime: Int = 0,
    val isShowingAnswer: Boolean = false,
    val isFinished: Boolean = false,
    val selectedAnswerId: Int? = null,
    val showScore: Boolean = false,
)