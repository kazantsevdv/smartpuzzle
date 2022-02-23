package com.kazantsev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kazantsev.data.database.model.CategoryEntity
import com.kazantsev.data.database.model.PuzzleEntity

@Database(
    entities = [
        CategoryEntity::class,
        PuzzleEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val puzzleDao: AppDao
}