package com.app.flagschallengeapp.data.repository

import com.app.flagschallengeapp.data.model.Question

interface QuizRepository {
    suspend fun loadQuestions(): List<Question>
}
