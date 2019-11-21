package livroandroid.com.br.domain

import android.util.Base64
import livroandroid.com.br.domain.dao.DatabaseManager
import livroandroid.com.br.extensions.fromJson
import livroandroid.com.br.extensions.toJson
import livroandroid.com.br.utils.HttpHelper
import livroandroid.com.br.utils.TipoCarro
import livroandroid.com.br.utils.BASE_URL
import java.io.File

object CarroServiceOkHttp {

    // Busca os carros por tipo (clássicos, esportivos ou luxo)
    fun getCarros(tipo: TipoCarro): List<Carro> {

        val url = "${BASE_URL.CARROS.TIPO}${tipo.name}"
        val pair = HttpHelper.get(url)
        val json = pair.second
        val carros = fromJson<List<Carro>>(json)
        return carros
    }

    // Salva um carro
    fun save(carro: Carro): Response {

        var novoCarro: CarroSemId? = null

        if (carro.id == 0.toLong()) {

            novoCarro = CarroSemId(
                tipo = carro.tipo,
                nome = carro.nome,
                descricao = carro.descricao,
                urlFoto = carro.urlFoto
            )
        }

        val jsonCarro = (novoCarro ?: carro).toJson()

        // Faz POST  do JSON carro
        val pair = HttpHelper.post(BASE_URL.CARROS.CARROS, jsonCarro)
        val json = pair.second
        val response = fromJson<Response>(json)
        response.code = pair.first
        return response
    }

    // Deleta um carro
    fun delete(carro: Carro): Response {

        val url = "${BASE_URL.CARROS.CARROS}${carro.id}"
        val pair = HttpHelper.delete(url)
        val json = pair.second
        val response = fromJson<Response>(json)
        response.code = pair.first

        if (response.isCodeSucess()) {
            // Se removeu do servidor, remove dos favoritos
            val dao = DatabaseManager.getCarroDAO()
            dao.delete(carro)
        }
        return response
    }

    // POST
    fun postFoto(file: File): Response {

        // Converte para Base64
        val bytes = file.readBytes()
        val base64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
        val params = mapOf("fileName" to file.name, "mimeType" to "image/png", "base64" to base64)
        val pair = HttpHelper.post(BASE_URL.UPLOAD, params.toJson())
        val json = pair.second
        val response = fromJson<Response>(json)
        response.code = pair.first

        return response
    }
}