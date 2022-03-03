package com.kazantsev.domain.use_case

import com.kazantsev.domain.repository.Repository
import javax.inject.Inject

class ClearSolvedUseCase @Inject constructor(
    private val repository: Repository,
) {
    suspend operator fun invoke() {
        return repository.clearSolvedPuzzle()
    }
}