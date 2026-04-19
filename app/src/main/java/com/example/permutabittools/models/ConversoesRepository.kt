package com.example.permutabittools.models

import com.example.permutabittools.dataBase.ConversoesDAO
import com.example.permutabittools.dataBase.ConversoesDataBase

class ConversoesRepository(private val dao: ConversoesDAO) {
    var historico = dao.getAll()

    suspend fun inserir(conversao: ConversoesDataBase){
        dao.inserirConversao(conversao)
    }

    suspend fun deletarItem(conv: ConversoesDataBase){
        dao.deleteItem(conv)
    }

    suspend fun deletar(){
        dao.deleteAll()
    }
}