package com.example.permutabittools.models

import com.example.permutabittools.models.TipoPasso

data class Passo(
    val id: Int,
    val args: List<Any> = emptyList(),
    val tipoPasso: TipoPasso
)