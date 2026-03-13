package com.example.permutabittools.baseNumerica.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.permutabittools.R
import com.example.permutabittools.baseNumerica.baseNumericaModel.NumericBase
import com.example.permutabittools.dataBase.ConversoesDataBase
import com.example.permutabittools.databinding.FragmentCalculoBaseNumericaBinding

class CalculoBaseNumerica : Fragment(), View.OnClickListener {

    private var _binding: FragmentCalculoBaseNumericaBinding? = null
    private val binding get() = _binding!!
    private var conversao: ConversoesDataBase? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalculoBaseNumericaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receberDadosConversao() //Recebo os dados da fragment
        mostrarDadosConversao() //Exibo os dados na parte superior da fragment
        calcularEmostrarConversao() //Faz os cálculos e mostro o passo a passo da conversão
        binding.buttonCopiar.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun receberDadosConversao() {
        conversao = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable("conversaoSelecionada", ConversoesDataBase::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable("conversaoSelecionada") as? ConversoesDataBase
        }
    }

    private fun mostrarDadosConversao() {
        binding.textInformaBaseEntrada.setText("${conversao?.baseOrigem}")
        binding.textInformaBaseSaida.setText("${conversao?.baseDestino}")
        binding.txtInformaValorEndrada.setText("${conversao?.valorEntrada}")
        binding.textInformaValorSaida.setText("${conversao?.valorSaida}")
    }

    private fun calcularEmostrarConversao() {
        if(conversao!!.baseOrigem.equals(NumericBase.DECIMAL)){
            if(conversao!!.baseDestino.equals(NumericBase.HEXADECIMAL)){
                binding.txtPassoapasso.setText("De decimal -> Mapeamento ->  para hexadecimal")
            }
            else{
                binding.txtPassoapasso.setText("De decimal para ${conversao?.baseDestino}")
            }
        }
        else{
            if(conversao!!.baseOrigem.equals(NumericBase.HEXADECIMAL)){
                binding.txtPassoapasso.setText("De Hexadecimal -> Mapeamento -> ${conversao?.baseDestino}")
            }
            else{
                binding.txtPassoapasso.setText("De ${conversao?.baseOrigem} -> ${conversao?.baseDestino}")
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.buttonCopiar -> {
                Toast.makeText(requireContext(), "Copiando cálculos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}