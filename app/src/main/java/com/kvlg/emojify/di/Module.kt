package com.kvlg.emojify.di

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.kvlg.emojify.data.AnalyticsRepository
import com.kvlg.emojify.data.AnalyticsRepositoryImpl
import com.kvlg.emojify.data.db.history.HistoryTextDatabase
import com.kvlg.emojify.domain.AnalyticsInteractor
import com.kvlg.emojify.domain.AppSettings
import com.kvlg.emojify.domain.EmojiInteractor
import com.kvlg.emojify.domain.ResourceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * @author Konstantin Koval
 * @since 11.11.2020
 */
@Module
@InstallIn(ApplicationComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideHistoryDatabase(@ApplicationContext context: Context): HistoryTextDatabase {
        return Room.databaseBuilder(context, HistoryTextDatabase::class.java, "historyDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideResourceManager(@ApplicationContext context: Context) = ResourceManager(context)

    @Provides
    fun provideInteractor(database: HistoryTextDatabase): EmojiInteractor =
        EmojiInteractor(database.getHistoryTextDao(), Dispatchers.IO)

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    fun provideAppSettings(@ApplicationContext context: Context) = AppSettings(context)

    @Provides
    @Singleton
    fun provideAnalyticsRepository(): AnalyticsRepository {
        return AnalyticsRepositoryImpl()
    }

    @Provides
    fun provideAnalyticsInteractor(repo: AnalyticsRepository): AnalyticsInteractor {
        return AnalyticsInteractor(repo)
    }
}