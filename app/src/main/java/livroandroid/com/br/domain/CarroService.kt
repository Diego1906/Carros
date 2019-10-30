package livroandroid.com.br.domain

import android.content.Context
import livroandroid.com.br.R
import livroandroid.com.br.extensions.fromJson
import livroandroid.com.br.utils.TipoCarro

object CarroService {

    private val TAG = "livro"

    // Busca os carros por tipo (clássicos, esportivos ou luxo)
    fun getCarros(context: Context, tipo: TipoCarro): List<Carro> {

        // Este é o arquivo JSON que temos de ler
        val raw = getArquivoRaw(tipo)

        // Abre o arquivo para leitura
        val inputStream = context.resources.openRawResource(raw)
        inputStream.bufferedReader().use {
            // Lê o JSON e cria a lista de carros através da função parserJson(json)
            val json = it.readText()

            // Converte o JSON para List<Carro>
            val carros = fromJson<List<Carro>>(json)
            return carros
        }
    }

    // Retorna o arquivo que temos de ler para o tipo de carro informado
    private fun getArquivoRaw(tipo: TipoCarro) = when (tipo) {
        TipoCarro.classicos -> R.raw.carros_classicos
        TipoCarro.esportivos -> R.raw.carros_esportivos
        else -> R.raw.carros_luxo
    }
}
