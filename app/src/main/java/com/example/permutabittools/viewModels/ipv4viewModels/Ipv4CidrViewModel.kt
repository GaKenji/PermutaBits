package com.example.permutabittools.viewModels.ipv4viewModels

import androidx.lifecycle.ViewModel
import com.example.permutabittools.enum.Cidr

class Ipv4CidrViewModel : ViewModel() {

    private lateinit var endereco: String
    private lateinit var cidr: Cidr
    fun verificaCird(endereco: String): Boolean{
        val end = endereco.split(".")
        if(end.size != 4){
            return false
        }else{
            var contador: Int = 0
            for(octeto in end){
                var validaOcteto = validaFormatoNumerico(octeto)
                if(validaOcteto){
                    if(validaNum(octeto.toInt())){
                        contador++
                    }
                }
                else{
                    return false
                }
            }
            if(contador != 4) return false
        }
        return true
    }

    fun calcularRede(endereco: String, cidr: Cidr){
        this.endereco = endereco
        this.cidr = cidr

        val listaOcteto = mutableListOf<Int>()
        var octetos = endereco.split(".")

        for(o in octetos) listaOcteto.add(o.toInt())
        println(listaOcteto)
    }

    private fun validaFormatoNumerico (octeto: String): Boolean{
        if(octeto.isNullOrBlank()) return false
        return octeto.toIntOrNull() != null
    }

    private fun validaNum(num: Int): Boolean{
        if(num >= 0 && num<=255) return true
        else return false
    }

    fun mapearCidr(cidr: String): Cidr{
        return when(cidr){
            "/1" -> Cidr.UM
            "/2" -> Cidr.DOIS
            "/3" -> Cidr.TRES
            "/4" -> Cidr.QUATRO
            "/5" -> Cidr.CINCO
            "/6" -> Cidr.SEIS
            "/7" -> Cidr.SETE
            "/8" -> Cidr.OITO
            "/9" -> Cidr.NOVE
            "/10" -> Cidr.DEZ
            "/11" -> Cidr.ONZE
            "/12" -> Cidr.DOZE
            "/13" -> Cidr.TREZE
            "/14" -> Cidr.CATORZE
            "/15" -> Cidr.QUINZE
            "/16" -> Cidr.DEZESSEIS
            "/17" -> Cidr.DEZESSETE
            "/18" -> Cidr.DEZOITO
            "/19" -> Cidr.DEZENOVE
            "/20" -> Cidr.VINTE
            "/21" -> Cidr.VINTE_E_UM
            "/22" -> Cidr.VINTE_E_DOIS
            "/23" -> Cidr.VINTE_E_TRES
            "/24" -> Cidr.VINTE_E_QUATRO
            "/25" -> Cidr.VINTE_E_CINCO
            "/26" -> Cidr.VINTE_E_SEIS
            "/27" -> Cidr.VINTE_E_SETE
            "/28" -> Cidr.VINTE_E_OITO
            "/29" -> Cidr.VINTE_E_NOVE
            "/30" -> Cidr.TRINTA
            "/31" -> Cidr.TRINTA_E_UM
            "/32" -> Cidr.TRINTA_E_DOIS
            else -> throw IllegalArgumentException("Cidr INválido")
        }
    }
}