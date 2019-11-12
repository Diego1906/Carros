package livroandroid.com.br.activity

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_carro.*
import kotlinx.android.synthetic.main.activity_carro_contents.*
import livroandroid.com.br.R
import livroandroid.com.br.domain.Carro
import livroandroid.com.br.domain.CarroServiceOkHttp
import livroandroid.com.br.domain.FavoritosService
import livroandroid.com.br.extensions.loadUrl
import livroandroid.com.br.extensions.setupToolbar
import livroandroid.com.br.fragments.MapaFragment
import org.jetbrains.anko.*

class CarroActivity : BaseActivity() {

    // Inicializa a variável quando a mesma for utilizada pela primeira vez
    private val carro: Carro by lazy {
        intent.getParcelableExtra<Carro>("carroExtras")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro)

        // Seta o nome do carro como título da Toolbar
        setupToolbar(R.id.toolBarCarroActivity, carro.nome, true)

        // Atualiza os dados do carro na tela
        initViews()

        // Variável gerada automaticamente pelo Kotlin Extensions
        fab.setOnClickListener {
            onClickFavoritar(carro)
        }
    }

    private fun initViews() {
        // Variáveis do xml geradas automaticamente pelo Koltin Extensions (veja import)
        descricao_carro_contents.text = carro.descricao
        appBarImg.loadUrl(carro.urlFoto)

        // Foto do Carro (pequena com transparência)
        img.loadUrl(carro.urlFoto)

        // Toca o vídeo
        imgPlayVideo.setOnClickListener {
            carro.urlVideo?.let {
                startActivity(
                    Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(Uri.parse(it), "video/*")
                    })
            }
        }

        // Adiciona o fragment do Mapa
        val mapaFragment = MapaFragment(this)
        mapaFragment.arguments = intent.extras

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mapaFragment, mapaFragment)
            .commit()
    }

    // Adiciona ou Remove o carro dos Favoritos
    private fun onClickFavoritar(carro: Carro) {

        doAsync {

            val favoritado = FavoritosService.favoritar(carro)

            uiThread {

                // Alerta de sucesso
                toast(
                    if (favoritado) R.string.msg_carro_favoritado
                    else R.string.msg_carro_desfavoritado
                )

                // Atualiza cor do botão FAB
                setFavoriteColor(favoritado)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        taskUpdateFavoritoColor()
    }

    // Busca no banco se o carro está favoritado e atualiza a cor do FAB
    private fun taskUpdateFavoritoColor() {

        doAsync {

            val favorito = FavoritosService.isFavotiro(carro)

            uiThread {

                setFavoriteColor(favorito)
            }
        }
    }

    // Desenha a cor do FAB conforme está favoritado ou não
    fun setFavoriteColor(favorito: Boolean) {
        // Troca a cor conforme o status dos favoritos
        val fundo = ContextCompat.getColor(
            this,
            if (favorito) R.color.favorito_on
            else R.color.favorito_off
        )

        val cor = ContextCompat.getColor(
            this,
            if (favorito) R.color.yellow
            else R.color.favorito_on
        )

        fab.backgroundTintList = ColorStateList(arrayOf(intArrayOf(0)), intArrayOf(fundo))
        fab.setColorFilter(cor)
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
                alert(R.string.msg_confirma_excluir_carro, R.string.app_name) {
                    positiveButton(R.string.sim) {
                        // Confirmou o excluir
                        taskExcluir()
                    }
                    negativeButton(R.string.nao) {
                        // Não confirmou
                    }
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Exclui um carro do servidor
    fun taskExcluir() {

        doAsync {

            // val response = CarroServiceRetrofit.delete(carro)
            val response = CarroServiceOkHttp.delete(carro)

            uiThread {

                toast(response.msg.toString())
                finish()
            }
        }
    }
}
