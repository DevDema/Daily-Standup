package com.example.dailystandup.presentation.di.core

import android.content.Context
import androidx.room.Room
import com.example.dailystandup.data.local.DailyStandupDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DailyStandupDatabase =
        Room.databaseBuilder(
            context,
            DailyStandupDatabase::class.java,
            DailyStandupDatabase.DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideMeetingDao(database: DailyStandupDatabase) = database.meetingDao()

    @Singleton
    @Provides
    fun provideTeamMemberDao(database: DailyStandupDatabase) = database.teamMemberDao()
}