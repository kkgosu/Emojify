package com.kvlg.emojify.di

import android.content.Context
import com.kvlg.emojify.domain.ResourceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * @author Konstantin Koval
 * @since 11.11.2020
 */
@Module
@InstallIn(ApplicationComponent::class)
object Module {

    @Provides
    fun provideResourceManager(@ApplicationContext context: Context) = ResourceManager(context)
}