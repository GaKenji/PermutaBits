package com.example.permutabittools.baseNumerica.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.permutabittools.baseNumerica.baseNumericaModel.NumericBase
import com.example.permutabittools.dataBase.ConversoesDAO
import com.example.permutabittools.dataBase.ConversoesDataBase
import com.example.permutabittools.dataBase.PermutaDataBase
import kotlinx.coroutines.launch
import java.lang.Math.pow
import kotlin.math.pow
import kotlin.text.iterator

class BaseNumericaViewModel(): ViewModel() {

    private var origem: NumericBase? = null
    private var destino: NumericBase? = null
    private val algarismos = ArrayList<Int>()

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

    fun calcularEmostrarConversao(baseOrigem: String, baseDestino: String, valor: String): List<String> {
        val passos = mutableListOf<String>()

        var tamanho: Int = valor.length - 1
        var resultadoDecimal: Int = 0

        origem = mapearBases(baseOrigem)
        destino = mapearBases(baseDestino)

        val base = origem!!.raiz

        when(destino?.name){
            "DECIMAL" -> {
                if(origem?.name.equals("HEXADECIMAL")){
                    passos.add("Converter valores HEXADECIMAIS para DECIMAIS\n")
                    for(i in valor){
                        var digito = i.digitToInt(16)
                        passos.add("${i} ===> ${digito}")
                        algarismos.add(digito)
                    }
                }
                else for(i in valor) algarismos.add(i.digitToInt())

                passos.add("\nOrganizar os algarismos da seguinte forma:\n")
                for(i in algarismos){
                    resultadoDecimal += i * potencia(base, tamanho)

                    if(tamanho != 0) passos.add("(${i} X ${base}^${tamanho}) + ")
                    else passos.add("(${i} X ${base}^${tamanho})")

                    tamanho--
                }
                passos.add("\nResultado Final: ${resultadoDecimal}\n")
            }
            else -> {

                if(origem?.name.equals("HEXADECIMAL")){
                    passos.add("Converter valores HEXADECIMAIS para DECIMAIS\n")
                    for(i in valor){
                        var digito = i.digitToInt(16)
                        passos.add("${i} ===> ${digito}")
                        algarismos.add(digito)
                    }
                    passos.add("\nOrganizar os algarismos da seguinte forma:\n")
                    for(i in algarismos){
                        resultadoDecimal += i * potencia(base, tamanho)

                        if(tamanho != 0) passos.add("(${i} X ${base}^${tamanho}) + ")
                        else passos.add("(${i} X ${base}^${tamanho}) = ")

                        tamanho--
                    }
                    passos.add("Decimal = ${resultadoDecimal}\n")
                }
                else if(origem?.name.equals("DECIMAL")){
                    resultadoDecimal = valor.toInt()
                    passos.add("Decimal = ${resultadoDecimal}\n")
                }
                else{
                    for(i in valor) algarismos.add(i.digitToInt())
                    passos.add("\nOrganizar os algarismos da seguinte forma:\n")
                    for(i in algarismos){
                        resultadoDecimal += i * potencia(base, tamanho)

                        if(tamanho != 0) passos.add("(${i} X ${base}^${tamanho}) + ")
                        else passos.add("(${i} X ${base}^${tamanho})")

                        tamanho--
                    }
                    passos.add("\nDecimal = ${resultadoDecimal}\n")
                }

                passos.add("Converta de DECIMAL para a base ${destino?.name}\n")

                var numero = resultadoDecimal
                val restos = mutableListOf<String>()

                while(numero > 0){
                    val quociente = numero/destino!!.raiz
                    val resto = numero % destino!!.raiz
                    val restoString: String

                    if(resto > 9 || resto < 16) restoString = resto.toString(16).uppercase()
                    else restoString = resto.toString().uppercase()

                    restos.add(restoString)
                    passos.add("${numero} / ${destino!!.raiz} = ${quociente} ===> Resto = ${resto}")
                    numero = quociente
                }

                val resultado = restos.reversed().joinToString("")

                passos.add("\nLeia os restos de cima para baixo\n")
                passos.add("Resultado final: ${resultado}")
            }
        }
        return passos
    }

    private fun potencia(num: Int, pot: Int): Int{
        var resultado = 1
        for(i in 1..pot){
            resultado *= num
        }
        return resultado
    }
}