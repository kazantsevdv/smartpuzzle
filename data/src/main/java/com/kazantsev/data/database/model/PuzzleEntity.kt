package com.kazantsev.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Puzzle")
data class PuzzleEntity(
    @PrimaryKey val id: Int? = null,
    val categoryID: Int,
    val name: String,
    val question: String,
    val answer: String,
    val difficult: Int,
    val solved: Boolean,
    val favorite: Boolean,
    val link: String,
)