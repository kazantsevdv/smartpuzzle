package com.kazantsev.domain.use_case

import com.kazantsev.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BlankUseCase @Inject constructor(
    private val repository: Repository,
) {
    operator fun invoke(id: Int): Flow<List<String>> {
        return repository.getNote()
    }
}