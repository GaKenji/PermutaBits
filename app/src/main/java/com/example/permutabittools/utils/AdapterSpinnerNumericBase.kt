package com.example.permutabittools.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.permutabittools.R

class AdapterSpinnerNumericBase
    (context: Context,
     private val spinnerItens: List<String>):
    ArrayAdapter<String> (context, 0, spinnerItens){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View{
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
        val text = view.findViewById<TextView>(R.id.textViewOpcaoSpinner)
        text.text = spinnerItens[position]
        return view
    }
}