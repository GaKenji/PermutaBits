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
        var tamanho: Int = valor.length - 1

        val algarismos = ArrayList<Int>()
        var resultado: Int = 0

        origem = mapearBases(baseOrigem)
        destino = mapearBases(baseDestino)

        val base = origem!!.raiz

        println("BASE ENTRADA: ${origem?.name} ${origem?.raiz}")
        println("BASE SAÌDA: ${destino?.name} ${destino?.raiz}")

        when(destino?.name){
            "DECIMAL" -> {
                if(origem?.name.equals("HEXADECIMAL")){
                    println("Converter valores Hexadecimais para decimais")
                    for(i in valor){
                        print("${i} ===> ")
                        algarismos.add(i.digitToInt(16))
                        print("${i.digitToInt(16)}\n")
                    }
                }
                else{
                    for(i in valor){
                        algarismos.add(i.digitToInt())
                    }
                    println("Outros valores")
                    println(algarismos)
                }

                println("Organizar os algarismos da seguinte forma:")
                for(i in algarismos){
                    //println("Algarismo: ${i}  ======>  Expoente: ${tamanho}")
                    resultado += i * potencia(base, tamanho)
                    if(tamanho != 0)
                        print("(${i} X ${base}^${tamanho}) + ")
                    else print("(${i} X ${base}^${tamanho}) = ")
                    tamanho--
                }
                print("${resultado}\n")
            }
            else -> println("A base de destino NÃO É DECIMAL!!")
        }
    }

    private fun potencia(num: Int, pot: Int): Int{
        var resultado = 1
        for(i in 1..pot){
            resultado *= num
        }
        return resultado
    }
}