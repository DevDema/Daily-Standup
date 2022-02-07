package com.andreadematteis.dailystandup.presentation.di.core

import android.content.Context
import androidx.room.Room
import com.andreadematteis.dailystandup.data.local.DailyStandupDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): DailyStandupDatabase =
        Room.databaseBuilder(
            context,
            DailyStandupDatabase::class.java,
            DailyStandupDatabase.DATABASE_NAME
        )
            .createFromAsset("daily_standup.db")
            .build()

    @Singleton
    @Provides
    fun provideMeetingDao(database: DailyStandupDatabase) = database.meetingDao()

    @Singleton
    @Provides
    fun provideTeamMemberDao(database: DailyStandupDatabase) = database.teamMemberDao()
}