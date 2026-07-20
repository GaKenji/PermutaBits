package com.example.permutabittools.ui.Ipv4Fragments

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.example.permutabittools.R
import com.example.permutabittools.databinding.FragmentIpv4CidrBinding
import com.example.permutabittools.enum.Cidr
import com.example.permutabittools.viewModels.ipv4viewModels.Ipv4CidrViewModel

class Ipv4CidrFragment : Fragment(), View.OnClickListener {
    companion object {
        fun newInstance() = Ipv4CidrFragment()
    }

    private var _binding: FragmentIpv4CidrBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: Ipv4CidrViewModel
    private var cidr: Cidr? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentIpv4CidrBinding.inflate(layoutInflater)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(Ipv4CidrViewModel::class.java)

        carregarCIDR()

        binding.exposedDropDownCIDR.setOnItemClickListener{parent, _, position, _ ->
            val selecionado = parent.getItemAtPosition(position).toString()
            cidr = viewModel.mapearCidr(selecionado)
        }
        binding.btnCalcularSubnet.setOnClickListener(this)
        binding.btnCopiarSubnet.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        carregarCIDR()
        binding.textRespostaSubnet.setText(" ")
        binding.exposedDropDownCIDR.setText("", false)
        cidr = null
    }

    private fun esconderTeclado(){
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        requireActivity().currentFocus?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            it.clearFocus()
        }
    }

    private fun carregarCIDR(){
        val listaCIDR = listOf("/1","/2","/3","/4","/5","/6","/7","/8","/9","/10","/11",
            "/12","/13","/14","/15","/16","/17","/18","/19","/20","/21","/22","/23","/24","/25",
            "/26","/27", "/28","/29","/30","/31","/32")
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, listaCIDR)
        binding.exposedDropDownCIDR.setAdapter(adapter)
    }

    private fun alertarCidr(mensagem: String){
        AlertDialog.Builder(requireContext()).
        setMessage(mensagem).
        setNeutralButton(R.string.oK, null).
        show()
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnCalcularSubnet ->{
                val endereco = binding.editTextInputIpv4.text.toString().trim()
                //pega o endereço de ip que o usuário digita

                val validaEndereco = viewModel.verificaCird(endereco)
                //verifica se o endereço digitado é válido

                if(!validaEndereco){
                    //Se o endereço for inválido, o usuário é notificado
                    alertarCidr(getString(R.string.ip_invalido))
                    return
                }

                else if(cidr == null){
                    //Se o usuário não selecionou um CIDR, o usuário é notificado a selecionar um
                    alertarCidr(getString(R.string.selecione_cidr))
                    return
                }

                //Se o endereço estiver correto, realizamos a conversão
                //Pegamos o IP e o CIDR, mandamos para a ViewModel e ela retorna um Network Info com todas as informações
                val infoNetwork = viewModel.calcularRede(endereco, cidr!!)

                //Exibe o resultado da conversão
                binding.textRespostaSubnet.text = HtmlCompat.fromHtml(
                    """
                            <font color='#B3E5FC'>${getString(R.string.enderecoIp)}:</font> ${infoNetwork.ip}<br><br>
                            <font color='#B3E5FC'>${getString(R.string.mascara)}:</font> ${infoNetwork.mascara}<br><br><br>
                            <font color='#B3E5FC'>${getString(R.string.enderecoRede)}:</font> ${infoNetwork.endRede}<br><br>
                            <font color='#B3E5FC'>${getString(R.string.broadcast)}:</font> ${infoNetwork.broadcast}<br><br>
                            <font color='#B3E5FC'>${getString(R.string.primeiroHost)}:</font> ${infoNetwork.primeiroHost}<br><br>
                            <font color='#B3E5FC'>${getString(R.string.ultimoHost)}:</font> ${infoNetwork.ultimoHost}<br><br>
                            <font color='#B3E5FC'>${getString(R.string.totalHost)}:</font> ${infoNetwork.totalHosts}<br><br>
                            <font color='#B3E5FC'>${getString(R.string.hostsUtilizaveis)}:</font> ${infoNetwork.hostsUtilizaveis}<br><br>
                            <font color='#B3E5FC'>${getString(R.string.classe)}:</font> ${infoNetwork.classeIp}<br><br>
                            <font color='#B3E5FC'>${getString(R.string.privado)}:</font> ${infoNetwork.privado}
                            """.trimIndent(),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            }

            R.id.btnCopiarSubnet -> {
                val clipBoard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val texto = binding.textRespostaSubnet.text.toString()

                val clip = ClipData.newPlainText("Passo a passo", texto)
                clipBoard.setPrimaryClip(clip)

                Toast.makeText(requireContext(), R.string.network_info_copiado, Toast.LENGTH_SHORT).show()
            }
        }
        esconderTeclado()
    }

}