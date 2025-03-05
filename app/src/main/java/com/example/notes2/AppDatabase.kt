package com.example.notes2

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun noteDataAccessObject() : NoteDataAccessObject

//    companion object{
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "notes_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
}