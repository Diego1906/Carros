package livroandroid.com.br.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_carro.view.*
import livroandroid.com.br.R
import livroandroid.com.br.domain.Carro
import livroandroid.com.br.extensions.loadUrl

// Define o construtor que recebe (carros, onClick)
class CarroAdapter(val carros: List<Carro>, val onClick: (Carro) -> Unit) :
    RecyclerView.Adapter<CarroAdapter.CarrosViewHolder>() {

    // Infla o layout XML do adapter e retorna o ViewHolder com o objeto view inflado armazenado nele
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrosViewHolder {

        // Infla a view do adapter (Converte uma view em XML para um objeto "view")
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.adapter_carro, parent, false)

        // Retorna o ViewHolder que contém todas as views
        val holder = CarrosViewHolder(view)
        return holder
    }

    // Retorna a quantidade de itens na lista para o Sistema Android
    override fun getItemCount(): Int = this.carros.size

    // Faz o bind para atualizar o valor das views com os dados do Carro
    override fun onBindViewHolder(holder: CarrosViewHolder, position: Int) {

        // Recupera o objeto carro na posição X
        val carro = carros[position]

        // O holder.itemView contém as variáveis definidas no XML do adapter (lembre do nome de cada id)
        with(holder.itemView) {
            // Atualiza os dados do carro
            nomeCarroAdapter.text = carro.nome

            // Deixa visível o progressbar
            progressCarroAdapter.visibility = View.VISIBLE

            // Faz o download da foto e mostra o progressbar
            imgCarroAdapter.loadUrl(carro.urlFoto, progressCarroAdapter)

            // Adiciona o evento de clique na linha
            setOnClickListener {
                onClick.invoke(carro)
            }
        }
    }

    // ViewHolder fica vazio pois usamos o import do Android Kotlin Extensions
    class CarrosViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
}