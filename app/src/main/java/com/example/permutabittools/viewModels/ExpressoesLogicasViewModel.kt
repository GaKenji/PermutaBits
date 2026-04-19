package com.example.permutabittools.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExpressoesLogicasViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Em desenvolvimento"
    }
    val text: LiveData<String> = _text
}