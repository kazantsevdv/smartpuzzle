package com.kazantsev.domain.model

data class Puzzle(
    val id: Int,
    val categoryID: Int,
    val name: String,
    val question: String,
    val answer: String,
    val difficult: Difficult,
    val solved: Boolean,
    val favorite: Boolean,
    val link: String,
)