package com.example.permutabittools.viewModels.baseNumericaViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.permutabittools.repository.ConversoesRepository

class BaseNumericaViewModelFactory(
    private val repository: ConversoesRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BaseNumericaViewModel::class.java)){
            return BaseNumericaViewModel(repository) as T
        }
        throw IllegalArgumentException("Classe ViewModel Desconhecida")
    }

}