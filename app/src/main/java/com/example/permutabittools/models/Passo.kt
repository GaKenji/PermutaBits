package com.example.permutabittools.models

import com.example.permutabittools.enum.TipoPasso

data class Passo(
    val id: Int,
    val args: List<Any> = emptyList(),
    val tipoPasso: TipoPasso
)