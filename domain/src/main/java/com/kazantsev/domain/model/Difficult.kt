package com.kazantsev.domain.model

enum class Difficult(val value: Int) {
    Easy(1),
    Medium(2),
    Hard(3);
    companion object {
        fun valueOf(value: Int) = Difficult.values().find { it.value == value }!!
    }
}