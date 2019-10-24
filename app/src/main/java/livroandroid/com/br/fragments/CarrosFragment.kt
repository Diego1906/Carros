package livroandroid.com.br.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import livroandroid.com.br.R
import livroandroid.com.br.utils.TipoCarro

/**
 * A simple [Fragment] subclass.
 */
class CarrosFragment : BaseFragment() {

    private var tipo: TipoCarro = TipoCarro.classicos

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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_carros, container, false)

        val textViewCarrosFragment = view?.findViewById<TextView>(R.id.textCarros)

        // Converte o R.string.xxx em texto
        val tipoString = getString(tipo.string)
        textViewCarrosFragment?.text = "Carros $tipoString"

        return view
    }
}
