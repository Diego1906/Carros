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
import org.jetbrains.anko.startActivity

class CarrosFragment : BaseFragment() {

    private var tipo: TipoCarro = TipoCarro.classicos
    private var carros = listOf<Carro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lê o parâmetro tipo enviado ( clássicos, esportivos ou luxo )
        tipo = arguments?.getSerializable("tipo") as TipoCarro
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
        // Busca os carros
        context?.let {
            this.carros = CarroService.getCarros(it, tipo)
        }

        // Atualiza a lista
        recyclerView.adapter = CarroAdapter(carros) {
            onClickCarro(it)
        }
    }

    private fun onClickCarro(carro: Carro) {
        // Ao clicar no carro vamos navegar para a tela de detalhes
        activity?.startActivity<CarroActivity>("carro" to carro)
    }
}
