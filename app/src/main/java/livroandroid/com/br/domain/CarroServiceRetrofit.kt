package livroandroid.com.br.domain

import livroandroid.com.br.utils.TipoCarro
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CarroServiceRetrofit {

    private var BASE_URL = "http://livrowebservices.com.br/rest/carros/"
    private var service: CarrosREST

    private val listCarros: List<Carro> = mutableListOf()

    init {
         val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(CarrosREST::class.java)
    }


    // Busca os carros por tipo (clássicos, esportivos ou luxo)
    fun getCarros(tipo: TipoCarro) =  service.getCarros(tipo.name).execute().body() ?: listCarros

    // Salva um carro
    fun save(carro: Carro) = service.save(carro).execute()?.body()

    // Deleta um carro
    fun delete(carro: Carro) = service.delete(carro.id).execute()?.body()
}