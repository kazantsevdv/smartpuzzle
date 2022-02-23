package com.kazantsev.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class CategoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: Long,
)