package com.kazantsev.domain.model

data class CategoryWithTotal(
    val categoryID: Int,
    val total: Int,
    val solved: Int,
)