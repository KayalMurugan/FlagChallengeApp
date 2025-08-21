package com.app.flagschallengeapp.di

import com.app.flagschallengeapp.QuizRepositoryImpl
import com.app.flagschallengeapp.data.repository.QuizRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindQuestionRepository(
        impl: QuizRepositoryImpl
    ): QuizRepository
}
