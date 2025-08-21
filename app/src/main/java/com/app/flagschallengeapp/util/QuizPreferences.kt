package com.app.flagschallengeapp.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map



val Context.quizDataStore by preferencesDataStore(name = "quiz_prefs")

class QuizPreferences(private val context: Context) {

    companion object {
        val QUIZ_LEFT_TIME = longPreferencesKey("quiz_left_time")
        val QUIZ_TOTAL_TIME = intPreferencesKey("quiz_total_time")
        val QUIZ_SAVE_TIME = stringPreferencesKey("quiz_save_time")
        val QUIZ_SAVE = booleanPreferencesKey("quiz_save")
    }
    suspend fun getStartTime(): Long? {
        val prefs = context.quizDataStore.data.first()
        return prefs[QUIZ_LEFT_TIME]
    }

    suspend fun saveTotalTime(total: Int) {
        context.quizDataStore.edit { prefs ->
            prefs[QUIZ_TOTAL_TIME] = total
        }
    }

    suspend fun getTotalTime(): Int? {
        val prefs = context.quizDataStore.data.first()
        return prefs[QUIZ_TOTAL_TIME]
    }

    suspend fun saveStartTime(time: Long) {
        context.quizDataStore.edit { prefs ->
            prefs[QUIZ_LEFT_TIME] = time
        }
    }

    suspend fun getSaveTime(): String? {
        val prefs = context.quizDataStore.data.first()
        return prefs[QUIZ_SAVE_TIME]
    }

    suspend fun saveSaveTime(saveTime: String) {
        context.quizDataStore.edit { prefs ->
            prefs[QUIZ_SAVE_TIME] = saveTime
        }
    }

    suspend fun getSave(): Boolean? {
        val prefs = context.quizDataStore.data.first()
        return prefs[QUIZ_SAVE]
    }

    suspend fun saveSave(save: Boolean) {
        context.quizDataStore.edit { prefs ->
            prefs[QUIZ_SAVE] = save
        }
    }

    suspend fun clear() {
        context.quizDataStore.edit { prefs ->
            prefs.remove(QUIZ_LEFT_TIME)
            prefs.remove(QUIZ_TOTAL_TIME)
            prefs.remove(QUIZ_SAVE_TIME)
            prefs.remove(QUIZ_SAVE)
        }
    }
}
