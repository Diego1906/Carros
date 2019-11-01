package livroandroid.com.br.domain

import livroandroid.com.br.utils.TipoCarro
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CarroService {

    private var BASE_URL = "http://livrowebservices.com.br/rest/carros"
    private var service: CarrosREST

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(CarrosREST::class.java)
    }

    // Busca os carros por tipo (clássicos, esportivos ou luxo)
    fun getCarros(tipo: TipoCarro): List<Carro>? {

        val call = service.getCarros(tipo.name)
        val carros = call.execute().body()
        return carros
    }

    // Salva um carro
    fun save(carro: Carro): Response? {
        val call = service.save(carro)
        val response = call.execute().body()
        return response
    }

    // Deleta um carro
    fun delete(carro: Carro): Response? {
        val call = service.delete(carro.id)
        val response = call.execute().body()
        return response
    }
}