package com.example.permutabittools.viewModels.ipv4viewModels

import androidx.lifecycle.ViewModel
import com.example.permutabittools.enum.Cidr
import com.example.permutabittools.models.NetworkInfo

class Ipv4CidrViewModel : ViewModel() {
    fun calcularRede(ip: String, cidr: Cidr): NetworkInfo{
        //transforma dados em UInt
        val mascaraUInt = cidrToUInt(cidr.num)
        val ipUInt = ipToUint(ip)
        val redeUInt = getEndRede(ipUInt, mascaraUInt)
        val broadcastUInt = broadcast(redeUInt, mascaraUInt)
        val primeiroHostUInt = primeiroHost(redeUInt, cidr.num)
        val ultimoHostUInt = ultimoHost(broadcastUInt, cidr.num)

        val mascara = uintToIp(mascaraUInt)
        val endRede = uintToIp(redeUInt)
        val broadcast = uintToIp(broadcastUInt)
        val primeiroHost = uintToIp(primeiroHostUInt)
        val ultimoHost = uintToIp(ultimoHostUInt)
        val totalHosts = totalHosts(cidr.num)
        val hostsUtilizaveis = hostsUtilizaveis(cidr.num)
        val classe = classeIp(ipUInt)
        val privado = privado(ipUInt)

        println("Endereço IP: $ip")
        println("Máscara: $mascara")
        println("Endereço de rede: $endRede")
        println("Broadcast: $broadcast")
        println("Primeiro Host: $primeiroHost")
        println("Ultimo Host: $ultimoHost")
        println("Total Host: $totalHosts")
        println("Hosts utilizáveis: $hostsUtilizaveis")
        println("Ip classe: $classe")
        println("Privado: $privado")

        return NetworkInfo(ip, mascara, endRede, broadcast, primeiroHost, ultimoHost,
            totalHosts, hostsUtilizaveis, classe, privado)
    }

    fun verificaCird(endereco: String): Boolean{
        val end = endereco.split(".")
        if(end.size != 4){
            return false
        }else{
            var contador = 0
            for(octeto in end){
                val validaOcteto = validaFormatoNumerico(octeto)
                if(validaOcteto){
                    if(validaNum(octeto.toInt())) contador++
                }
                else return false
            }
            if(contador != 4) return false
        }
        return true
    }

    private fun validaFormatoNumerico (octeto: String): Boolean{
        if(octeto.isNullOrBlank()) return false
        return octeto.toIntOrNull() != null
    }

    private fun validaNum(num: Int): Boolean{
        return if(num >= 0 && num<=255) true
        else false
    }

    private fun cidrToUInt(cidr: Int): UInt {
        require(cidr in 1..32)
        return if (cidr == 32) UInt.MAX_VALUE
        else UInt.MAX_VALUE shl (32 - cidr)
    }

    private fun uintToIp(valor: UInt): String {
        val o1 = (valor shr 24) and 0xFFu
        val o2 = (valor shr 16) and 0xFFu
        val o3 = (valor shr 8) and 0xFFu
        val o4 = valor and 0xFFu

        return "$o1.$o2.$o3.$o4"
    }

    private fun ipToUint(ip: String): UInt{
        val p = ip.split(".")

        return (p[0].toUInt() shl 24) or
                (p[1].toUInt() shl 16) or
                (p[2].toUInt() shl 8) or
                 p[3].toUInt()
    }

    private fun getEndRede(ip: UInt, mascara: UInt): UInt{
        return ip and mascara
    }

    private fun broadcast(rede: UInt, mascara: UInt): UInt {
        return rede or mascara.inv()
    }

    private fun primeiroHost(rede: UInt, cidr: Int): UInt{
        return when(cidr){
            31 -> rede
            32 -> rede
            else -> rede + 1u
        }
    }

    private fun ultimoHost(broadcast: UInt, cidr: Int): UInt{
        return when(cidr){
            31 -> broadcast
            32 -> broadcast
            else -> broadcast - 1u
        }
    }

    private fun totalHosts(cidr: Int): Int {
        return 1 shl (32 - cidr)
    }

    private fun hostsUtilizaveis(cidr:Int):Int{
        return when(cidr){
            31 -> 2
            32 -> 1
            else -> totalHosts(cidr)-2
        }
    }

    private fun classeIp(ip: UInt): String {
        val primeiro = (ip shr 24).toInt()
        return when (primeiro) {
            in 1..126 -> "A"
            127 -> "Loopback"
            in 128..191 -> "B"
            in 192..223 -> "C"
            in 224..239 -> "D (Multicast)"
            else -> "E (Reservado)"
        }
    }

    private fun privado(ip:UInt):Boolean{
        val o1=(ip shr 24).toInt()
        val o2=((ip shr 16) and 0xFFu).toInt()
        return when{
            o1==10 -> true
            o1==172 && o2 in 16..31 -> true
            o1==192 && o2==168 -> true
            else -> false
        }
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