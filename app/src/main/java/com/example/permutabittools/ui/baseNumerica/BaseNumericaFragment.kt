package com.example.permutabittools.ui.baseNumerica

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.permutabittools.databinding.FragmentBasenumericaBinding
import com.example.permutabittools.utils.AdapterSpinnerNumericBase

class BaseNumericaFragment : Fragment(), View.OnClickListener{

    private var _binding: FragmentBasenumericaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBasenumericaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonConverterBaseNumerica.setOnClickListener(this)
        loadSpinners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.buttonConverterBaseNumerica.id -> {
                binding.textViewMostraResultado.setText("Convertendo valores...")
            }
        }
    }

    private fun loadSpinners(){
        val lista1 = listOf("De...", "Binário", "Octal", "Decimal", "Hexadecimal")
        val lista2 = listOf("Para...", "Binário", "Octal", "Decimal", "Hexadecimal")
        val adapter1 = AdapterSpinnerNumericBase(requireContext(), lista1)
        val adapter2 = AdapterSpinnerNumericBase(requireContext(), lista2)
        binding.spinnerConversaoNumerica1.adapter = adapter1
        binding.spinnerConversaoNumerica2.adapter = adapter2
    }
}