package com.example.permutabittools.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.permutabittools.views.HistoricoViewHolder
import com.example.permutabittools.dataBase.ConversoesDataBase
import com.example.permutabittools.databinding.ItemHistoricoBaseNumericaBinding

class HIstoricoAdapter(
    private val onItemClick: (ConversoesDataBase) -> Unit,
    private val onItemLongClick: (ConversoesDataBase) -> Unit):
    RecyclerView.Adapter<HistoricoViewHolder>(){

    private val listaConversoes = mutableListOf<ConversoesDataBase>()

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
        holder.itemView.setOnClickListener { onItemClick(listaConversoes[position]) }
        holder.itemView.setOnLongClickListener{ onItemLongClick(listaConversoes[position]); true}
    }
    override fun getItemCount(): Int {
        //retorna o tamanho da lista
        return listaConversoes.size
    }
    fun atualizaLista(novaLista: List<ConversoesDataBase>){
        listaConversoes.clear()
        listaConversoes.addAll(novaLista)
        notifyDataSetChanged()
    }

}