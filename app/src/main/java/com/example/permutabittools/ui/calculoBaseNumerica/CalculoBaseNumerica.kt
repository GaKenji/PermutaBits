package com.example.permutabittools.ui.calculoBaseNumerica

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.permutabittools.R
import com.example.permutabittools.databinding.FragmentCalculoBaseNumericaBinding
import com.example.permutabittools.util.Conversoes
import kotlinx.serialization.StringFormat
import kotlin.coroutines.Continuation

class CalculoBaseNumerica : Fragment() {

    private var _binding: FragmentCalculoBaseNumericaBinding? = null
    private val binding get () = _binding!!
    private var conversao: Conversoes? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCalculoBaseNumericaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        conversao = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            arguments?.getSerializable("conversaoSelecionada", Conversoes::class.java)
        }else{
            @Suppress("DEPRECATION")
            arguments?.getSerializable("conversaoSelecionada") as?Conversoes
        }

        binding.textInformaBaseEntrada.setText("${conversao?.baseOrigem}")
        binding.textInformaBaseSaida.setText("${conversao?.baseDestino}")
        binding.txtInformaValorEndrada.setText("${conversao?.valorEntrada}")
        binding.textInformaValorSaida.setText("${conversao?.valorSaida}")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}