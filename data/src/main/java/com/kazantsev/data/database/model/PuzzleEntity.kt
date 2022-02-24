package com.kazantsev.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kazantsev.domain.model.Difficult
import com.kazantsev.domain.model.Puzzle

@Entity(tableName = "Puzzle")
data class PuzzleEntity(
    @PrimaryKey val id: Int,
    val categoryID: Int,
    val name: String,
    val question: String,
    val answer: String,
    val difficult: Int,
    val solved: Boolean,
    val favorite: Boolean,
    val link: String,
) {
    fun toDomain() = Puzzle(
        id=id,
        categoryID=categoryID,
        name=name,
        question=question,
        answer=answer,
        difficult= Difficult.valueOf(difficult),
        solved=solved,
        favorite=favorite,
        link=link,
    )
}