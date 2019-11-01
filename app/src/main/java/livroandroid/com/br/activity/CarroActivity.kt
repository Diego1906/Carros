package livroandroid.com.br.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.activity_carro_contents.*
import livroandroid.com.br.R
import livroandroid.com.br.domain.Carro
import livroandroid.com.br.extensions.loadUrl
import livroandroid.com.br.extensions.setupToolbar
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.alert

class CarroActivity : BaseActivity() {

    // Inicializa a variável quando a mesma for utilizada pela primeira vez
    private val carro: Carro by lazy {
        intent.getParcelableExtra<Carro>("carroExtras")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro)

        // Seta o nome do carroExtras como título da Toolbar
        setupToolbar(R.id.toolBarCarroActivity, carro.nome, true)

        // Atualiza os dados do carroExtras na tela
        initViews()
    }

    private fun initViews() {
        // Variáveis geradas automaticamente pelo Koltin Extensions (veja import)
        descricao_carro_contents.text = carro.desc
        appBarImg.loadUrl(carro.urlFoto)
    }

    // Adiciona as opções de Salvar e Deletar no menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_carro, menu)
        return true
    }

    // Trata os eventos do menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_editar -> {
                startActivity<CarroFormActivity>("carroExtras" to carro)
                finish()
            }
            R.id.action_deletar -> {
                TODO("Deletar carroExtras")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
