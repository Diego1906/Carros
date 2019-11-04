package livroandroid.com.br.fragments

import kotlinx.android.synthetic.main.fragment_carros.*
import livroandroid.com.br.activity.CarroActivity
import livroandroid.com.br.adapter.CarroAdapter
import livroandroid.com.br.domain.Carro
import livroandroid.com.br.domain.FavoritosService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class FavoritosFragment : CarrosFragment() {

    override fun taskCarros() {
        doAsync {

            // Busca os carros favoritados
            carros = FavoritosService.getCarros()

            uiThread {

                recyclerView.adapter = CarroAdapter(carros, ::onClickCarro)
            }
        }
    }

    override fun onClickCarro(carro: Carro) {
        // Ao clicar no carro vamos navegar para a tela de detalhes
        activity?.startActivity<CarroActivity>("carroExtras" to carro)
    }
}