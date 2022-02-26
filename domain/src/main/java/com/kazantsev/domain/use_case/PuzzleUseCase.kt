package com.kazantsev.domain.use_case

import com.kazantsev.domain.model.Puzzle
import com.kazantsev.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PuzzleUseCase  @Inject constructor(
    private val repository: Repository,
) {
    operator fun invoke(id:Int): Flow<Puzzle> {
        return repository.getPuzzleById(id)
    }
}