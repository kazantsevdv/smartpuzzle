package com.kazantsev.ui_common.util

import android.graphics.Color
import com.kazantsev.domain.model.Difficult

object Util {
    fun favoriteColor(isFavorite: Boolean) =
        if (isFavorite) Color.MAGENTA else Color.GRAY

     fun difficultColor(difficult: Difficult) = when (difficult) {
        Difficult.Easy -> Color.GREEN
        Difficult.Medium -> Color.YELLOW
        Difficult.Hard -> Color.RED
         else -> Color.WHITE
     }
}