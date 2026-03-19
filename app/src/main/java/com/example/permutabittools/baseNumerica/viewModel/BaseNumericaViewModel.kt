package com.example.permutabittools.baseNumerica.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.permutabittools.baseNumerica.baseNumericaModel.NumericBase
import com.example.permutabittools.dataBase.ConversoesDAO
import com.example.permutabittools.dataBase.ConversoesDataBase
import com.example.permutabittools.dataBase.PermutaDataBase
import kotlinx.coroutines.launch

class BaseNumericaViewModel(): ViewModel() {

    private var origem: NumericBase? = null
    private var destino: NumericBase? = null
    fun mapearBases(select: String): NumericBase?{
        //Mapeamento das bases numéricas para os spinners
        //Trata os itens que podem ser clicados
        //Descarta a acão do click do primeiro item "De..." e "Para..."
        return when(select){
            "Binário", "BINARIO" -> NumericBase.BINARIO
            "Octal", "OCTAL" -> NumericBase.OCTAL
            "Decimal", "DECIMAL" -> NumericBase.DECIMAL
            "Hexadecimal", "HEXADECIMAL" -> NumericBase.HEXADECIMAL
            else -> null
        }
    }

    fun converter(valorEntrada: String, baseOrigem: NumericBase?, baseDestino: NumericBase?): String{

        val decimal = when (baseOrigem!!){
            /*Primeiro passo da conversão
            Pega o valor da textView e depois converte para decimal
            Pegua a string valor que está na base radix e devolve um decimal equivalente*/
            NumericBase.BINARIO -> valorEntrada.toInt(2)
            NumericBase.OCTAL -> valorEntrada.toInt(8)
            NumericBase.DECIMAL -> valorEntrada.toInt(10)
            NumericBase.HEXADECIMAL -> valorEntrada.toInt(16)
        }

        return when(baseDestino!!){
            NumericBase.BINARIO -> decimal.toString(2)
            NumericBase.OCTAL -> decimal.toString(8)
            NumericBase.DECIMAL -> decimal.toString(10)
            NumericBase.HEXADECIMAL -> decimal.toString(16).uppercase()
        }

    }

    fun calcularEmostrarConversao(baseOrigem: String, baseDestino: String, valor: String) {
        val tamanho = valor.length
        var resultado: Int = 0

        origem = mapearBases(baseOrigem)
        destino = mapearBases(baseDestino)

        println("BASE ENTRADA: ${origem?.name} ${origem?.raiz}")
        println("BASE SAÌDA: ${destino?.name} ${destino?.raiz}")
        println("VALOR DE ENTRADA: ${valor}")
        println("TAMANHO = ${tamanho}")

    }
}