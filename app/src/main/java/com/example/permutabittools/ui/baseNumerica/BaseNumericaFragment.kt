package com.example.permutabittools.ui.baseNumerica

import android.graphics.Color
import android.graphics.LightingColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.permutabittools.R
import com.example.permutabittools.databinding.FragmentBasenumericaBinding
import com.example.permutabittools.adapters.AdapterSpinnerNumericBase
import com.example.permutabittools.util.NumericBase
import com.google.android.material.snackbar.Snackbar
import kotlinx.serialization.StringFormat

class BaseNumericaFragment : Fragment(), View.OnClickListener{

    private var _binding: FragmentBasenumericaBinding? = null
    private val binding get() = _binding!!
    private var baseOrigem: NumericBase? = null
    private var baseDestino: NumericBase? = null
    private lateinit var valor: String

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
        loadSpinners() //Carrega o conteúdo dos spinners

        //Tratamento da seleção de itens do spinner de origem
        binding.spinnerConversaoNumerica1.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                baseOrigem = mapearBases(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        //Tratamento da seleção de itens do spinner de destino
        binding.spinnerConversaoNumerica2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                baseDestino = mapearBases(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.buttonConverterBaseNumerica.setOnClickListener(this)//captação do evento de click do botão converter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        //tratamento do click do botão converter
        when(v?.id){
            binding.buttonConverterBaseNumerica.id -> {
                valor = binding.editTextInputBaseNumerica.text.toString()
                if(valor.isEmpty()){
                    alerta(getString(R.string.alerta_inserir_valor))
                    return
                }
                else if(baseOrigem == null || baseDestino == null){
                    alerta(getString(R.string.alerta_selecionar_bases))
                    return
                }

                else converter()
            }
        }
    }

    private fun loadSpinners(){
        //Prenche listas de valores que serão colocados nos spinners
        //Os valores colocados estão descritos em strings.xml
        val lista1 = resources.getStringArray(R.array.bases_numericas_origem).toList()
        val lista2 = resources.getStringArray(R.array.bases_numericas_destino).toList()

        //Uso de um adapter para criar um spinner personalizado
        //Requer o contexto e a lista de valores que preencherão os spinners
        val adapter1 = AdapterSpinnerNumericBase(requireContext(), lista1)
        val adapter2 = AdapterSpinnerNumericBase(requireContext(), lista2)

        //preenche os spinners depois de obtermos o adapter
        binding.spinnerConversaoNumerica1.adapter = adapter1
        binding.spinnerConversaoNumerica2.adapter = adapter2
    }

    private fun mapearBases(position: Int): NumericBase?{
        //Mapeamento das bases numéricas para os spinners
        return when(position){
            1-> NumericBase.BINARIO
            2-> NumericBase.OCTAL
            3-> NumericBase.DECIMAL
            4-> NumericBase.HEXADECIMAL
            else -> null
        }
    }

    private fun converter(){
        //Método responsável por fazer as conversões
        //Chamado quando o usuário apertar o botão converter
        try{
            val decimal = paraDecimal()//Converte o valor primeiro para decimal
            val resultado = deDecimal(decimal)//Depois converte o valor para a base desejada
            binding.textViewMostraResultado.setText(resultado)
        }catch (e: Exception){
            alerta(getString(R.string.alerta_valor_invalido))
            //alerta chamado para quando o usuário digitar uma entrada inválida
        }
    }

    private fun alerta(alerta: String){
        AlertDialog.Builder(requireContext()).
        setMessage(alerta).
        setNeutralButton(getString(R.string.entendi), null).
        show()
        //Método usado para alertar o usuário sobre algum erro
        //Pega uma string do arquivo strings.xml referente ao erro e exibe ao usuário
    }

    private fun paraDecimal(): Int{
        //Primeiro passo da conversão
        //Pega o valor da textView e depois converte para decimal
        return when(baseOrigem!!){
            //Pegua a string valor que está na base radix e devolve um decimal equivalente
            NumericBase.BINARIO -> valor.toInt(2)
            NumericBase.OCTAL -> valor.toInt(8)
            NumericBase.DECIMAL -> valor.toInt(10)
            NumericBase.HEXADECIMAL -> valor.toInt(16)
        }
    }

    private fun deDecimal(dcimal: Int): String{
        //Segundo passo da conversão
        //Pega o valor em Decimal e converte para a base de destino escolhida
        return when(baseDestino!!){
            NumericBase.BINARIO -> dcimal.toString(2)
            NumericBase.OCTAL -> dcimal.toString(8)
            NumericBase.DECIMAL -> dcimal.toString(10)
            NumericBase.HEXADECIMAL -> dcimal.toString(16).uppercase()
        }
    }
}