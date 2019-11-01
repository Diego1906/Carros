package livroandroid.com.br.domain

import livroandroid.com.br.utils.TipoCarro
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarroServiceRetrofit {

    private var BASE_URL = "http://livrowebservices.com.br/rest/carros/"

    private var retrofit : Retrofit
    private val listCarros: List<Carro> = mutableListOf()

    init {
         retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService() =  retrofit.create(CarrosREST::class.java)

    // Busca os carros por tipo (clássicos, esportivos ou luxo)
    fun getCarros(tipo: TipoCarro) =  getService().getCarros(tipo.name).execute().body() ?: listCarros

    // Salva um carro
    fun save(carro: Carro) = getService().save(carro).execute()?.body()

    // Deleta um carro
    fun delete(carro: Carro) = getService().delete(carro.id).execute()?.body()
}