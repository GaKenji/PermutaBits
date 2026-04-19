package com.example.permutabittools.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.permutabittools.R
import com.example.permutabittools.models.ConversoesRepository
import com.example.permutabittools.models.TipoPasso
import com.example.permutabittools.dataBase.ConversoesDataBase
import com.example.permutabittools.dataBase.PermutaDataBase
import com.example.permutabittools.databinding.FragmentCalculoBaseNumericaBinding
import com.example.permutabittools.viewModels.BaseNumericaViewModel
import com.example.permutabittools.viewModels.BaseNumericaViewModelFactory

class CalculoBaseNumerica : Fragment(), View.OnClickListener {

    private var _binding: FragmentCalculoBaseNumericaBinding? = null
    private val binding get() = _binding!!
    private var conversao: ConversoesDataBase? = null
    private lateinit var viewModel: BaseNumericaViewModel

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

        val dao = PermutaDataBase.Companion.getDataBase(requireContext()).conversaoDao()
        val repository = ConversoesRepository(dao)
        val factory = BaseNumericaViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(BaseNumericaViewModel::class.java)

        receberDadosConversao() //Recebo os dados da fragment
        mostrarDadosConversao() //Exibo os dados na parte superior da fragment
        exibirPassoaPasso()
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

    private fun exibirPassoaPasso(){
        //Faz os cálculos e mostro o passo a passo da conversão
        val passos = viewModel.calcularEmostrarConversao(
            conversao!!.baseOrigem,
            conversao!!.baseDestino,
            conversao!!.valorEntrada
        )

        val textoFinal = passos.joinToString("<br><br>") { passo ->

            val texto = getString(passo.id, *passo.args.toTypedArray())
            when (passo.tipoPasso) {
                TipoPasso.TITULO -> "<font color='#B3E5FC'>👉 $texto</font>"
                TipoPasso.EXPLICACAO -> "<font color='#B3E5FC'>🧮 $texto</font>"
                TipoPasso.CALCULO -> texto
                TipoPasso.RESULTADO -> "<br><b>✅ $texto</b>"

            }
        }

        binding.txtPassoapasso.text =
            Html.fromHtml(textoFinal, Html.FROM_HTML_MODE_LEGACY)
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.buttonCopiar -> {
                val clipBoard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val texto = binding.txtPassoapasso.text.toString()

                val clip = ClipData.newPlainText("Passo a passo", texto)
                clipBoard.setPrimaryClip(clip)

                Toast.makeText(requireContext(), R.string.toast_copiar_calculos, Toast.LENGTH_SHORT).show()
            }
        }
    }
}