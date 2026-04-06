package com.example.permutabittools.baseNumerica.baseNumericaModel

import com.example.permutabittools.dataBase.ConversoesDAO
import com.example.permutabittools.dataBase.ConversoesDataBase

class ConversoesRepository(private val dao: ConversoesDAO) {
    var historico = dao.getAll()

    suspend fun inserir(conversao: ConversoesDataBase){
        dao.inserirConversao(conversao)
    }

    suspend fun deletar(){
        dao.deleteAll()
    }
}