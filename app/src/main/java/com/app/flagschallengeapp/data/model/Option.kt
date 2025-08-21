package com.app.flagschallengeapp.data.model


data class Option(
    val text: String,
    val id: Int,
    val ansId: Int,
    var state: OptionState = OptionState.DEFAULT
)

data class Answer(
    val selectedOptionId: Int?,
    val isCorrect: Boolean
)
