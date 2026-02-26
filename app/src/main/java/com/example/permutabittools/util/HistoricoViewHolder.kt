package com.example.permutabittools.util

import androidx.recyclerview.widget.RecyclerView
import com.example.permutabittools.databinding.ItemHistoricoBaseNumericaBinding

class HistoricoViewHolder(private val item: ItemHistoricoBaseNumericaBinding): RecyclerView.ViewHolder(item.root) {

    fun bind(listaConversoes: Conversoes){
        //liga cada elememto de layout a cada campo correspondente da conversão
        //A cada conversão, os valores são armazenados na classe Conversão e depois esses valores são atribuidos aos elementos de layout
        item.textHistoricoBaseOrigem.text = listaConversoes.baseOrigem
        item.textHistoricoValorEntrada.text = listaConversoes.valorEntrada
        item.textHistoricoBaseDestino.text = listaConversoes.baseDestino
        item.textHistoricoValorSaida.text = listaConversoes.valorSaida
        item.textHistoricoData.text = listaConversoes.data
        item.textHistoricoHora.text = listaConversoes.hora
    }
}