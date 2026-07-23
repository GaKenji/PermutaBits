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
import androidx.lifecycle.ViewModelProvider
import com.example.permutabittools.R
import com.example.permutabittools.databinding.FragmentIpv4CidrBinding
import com.example.permutabittools.enum.Cidr
import com.example.permutabittools.models.NetworkInfo
import com.example.permutabittools.viewModels.ipv4viewModels.Ipv4CidrViewModel

class Ipv4CidrFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentIpv4CidrBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: Ipv4CidrViewModel
    private var cidr: Cidr? = null
    private var networkInfo: NetworkInfo? = null

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

    private fun exibeResultados(){
        binding.textCamposRespostaSubnet.text = (
                "${getString(R.string.enderecoIp)}" + "\n" + "\n" + "\n" +
                        "${getString(R.string.mascara)}" + "\n" + "\n" +
                        "${getString(R.string.enderecoRede)}" + "\n" + "\n" +
                        "${getString(R.string.broadcast)}" + "\n" + "\n" +
                        "${getString(R.string.primeiroHost)}" + "\n" + "\n" +
                        "${getString(R.string.ultimoHost)}" + "\n" + "\n" +
                        "${getString(R.string.totalHost)}" + "\n" + "\n" +
                        "${getString(R.string.hostsUtilizaveis)}" + "\n" + "\n" +
                        "${getString(R.string.classe)}" + "\n" + "\n" +
                        "${getString(R.string.privado)}"
                )
        binding.textRespostaSubnet.text = (
                "${networkInfo!!.ip}" + "\n" + "\n" + "\n" +
                        "${networkInfo!!.mascara}" + "\n" + "\n" +
                        "${networkInfo!!.endRede}" + "\n" + "\n" +
                        "${networkInfo!!.broadcast}" + "\n" + "\n" +
                        "${networkInfo!!.primeiroHost}" + "\n" + "\n" +
                        "${networkInfo!!.ultimoHost}" + "\n" + "\n" +
                        "${networkInfo!!.totalHosts}" + "\n" + "\n" +
                        "${networkInfo!!.hostsUtilizaveis}" + "\n" + "\n" +
                        "${networkInfo!!.classeIp}" + "\n" + "\n" +
                        "${networkInfo!!.privado}"
                )
    }

    private fun textoCopiado(): String{
        return "${getString(R.string.enderecoIp)}: ${networkInfo!!.ip}" + "\n" +
                "${getString(R.string.mascara)}: ${networkInfo!!.mascara}" + "\n" +
                "${getString(R.string.enderecoRede)}: ${networkInfo!!.endRede}"+ "\n" +
                "${getString(R.string.broadcast)}: ${networkInfo!!.broadcast}"+ "\n" +
                "${getString(R.string.primeiroHost)}: ${networkInfo!!.primeiroHost}"+ "\n" +
                "${getString(R.string.ultimoHost)}: ${networkInfo!!.ultimoHost}"+ "\n" +
                "${getString(R.string.totalHost)}: ${networkInfo!!.totalHosts}"+ "\n" +
                "${getString(R.string.hostsUtilizaveis)}: ${networkInfo!!.hostsUtilizaveis}"+ "\n" +
                "${getString(R.string.classe)}: ${networkInfo!!.classeIp}"+ "\n" +
                "${getString(R.string.privado)}: ${networkInfo!!.privado}"
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
                this.networkInfo = viewModel.calcularRede(endereco, cidr!!)

                //Exibe o resultado da conversão
                exibeResultados()
            }

            R.id.btnCopiarSubnet -> {
                val clipBoard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                if(networkInfo == null){
                    Toast.makeText(requireContext(), R.string.network_info_texto_nulo, Toast.LENGTH_SHORT).show()
                    return
                }
                val texto = textoCopiado()

                val clip = ClipData.newPlainText("Passo a passo", texto)
                clipBoard.setPrimaryClip(clip)

                Toast.makeText(requireContext(), R.string.network_info_copiado, Toast.LENGTH_SHORT).show()
            }
        }
        esconderTeclado()
    }

}