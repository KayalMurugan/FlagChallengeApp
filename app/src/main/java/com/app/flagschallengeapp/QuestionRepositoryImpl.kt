package com.app.flagschallengeapp

import android.content.Context
import com.app.flagschallengeapp.data.model.Question
import com.app.flagschallengeapp.data.model.QuizJsonWrapper
import com.app.flagschallengeapp.data.repository.QuizRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : QuizRepository {

    override suspend fun loadQuestions(): List<Question> {

        val jsonString = context.resources.openRawResource(R.raw.questions).bufferedReader().use { it.readText() }
        val wrapper = Json.decodeFromString<QuizJsonWrapper>(jsonString)
        return wrapper.questions
    }
}
