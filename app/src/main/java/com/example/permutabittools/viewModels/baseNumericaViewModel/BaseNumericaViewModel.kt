package com.example.permutabittools.viewModels.baseNumericaViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.permutabittools.R
import com.example.permutabittools.dataBase.ConversoesDataBase
import com.example.permutabittools.repository.ConversoesRepository
import com.example.permutabittools.enum.NumericBase
import com.example.permutabittools.models.Passo
import com.example.permutabittools.enum.TipoPasso
import kotlinx.coroutines.launch
import kotlin.text.iterator

class BaseNumericaViewModel(private val repository: ConversoesRepository): ViewModel() {

    private var origem: NumericBase? = null
    private var destino: NumericBase? = null
    private val algarismos = ArrayList<Int>()

    val historico = repository.historico

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
            NumericBase.BINARIO -> valorEntrada.toLong(2)
            NumericBase.OCTAL -> valorEntrada.toLong(8)
            NumericBase.DECIMAL -> valorEntrada.toLong(10)
            NumericBase.HEXADECIMAL -> valorEntrada.toLong(16)
        }

        return when(baseDestino!!){
            NumericBase.BINARIO -> decimal.toString(2)
            NumericBase.OCTAL -> decimal.toString(8)
            NumericBase.DECIMAL -> decimal.toString(10)
            NumericBase.HEXADECIMAL -> decimal.toString(16).uppercase()
        }

    }

    fun calcularEmostrarConversao(baseOrigem: String, baseDestino: String, valor: String): List<Passo> {
        val passos = mutableListOf<Passo>()
        var tamanho = valor.length - 1
        var resultadoDecimal = 0

        origem = mapearBases(baseOrigem)
        destino = mapearBases(baseDestino)

        val base = origem!!.raiz

        when (destino?.name) {
            "DECIMAL" -> {
                if (origem?.name == "HEXADECIMAL") {
                    passos.add(
                        Passo(R.string.passo_hex_para_decimal, tipoPasso = TipoPasso.TITULO)
                    )
                    for (i in valor) {
                        val digito = i.digitToInt(16)
                        passos.add(
                            Passo(
                                R.string.passo_digito_convertido,
                                listOf(i.toString(), digito),
                                TipoPasso.CALCULO
                            )
                        )
                        algarismos.add(digito)
                    }
                } else {
                    for (i in valor) algarismos.add(i.digitToInt())
                }
                passos.add(
                    Passo(R.string.passo_multiplicacao, tipoPasso = TipoPasso.EXPLICACAO)
                )

                for (i in algarismos) {
                    resultadoDecimal += i * potencia(base, tamanho)
                    val resId = if (tamanho != 0)
                        R.string.passo_potencia_soma
                    else
                        R.string.passo_potencia

                    passos.add(
                        Passo(
                            resId,
                            listOf(i, base, tamanho),
                            TipoPasso.CALCULO
                        )
                    )
                    tamanho--
                }

                passos.add(
                    Passo(
                        R.string.resultado_decimal,
                        listOf(resultadoDecimal),
                        TipoPasso.RESULTADO
                    )
                )
            }

            else -> {

                if (origem?.name == "HEXADECIMAL") {
                    passos.add(
                        Passo(R.string.passo_hex_para_decimal, tipoPasso = TipoPasso.TITULO)
                    )
                    for (i in valor) {
                        val digito = i.digitToInt(16)
                        passos.add(
                            Passo(
                                R.string.passo_digito_convertido,
                                listOf(i.toString(), digito),
                                TipoPasso.CALCULO
                            )
                        )

                        algarismos.add(digito)
                    }
                    passos.add(
                        Passo(R.string.passo_multiplicacao, tipoPasso = TipoPasso.EXPLICACAO)
                    )
                    for (i in algarismos) {
                        resultadoDecimal += i * potencia(base, tamanho)
                        val resId = if (tamanho != 0)
                            R.string.passo_potencia_soma
                        else
                            R.string.passo_potencia

                        passos.add(
                            Passo(resId, listOf(i, base, tamanho), TipoPasso.CALCULO)
                        )
                        tamanho--
                    }

                    passos.add(
                        Passo(
                            R.string.resultado_parcial_decimal,
                            listOf(resultadoDecimal),
                            TipoPasso.RESULTADO
                        )
                    )
                }

                else if (origem?.name == "DECIMAL") {
                    resultadoDecimal = valor.toInt()
                    passos.add(
                        Passo(
                            R.string.resultado_parcial_decimal,
                            listOf(resultadoDecimal),
                            TipoPasso.RESULTADO
                        )
                    )
                }

                else {
                    for (i in valor) algarismos.add(i.digitToInt())
                    passos.add(
                        Passo(R.string.passo_multiplicacao, tipoPasso = TipoPasso.EXPLICACAO)
                    )

                    for (i in algarismos) {

                        resultadoDecimal += i * potencia(base, tamanho)

                        val resId = if (tamanho != 0)
                            R.string.passo_potencia_soma
                        else
                            R.string.passo_potencia

                        passos.add(
                            Passo(
                                resId,
                                listOf(i, base, tamanho),
                                TipoPasso.CALCULO
                            )
                        )

                        tamanho--
                    }

                    passos.add(
                        Passo(
                            R.string.resultado_parcial_decimal,
                            listOf(resultadoDecimal),
                            TipoPasso.RESULTADO
                        )
                    )
                }

                passos.add(
                    Passo(R.string.passo_divisoes, tipoPasso = TipoPasso.EXPLICACAO)
                )

                var numero = resultadoDecimal
                val restos = mutableListOf<String>()

                while (numero > 0) {

                    val quociente = numero / destino!!.raiz
                    val resto = numero % destino!!.raiz

                    if (resto > 9 && resto < 16) {

                        val restoString = resto.toString(16).uppercase()

                        passos.add(
                            Passo(
                                R.string.passo_divisao_hex,
                                listOf(numero, destino!!.raiz, quociente, resto, restoString),
                                TipoPasso.CALCULO
                            )
                        )

                        restos.add(restoString)
                    } else {

                        passos.add(
                            Passo(
                                R.string.passo_divisao,
                                listOf(numero, destino!!.raiz, quociente, resto),
                                TipoPasso.CALCULO
                            )
                        )

                        restos.add(resto.toString())
                    }
                    numero = quociente
                }

                val resultado = restos.reversed().joinToString("")
                passos.add(
                    Passo(R.string.passo_leitura_restos, tipoPasso = TipoPasso.EXPLICACAO)
                )
                passos.add(
                    Passo(
                        R.string.resultado_final_string,
                        listOf(resultado),
                        TipoPasso.RESULTADO
                    )
                )
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

    fun inserir(conversao: ConversoesDataBase){
        viewModelScope.launch {
            repository.inserir(conversao)
        }
    }

    fun deleteItem(conv: ConversoesDataBase){
        viewModelScope.launch {
            repository.deletarItem(conv)
        }
    }

    fun deleteTudo(){
        viewModelScope.launch {
            repository.deletar()
        }
    }
}