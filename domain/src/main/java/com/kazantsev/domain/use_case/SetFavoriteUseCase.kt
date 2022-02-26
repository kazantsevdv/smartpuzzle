package com.kazantsev.domain.use_case

import com.kazantsev.domain.repository.Repository
import javax.inject.Inject

class SetFavoriteUseCase @Inject constructor(
    private val repository: Repository,
) {
    suspend operator fun invoke(id: Int, isFavorite: Boolean) =
        repository.updateFavoriteByPuzzleId(id, isFavorite)
}