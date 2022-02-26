package com.kazantsev.category_screen

import androidx.lifecycle.ViewModel
import com.kazantsev.domain.use_case.CategoryUseCase
import com.kazantsev.domain.use_case.CategoryWithTotalUseCase
import com.kazantsev.category_screen.model.CategoryUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase,
    private val categoryWithTotalUseCase: CategoryWithTotalUseCase
) : ViewModel() {
    fun loadCategory() = categoryUseCase().combine(categoryWithTotalUseCase()) { category, total ->
        category.map {
            CategoryUi(
                id=it.id,
                name = it.name,
                description = it.description,
                total = total.first{total->total.categoryID==it.id}.total,
                solved = total.first{total->total.categoryID==it.id}.solved
             )
        }
    }
}