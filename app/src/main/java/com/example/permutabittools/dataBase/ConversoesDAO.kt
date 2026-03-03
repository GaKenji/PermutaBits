package com.example.permutabittools.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversoesDAO {

    @Query("SELECT * FROM conversoes ORDER BY id DESC")
    fun getAll(): Flow<List<ConversoesDataBase>>//Flow permite que o Room mantenha o histórico atualizado

    @Query("SELECT * FROM conversoes WHERE favorito = 1 ORDER BY id DESC")
    fun getFavoritos(): Flow<List<ConversoesDataBase>>

    @Query("UPDATE conversoes SET favorito = :valor WHERE id = :id")
    suspend fun atualizaFavoritos(id: Int, valor: Boolean)

    @Insert
    suspend fun inserirConversao(conversao: ConversoesDataBase)

    @Delete
    suspend fun deleteItem(conversao: ConversoesDataBase)

    @Query("DELETE FROM conversoes")
    suspend fun deleteAll()
    //suspend evita que esses métodos fiquem rodando na thread principal
}