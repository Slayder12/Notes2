package com.example.notes2

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module // Эти аннотации используются для создания модулей, которые предоставляют зависимости.
@InstallIn(SingletonComponent::class) // определяет, в каком компоненте будут доступны эти зависимости.
object DatabaseModule {

    @Provides // Эта аннотация используется в модулях для указания, как создавать экземпляры определенных типов.
    @Singleton // Эта аннотация гарантирует, что экземпляр будет создан только один раз в рамках соответствующего компонента.
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "notes_database"
        ).build()
    }

    @Provides
    fun provideNoteDao(appDatabase: AppDatabase): NoteDataAccessObject {
        return appDatabase.noteDataAccessObject()
    }
}