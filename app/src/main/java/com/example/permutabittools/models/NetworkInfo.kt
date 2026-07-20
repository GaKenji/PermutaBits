package com.example.permutabittools.models

import java.io.Serializable

data class NetworkInfo(
    val ip: String,
    val mascara: String,
    val endRede: String,
    val broadcast: String,
    val primeiroHost: String,
    val ultimoHost: String,
    val totalHosts: Int,
    val hostsUtilizaveis: Int,
    val classeIp: String,
    val privado: Boolean
): Serializable
