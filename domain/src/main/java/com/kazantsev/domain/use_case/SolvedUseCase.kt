package com.kazantsev.domain.use_case

import com.kazantsev.domain.model.Puzzle
import com.kazantsev.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SolvedUseCase  @Inject constructor(
    private val repository: Repository,
) {
    suspend operator fun  invoke(id:Int) {
        return repository.updateSolvedByPuzzleId(id)
    }
}