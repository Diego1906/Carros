package livroandroid.com.br.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_carros.*
import livroandroid.com.br.R
import livroandroid.com.br.activity.CarroActivity
import livroandroid.com.br.adapter.CarroAdapter
import livroandroid.com.br.domain.Carro
import livroandroid.com.br.domain.CarroService
import livroandroid.com.br.utils.TipoCarro
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class CarrosFragment : BaseFragment() {

    private val tipo: TipoCarro by lazy {
        arguments?.getSerializable("tipo") as TipoCarro
    }

    private lateinit var carros: List<Carro>
    private lateinit var adapter: CarroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // Cria a view do fragment
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retorna a view/res/layout/fragment_carros.xml
        val view = inflater.inflate(R.layout.fragment_carros, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Views
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
        }
    }

    override fun onResume() {
        super.onResume()

        taskCarros()
    }

    private fun taskCarros() {

        // Abre uma thread
        doAsync {

            // Busca os carros
            CarroService.getCarros(tipo)?.let {
                carros = it
            }

//            adapter = CarroAdapter(carros) {
//                onClickCarro(it)
//            }

            adapter = CarroAdapter(carros, ::onClickCarro)

            // Atualiza a lista na UI Thread
            uiThread {

                adapter.let {
                    recyclerView.adapter = it
                    it.notifyDataSetChanged()
                }
            }
        }
    }

    private fun onClickCarro(carro: Carro) {
        // Ao clicar no carroExtras vamos navegar para a tela de detalhes
        activity?.startActivity<CarroActivity>("carroExtras" to carro)
    }
}
