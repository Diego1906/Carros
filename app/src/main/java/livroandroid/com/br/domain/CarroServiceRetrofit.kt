package livroandroid.com.br.domain

import livroandroid.com.br.domain.dao.DatabaseManager
import livroandroid.com.br.utils.TipoCarro
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CarroServiceRetrofit {

    // ANTIGO
    // private var BASE_URL = "http://livrowebservices.com.br/rest/carros/"
    private var BASE_URL = "https://carros-springboot.herokuapp.com/api/v1/carros/"
    private var service: CarrosREST
    private val listCarros: List<Carro> = mutableListOf()

    init {
        service = Retrofit.Builder()
            .baseUrl(BASE_URL)
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
}