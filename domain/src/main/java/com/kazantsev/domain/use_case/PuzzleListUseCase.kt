package com.kazantsev.domain.use_case

import com.kazantsev.domain.model.Puzzle
import com.kazantsev.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PuzzleListUseCase  @Inject constructor(
    private val repository: Repository,
) {
    operator fun invoke(id:Int): Flow<List<Puzzle>> {
        return repository.getPuzzleListByCategory(id)
    }
}