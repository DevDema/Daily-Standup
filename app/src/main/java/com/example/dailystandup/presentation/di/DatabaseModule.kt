package com.example.dailystandup.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.dailystandup.data.local.DailyStandupDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): DailyStandupDatabase =
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