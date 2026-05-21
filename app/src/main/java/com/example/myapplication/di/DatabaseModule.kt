package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.data.local.MentorDatabase
import com.example.myapplication.data.local.dao.EventDao
import com.example.myapplication.data.local.dao.HabitDao
import com.example.myapplication.data.local.dao.ViolationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMentorDatabase(
        @ApplicationContext context: Context
    ): MentorDatabase {
        return Room.databaseBuilder(
            context,
            MentorDatabase::class.java,
            "mentor_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHabitDao(database: MentorDatabase): HabitDao = database.habitDao()

    @Provides
    @Singleton
    fun provideViolationDao(database: MentorDatabase): ViolationDao = database.violationDao()

    @Provides
    @Singleton
    fun provideEventDao(database: MentorDatabase): EventDao = database.eventDao()
}
