package livroandroid.com.br.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.activity_carro_contents.*
import livroandroid.com.br.R
import livroandroid.com.br.domain.Carro
import livroandroid.com.br.extensions.loadUrl
import livroandroid.com.br.extensions.setupToolbar

class CarroActivity : BaseActivity() {

    // Inicializa a variável quando a mesma for utilizada pela primeira vez
    val carro: Carro by lazy {
        // Lê o carro enviado como parâmetro
        intent.getParcelableExtra<Carro>("carro")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro)

        // Seta o nome do carro como título da Toolbar
        setupToolbar(R.id.toolBarCarroActivity, carro.nome, true)

        // Atualiza os dados do carro na tela
        initViews()
    }

    private fun initViews() {
        // Variáveis geradas automaticamente pelo Koltin Extensions (veja import)
        descricao_carro_contents.text = carro.desc
        appBarImg.loadUrl(carro.urlFoto)
    }
}
