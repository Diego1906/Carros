package livroandroid.com.br.domain

import android.util.Base64
import livroandroid.com.br.domain.dao.DatabaseManager
import livroandroid.com.br.extensions.fromJson
import livroandroid.com.br.utils.HttpHelper
import livroandroid.com.br.utils.TipoCarro
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import livroandroid.com.br.utils.BASE_URL

object CarroServiceRetrofit {

    private var service: CarrosREST
    private val listCarros: List<Carro> = mutableListOf()

    init {
        service = Retrofit.Builder()
            .baseUrl(BASE_URL.CARROS.CARROS)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarrosREST::class.java)
    }

    // Busca os carros por tipo (clássicos, esportivos ou luxo)
    fun getCarros(tipo: TipoCarro) = service.getCarros(tipo.name).execute().body() ?: listCarros

    // Salva um carro
    fun save(carro: Carro) = service.save(carro).execute().body()

    // Deleta um carro
    fun delete(carro: Carro): Response? {
        val response: Response? = service.delete(carro.id).execute().body()

        response?.let {
            if (it.isOK()) {
                val dao = DatabaseManager.getCarroDAO()
                dao.delete(carro)
            }
        }
        return response
    }

    // POST
    fun postFoto(file: File): Response {

        // Converte para Base64
        val bytes = file.readBytes()
        val base64 = Base64.encodeToString(bytes, Base64.NO_WRAP)
        val params = mapOf("fileName" to file.name, "base64" to base64)

        val json = HttpHelper.postForm(BASE_URL.UPLOAD, params)
        val response = fromJson<Response>(json.second)
        response.code = json.first
        return response
    }
}