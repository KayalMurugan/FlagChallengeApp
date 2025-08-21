package com.app.flagschallengeapp.util

import androidx.compose.ui.unit.dp
import com.app.flagschallengeapp.data.model.Question


val OPTION_WIDTH = 102.dp
val OPTION_HEIGHT = 24.dp
val FLAG_WIDTH = 72.dp
val FLAG_HEIGHT = 57.dp
val SPACER_WIDTH = 20.dp
val TEXT_PADDING_HORIZONTAL = 12.dp
val TEXT_PADDING_VERTICAL = 3.dp

data class ChallengeUiState(
    val scheduledTime: Long? = null,
    val questions: List<Question> = emptyList(),
    val answers: Map<Int, Int?> = emptyMap()
) {
    fun computeScore(): Int {
        return questions.indices.count { index ->
            answers[index] == questions[index].answer_id
        }
    }
}

sealed class Phase {
    object Waiting : Phase()
    data class PreStart(val remainingSec: Int) : Phase()
    data class Running(val index: Int, val remainingSec: Int) : Phase()
    data class Interval(val index: Int, val remainingSec: Int) : Phase()
    object Finished : Phase()
}
