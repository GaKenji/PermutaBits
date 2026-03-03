package com.example.permutabittools.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversoes")
data class ConversoesDataBase(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "baseOrigem") val baseOrigem: String,
    @ColumnInfo(name = "baseDestino") val baseDestino: String,
    @ColumnInfo(name = "valorEntrada")val valorEntrada: String,
    @ColumnInfo(name = "valorSaida")val valorSaida: String,
    @ColumnInfo(name = "data")val data: String,
    @ColumnInfo(name = "hora")val hora: String,
    @ColumnInfo("favorito") val favorito: Boolean
)