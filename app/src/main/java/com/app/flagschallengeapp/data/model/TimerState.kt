package com.app.flagschallengeapp.data.model

data class TimerState(
    val currentQuestionIndex: Int = 0,
    val remainingSeconds: Int = 0,
    val inGap: Boolean = false,
    val isFinished: Boolean = false
)