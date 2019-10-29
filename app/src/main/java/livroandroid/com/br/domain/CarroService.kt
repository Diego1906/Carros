package livroandroid.com.br.domain

import android.content.Context
import livroandroid.com.br.utils.TipoCarro

object CarroService {

    // Busca os carros por tipo (clássicos, esportivos ou luxo)
    fun getCarros(context: Context, tipo: TipoCarro): List<Carro> {

        val tipoString = context.getString(tipo.string)

        // Cria um list vazio de carros
        val carros = mutableListOf<Carro>()

        // Cria 20 carros
        for (i in 0..19) {
            val carro = Carro()

            // Nome do carro dinâmico para brincar
            carro.nome = "Carro $tipoString: $i"
            carro.desc = "Desc $i"

            // URL da Foto fixa por enquanto
            carro.urlFoto =
                "http://www.livroandroid.com.br/livro/carros/esportivos/Ferrari_FF.png"
            carros.add(carro)
        }
        return carros
    }
}
