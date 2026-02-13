package com.example.permutabittools.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.permutabittools.databinding.ItemHistoricoBaseNumericaBinding
import com.example.permutabittools.util.Conversoes
import com.example.permutabittools.util.HistoricoViewHolder

class HIstoricoAdapter: RecyclerView.Adapter<HistoricoViewHolder>(){

    private val listaConversoes = mutableListOf<Conversoes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoViewHolder {
        //Cria a view referente ao meu layout de cada item
        val view = ItemHistoricoBaseNumericaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        return HistoricoViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoricoViewHolder, position: Int) {
        //Atribui valores para os elementos de layout
        holder.bind(listaConversoes[position])
    }

    override fun getItemCount(): Int {
        //retorna o tamanho da lista
        return listaConversoes.size
    }

    fun adicionarConversoes(conversao: Conversoes){
        //Adiciona cada conversão realizada a minha lista de conversões do adapter
        listaConversoes.add(0, conversao)
        notifyItemInserted(0)
    }

}