package com.example.permutabittools.viewModel

import java.io.Serializable
data class Conversoes (
    val baseOrigem: String,
    val baseDestino: String,
    val valorEntrada: String,
    val valorSaida: String,
    val data: String,
    val hora: String,
    val favotito: Boolean
    //classe destinada a armazenar uma conversão de base numérica
): Serializable