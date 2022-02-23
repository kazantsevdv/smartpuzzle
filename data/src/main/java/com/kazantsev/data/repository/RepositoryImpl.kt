package com.kazantsev.data.repository

import androidx.room.RoomDatabase
import com.kazantsev.data.database.AppDatabase
import com.kazantsev.data.database.model.CategoryEntity
import com.kazantsev.data.network.api.ApiDataSource
import com.kazantsev.domain.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiDataSource,
    private val db: AppDatabase,
) : Repository {
    override fun getNote(): Flow<List<String>> =
db.puzzleDao.getAllFavorite().map { it.map { it.name } }

}