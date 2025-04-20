package ru.marat.library.di.app_modules

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import ru.marat.core_data.AppPreferences
import ru.marat.core_data.AppPreferencesImpl
import ru.marat.core_data.FileManager
import ru.marat.core_data.FileManagerImpl
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideAppPreferences(context: Context): AppPreferences = AppPreferencesImpl(context)

    @Singleton
    @Provides
    fun provideFileManager(context: Context): FileManager = FileManagerImpl(context)

    @Singleton
    @Provides
    fun provideWorkManager(context: Context): WorkManager = WorkManager.getInstance(context)
}