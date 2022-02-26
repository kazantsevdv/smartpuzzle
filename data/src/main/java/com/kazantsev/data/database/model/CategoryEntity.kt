package com.kazantsev.data.database.model

import com.kazantsev.domain.model.Category
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Category")
data class CategoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
) {
   internal fun toDomain() = Category(
        id = id,
        name = name,
        description = description,
    )
}