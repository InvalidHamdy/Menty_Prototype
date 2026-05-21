package com.example.myapplication.di

import com.example.myapplication.data.repository.*
import com.example.myapplication.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindHabitRepository(
        roomHabitRepositoryImpl: RoomHabitRepositoryImpl
    ): HabitRepository

    @Binds
    @Singleton
    abstract fun bindViolationRepository(
        roomViolationRepositoryImpl: RoomViolationRepositoryImpl
    ): ViolationRepository

    @Binds
    @Singleton
    abstract fun bindEventRepository(
        roomEventRepositoryImpl: RoomEventRepositoryImpl
    ): EventRepository

    @Binds
    @Singleton
    abstract fun bindTimerRepository(
        mockTimerRepositoryImpl: MockTimerRepositoryImpl
    ): TimerRepository

    @Binds
    @Singleton
    abstract fun bindAccessibilityRepository(
        accessibilityRepositoryImpl: AccessibilityRepositoryImpl
    ): AccessibilityRepository
}
