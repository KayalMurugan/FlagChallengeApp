package com.app.flagschallengeapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val answer_id: Int,
    val countries: List<Country>,
    val country_code: String
)

@Serializable
data class Country(
    val country_name: String,
    val id: Int
)

@Serializable
data class QuizJsonWrapper(
    val questions: List<Question>
)
