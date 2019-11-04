package livroandroid.com.br.domain

import livroandroid.com.br.domain.dao.DatabaseManager
import livroandroid.com.br.extensions.fromJson
import livroandroid.com.br.extensions.toJson
import livroandroid.com.br.utils.HttpHelper
import livroandroid.com.br.utils.TipoCarro

object CarroServiceOkHttp {

    // ANTIGO
    // private var BASE_URL = "http://livrowebservices.com.br/rest/carros"
    private var BASE_URL = "https://carros-springboot.herokuapp.com/api/v1/carros"

    // Busca os carros por tipo (clássicos, esportivos ou luxo)
    fun getCarros(tipo: TipoCarro): List<Carro> {
        val url = "$BASE_URL/tipo/${tipo.name}"
        val json = HttpHelper.get(url)
        val carros = fromJson<List<Carro>>(json)
        return carros
    }

    // Salva um carroExtras
    fun save(carro: Carro): Response {
        // Faz POST  do JSON carroExtras
        val json = HttpHelper.post(BASE_URL, carro.toJson())
        val response = fromJson<Response>(json)
        return response
    }

    // Deleta um carroExtras
    fun delete(carro: Carro): Response {
        val url = "$BASE_URL/${carro.id}"
        val json = HttpHelper.delete(url)
        val response = fromJson<Response>(json)

        if (response.isOK()) {
            // Se removeu do servidor, remove dos favoritos
            val dao = DatabaseManager.getCarroDAO()
            dao.delete(carro)
        }
        return response
    }
}