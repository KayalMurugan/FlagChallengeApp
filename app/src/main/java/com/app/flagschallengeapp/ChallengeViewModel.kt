package com.app.flagschallengeapp

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.flagschallengeapp.data.model.Question
import com.app.flagschallengeapp.data.model.QuizUiState
import com.app.flagschallengeapp.data.repository.QuizRepository
import com.app.flagschallengeapp.util.QuizPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel  @Inject constructor(
    private val repository: QuizRepository,
    private val quizPrefs: QuizPreferences
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState
    private val _selectedOptionId = MutableStateFlow(0)
    val selectedOptionId: StateFlow<Int?> = _selectedOptionId
    private val _timeSet = MutableStateFlow(false)
    val timeSet: StateFlow<Boolean?> = _timeSet
    private var questions: List<Question> = emptyList()
    private var timerJob: Job? = null
    var questionsLength: Int = 0
    var total = 600

    private val questionDuration = 40
    private val totalQuestions = 15
    private var quizLeftTime: Long = 0L
    private var timeDifference: Long = 0L

    val currentQuestion: Question?
        get() = questions.getOrNull(_uiState.value.currentQuestionIndex)

    init {
        viewModelScope.launch {
            questions = repository.loadQuestions()
            if (questions.isNotEmpty()) {
                startQuestionTimer()
                questionsLength = questions.size
            }
        }

    }
    private fun startQuestionTimer(balanceTime: Int? = null) {
        if(balanceTime==null) {
            timerJob?.cancel()
            timerJob = viewModelScope.launch {
                var time = 30
                while (time > 0) {
                    _uiState.value = _uiState.value.copy(currentTime = time, isShowingAnswer = false)
                    delay(1000)
                    time--
                }
                showAnswerPhase()
            }
        } else {
            if(balanceTime>30){
                timerJob?.cancel()
                timerJob = viewModelScope.launch {
                    _uiState.value = _uiState.value.copy(currentTime = 0, isShowingAnswer = false)
                    showAnswerPhase(balanceTime)
                }
            } else {
                timerJob?.cancel()
                timerJob = viewModelScope.launch {
                    var time = 30 - balanceTime
                    while (time > 0) {
                        _uiState.value = _uiState.value.copy(currentTime = time, isShowingAnswer = false)
                        delay(1000)
                        time--
                    }
                    showAnswerPhase()
                }
            }
        }

    }
    private fun showAnswerPhase(balanceTime: Int? = null) {
        if(balanceTime == null) {
            timerJob?.cancel()
            timerJob = viewModelScope.launch {
                var time = 10
                while (time > 0) {
                    _uiState.value = _uiState.value.copy( currentTime = 0, isShowingAnswer = true)
                    delay(1000)
                    time--
                }
                moveToNextQuestion()
            }
        } else {
            timerJob?.cancel()
            timerJob = viewModelScope.launch {
                var time = 10 - balanceTime
                while (time > 0) {
                    _uiState.value = _uiState.value.copy( currentTime = 0, isShowingAnswer = true)
                    delay(1000)
                    time--
                }
                moveToNextQuestion()
            }
        }

    }
    private fun moveToNextQuestion(questionNo: Int = 0, balanceTime: Int = 0) {
        var nextIndex = _uiState.value.currentQuestionIndex + 1
        if(questionNo != 0){
            nextIndex = questionNo
        }
        if(total>0){ total = total - 40 }
        if (nextIndex < questions.size) {
            _uiState.value = QuizUiState(currentQuestionIndex = nextIndex)
            startQuestionTimer(balanceTime)
        } else {
            _uiState.value = _uiState.value.copy(isFinished = true)
            viewModelScope.launch {
                quizPrefs.clear()
            }
        }
    }
    fun setTime(value: Boolean){
      _timeSet.value = value
        updateFormattedTime()
        viewModelScope.launch {
            quizPrefs.saveSave(true);
            quizPrefs.saveSaveTime(formattedTime)
        }
    }
    fun selectAnswer(answerId: Int, isCorrect: Boolean) {
        _uiState.value = _uiState.value.copy(selectedAnswerId = answerId)
        if(isCorrect){
            _selectedOptionId.value = _selectedOptionId.value+1;
        } else {
            _selectedOptionId.value = _selectedOptionId.value+0;
        }
    }
    fun onGameFinished() {
        _uiState.update { it.copy(isFinished = true, showScore = false) }
        viewModelScope.launch {
            delay(1000)
            _uiState.update { it.copy(showScore = true) }
        }
    }
    fun leftQuiz() {
        viewModelScope.launch {
            quizLeftTime = System.currentTimeMillis()
            quizPrefs.saveStartTime(quizLeftTime)
            quizPrefs.saveTotalTime(total)
        }
    }
    suspend fun resumeQuiz() {
        quizLeftTime = quizPrefs.getStartTime()?:0L
        total = quizPrefs.getTotalTime()?:600

        if(quizLeftTime!=0L){
            val now = System.currentTimeMillis()
            timeDifference = now - quizLeftTime
            val elapsedSeconds = (timeDifference.toInt()) / 1000
            var balanceSecond: Int? = null

            if(total>0){
                balanceSecond =  total - elapsedSeconds

                val quotient = balanceSecond / questionDuration
                val remainder = balanceSecond % questionDuration

                val questionNo = totalQuestions - quotient
                val timerNo = questionDuration - remainder

                moveToNextQuestion(if(questionNo>0)questionNo-1 else questionNo, timerNo)
            }
        }

    }

    var hours1 by mutableStateOf("0")
    var minutes1 by mutableStateOf("0")
    var seconds1 by mutableStateOf("0")

    var hours2 by mutableStateOf("0")
    var minutes2 by mutableStateOf("0")
    var seconds2 by mutableStateOf("0")

    var formattedTime by mutableStateOf("00:00:00")

    private var targetTimeMillis: Long = 0L

    var remainingTime by mutableStateOf(0)
        private set

    val targetFormattedTime: String
        get() {
            val diffSeconds = ((targetTimeMillis - System.currentTimeMillis()) / 1000).coerceAtLeast(0)
            val h = diffSeconds / 3600
            val m = (diffSeconds % 3600) / 60
            val s = diffSeconds % 60
            return "%02d:%02d:%02d".format(h, m, s)
        }
    fun updateFormattedTime(time: String = "") {
        if(time.isEmpty()) {
            val h = (hours1 + hours2).ifEmpty { "0" }.padStart(2, '0')
            val m = (minutes1 + minutes2).ifEmpty { "0" }.padStart(2, '0')
            val s = (seconds1 + seconds2).ifEmpty { "0" }.padStart(2, '0')
            formattedTime = "$h:$m:$s"
        } else {
            setTimeFromString(time)
        }
    }
    fun setTimeFromString(time: String) {
        val parts = time.split(":").map { it.toIntOrNull() ?: 0 }
        val h = parts.getOrElse(0) { 0 }
        val m = parts.getOrElse(1) { 0 }
        val s = parts.getOrElse(2) { 0 }

        val totalSeconds = h * 3600 + m * 60 + s

        targetTimeMillis = System.currentTimeMillis() + totalSeconds * 1000L

        updateRemainingTimeFromTarget()
    }
    fun updateRemainingTimeFromTarget() {
        val diffSeconds = ((targetTimeMillis - System.currentTimeMillis()) / 1000).toInt().coerceAtLeast(0)
        remainingTime = diffSeconds

        val hour = remainingTime / 3600
        val min = (remainingTime % 3600) / 60
        val sec = remainingTime % 60
        formattedTime = "%02d:%02d:%02d".format(hour, min, sec)
    }


    fun shouldStartTimer(): Boolean {
        val diffSeconds = (targetTimeMillis/1000).toInt()
        return diffSeconds > 20
    }


}