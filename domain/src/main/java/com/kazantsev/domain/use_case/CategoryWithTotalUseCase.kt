package com.kazantsev.domain.use_case

import com.kazantsev.domain.model.Category
import com.kazantsev.domain.model.CategoryWithTotal
import com.kazantsev.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryWithTotalUseCase  @Inject constructor(
    private val repository: Repository,
) {
    operator fun invoke(idCategory: Int? = null): Flow<List<CategoryWithTotal>> {
        return repository.getCategoryWithTotal(idCategory)
    }
}