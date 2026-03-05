package com.example.permutabittools.baseNumerica

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.permutabittools.R
import com.example.permutabittools.dataBase.PermutaDataBase
import com.example.permutabittools.databinding.FragmentBasenumericaBinding
import com.example.permutabittools.baseNumerica.HIstoricoAdapter
import com.example.permutabittools.baseNumerica.baseNumericaModel.NumericBase
import com.example.permutabittools.dataBase.ConversoesDataBase
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BaseNumericaFragment : Fragment(), View.OnClickListener{

    private var _binding: FragmentBasenumericaBinding? = null
    private val binding get() = _binding!!
    private var baseOrigem: NumericBase? = null
    private var baseDestino: NumericBase? = null
    private lateinit var valor: String
    private lateinit var historicoAdapter: HIstoricoAdapter
    private lateinit var db: PermutaDataBase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBasenumericaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = PermutaDataBase.getDataBase(requireContext())

        carregarExposedDropDowns() //Carrega o conteúdo dos spinners

        //Instanciando o adapter do Histórico com seu evento de clique
        historicoAdapter = HIstoricoAdapter{conversoes ->
            val bundle = Bundle()
            bundle.putSerializable("conversaoSelecionada", conversoes)

            findNavController().navigate(R.id
                    .action_nav_basesNumericas_to_nav_calculoBasesNumericas, bundle)
        }

        binding.recyclerHistoricoBasesNumericas.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerHistoricoBasesNumericas.adapter = historicoAdapter

        //Tratamento da seleção de itens do Exposed DropDown de origem
        binding.exposedDropDownInput.setOnItemClickListener { parent, _, position, _ ->
            val selecionado = parent.getItemAtPosition(position).toString()
            baseOrigem = mapearBases(selecionado)
        }

        //Tratamento da seleção de itens do Exposed DropDown de Destino
        binding.exposedDropDownOutput.setOnItemClickListener { parent, _, position, _ ->
            val selecionado = parent.getItemAtPosition(position).toString()
            baseDestino = mapearBases(selecionado)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            db.conversaoDao().getAll().collect {lista->
                historicoAdapter.atualizaLista(lista)
                alterarVisibilidadeReCyclerView()

                if(lista.isNotEmpty()){
                    binding.recyclerHistoricoBasesNumericas.smoothScrollToPosition(0)
                }

            }
        }

        binding.buttonConverterBaseNumerica.setOnClickListener(this)//captação do evento de click do botão converter
        binding.buttonApagarHistorico.setOnClickListener(this)

    }

    override fun onResume() {
        //Método necesário para recarregar os exposed DropDowns assim que o usuário retornar à essa fragment
        super.onResume()
        carregarExposedDropDowns()//Recarrega os Exposed DropDowns ao voltar para a fragment

        binding.exposedDropDownInput.setText("", false)//false ajuda a evitar o disparo do OnItemClickListener
        binding.exposedDropDownOutput.setText("", false)

        baseOrigem = null
        baseDestino = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        //tratamento do click do botão converter
        when(v?.id){
            binding.buttonConverterBaseNumerica.id -> {

                //Pega o valor digitado no TextInput
                valor = binding.editTextInputBaseNumerica.text.toString()

                if(valor.isEmpty()){//Verifica se o usuário digitou o valor da entrada
                    alerta(getString(R.string.alerta_inserir_valor))
                    return
                }
                else if(baseOrigem == null || baseDestino == null){//Verifica se o usuário selecionou as bases numéricas
                    alerta(getString(R.string.alerta_selecionar_bases))
                    return
                }
                else if(baseOrigem == baseDestino){//Verifica se as bases numéricas selecionadas são iguais
                    alerta(getString(R.string.alerta_bases_iguais))
                    return
                }
                else converter() //Se estiver tudo bem, realiza a conversão
                esconderTeclado()//Esconde o teclado ao apertar o botão
            }
            R.id.buttonApagarHistorico -> {
                lifecycleScope.launch {
                    db.conversaoDao().deleteAll()
                }
            }
        }
    }

    private fun carregarExposedDropDowns(){
        //Prenche listas de valores que serão colocados nos spinners
        //Os valores colocados estão descritos em strings.xml
        val lista1 = resources.getStringArray(R.array.bases_numericas).toList()
        val lista2 = resources.getStringArray(R.array.bases_numericas).toList()

        //Uso de um adapter para criar um spinner personalizado
        //Requer o contexto e a lista de valores que preencherão os spinners
        val adapter1 = ArrayAdapter(requireContext(), R.layout.spinner_item,lista1)
        val adapter2 = ArrayAdapter(requireContext(), R.layout.spinner_item,lista2)

        //preenche os spinners depois de obtermos o adapter
        binding.exposedDropDownInput.setAdapter(adapter1)
        binding.exposedDropDownOutput.setAdapter(adapter2)
    }

    private fun mapearBases(select: String): NumericBase?{
        //Mapeamento das bases numéricas para os spinners
        //Trata os itens que podem ser clicados
        //Descarta a acão do click do primeiro item "De..." e "Para..."
        return when(select){
            "Binário" -> NumericBase.BINARIO
            "Octal" -> NumericBase.OCTAL
            "Decimal" -> NumericBase.DECIMAL
            "Hexadecimal" -> NumericBase.HEXADECIMAL
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

            //Pega a data e a hora que a conversão foi feita
            val dataHora = LocalDateTime.now()
            val data = dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            val hora = dataHora.format(DateTimeFormatter.ofPattern("HH:mm"))

            //Instancia a conversao com os valores utilizados nela
            val conversao = ConversoesDataBase(
                0,
                baseOrigem!!.name,
                baseDestino!!.name,
                valor,
                resultado,
                data,
                hora,
                false
            )

            lifecycleScope.launch {
                db.conversaoDao().inserirConversao(conversao)
            }

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

    private fun esconderTeclado(){
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        requireActivity().currentFocus?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            it.clearFocus()
        }
    }

    private fun alterarVisibilidadeReCyclerView(){
        if (historicoAdapter.itemCount == 0){
            binding.txtListaVazia.visibility = View.VISIBLE
            binding.recyclerHistoricoBasesNumericas.visibility = View.GONE
            binding.buttonApagarHistorico.visibility = View.GONE
        }
        else{
            binding.txtListaVazia.visibility = View.GONE
            binding.recyclerHistoricoBasesNumericas.visibility = View.VISIBLE
            binding.buttonApagarHistorico.visibility = View.VISIBLE
        }
    }

}