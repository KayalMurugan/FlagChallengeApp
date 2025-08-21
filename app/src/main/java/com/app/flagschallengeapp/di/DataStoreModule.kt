package com.app.flagschallengeapp.di


import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.app.flagschallengeapp.util.QuizPreferences

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideQuizPreferences(@ApplicationContext context: Context): QuizPreferences {
        return QuizPreferences(context)
    }
}
