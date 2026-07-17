package com.example.permutabittools.models

import java.io.Serializable

data class NetworkInfo(
    val endereco: String,
    val mascara: String,
    val broadcast: String,
    val primeiroHost: String,
    val ultimoHost: String,
    val totalHosts: Int,
    val hostsUtilizaveis: Int,
    val classeIp: String,
    val privado: Boolean
): Serializable
