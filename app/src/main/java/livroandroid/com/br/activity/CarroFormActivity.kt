package livroandroid.com.br.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_carro_form.*
import kotlinx.android.synthetic.main.activity_carro_form_contents.*
import livroandroid.com.br.R
import livroandroid.com.br.domain.Carro
import livroandroid.com.br.domain.CarroService
import livroandroid.com.br.extensions.loadUrl
import livroandroid.com.br.extensions.setupToolbar
import livroandroid.com.br.utils.TipoCarro
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class CarroFormActivity : BaseActivity() {

    // O carroExtras pode ser nulo no caso de um Novo Carro
    val carroExtras: Carro? by lazy {
        intent.getParcelableExtra<Carro>("carroExtras")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro_form)

        // Título da Toolbar (Nome do carroExtras ou Novo Carro)
        setupToolbar(R.id.toolbarCarroForm, carroExtras?.nome ?: getString(R.string.novo_carro))

        // Atualiza os dados do formulário
        initViews()
    }

    // Adiciona as opções Salvar e Deletar no menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form_carro, menu)
        return true
    }

    // Trata os eventos do menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_salvar -> {
                taskSalvar()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Inicializa as views
    private fun initViews() {

        // A função apply somente é executada se o objeto não for nulo
        carroExtras?.apply {

            // Foto do carroExtras
            appBarImgCarroForm.loadUrl(carroExtras?.urlFoto)

            // Dados do carroExtras
            desc?.let {
                tDesc.setText(it)
            }

            nome?.let {
                tNome.setText(it)
            }

            // Tipo do carroExtras
            when (tipo) {
                "classicos" -> radioTipo.check(R.id.tipoClassico)
                "esportivos" -> radioTipo.check(R.id.tipoEsportivo)
                "luxo" -> radioTipo.check(R.id.tipoLuxo)
            }
        }
    }

    // Salva o carroExtras no ws ( WebService )
    private fun taskSalvar() {
        if (tNome.text.isEmpty()) {
            tNome.error = getString(R.string.msg_error_form_nome)
            return
        }

        if (tDesc.text.isEmpty()) {
            tDesc.error = getString(R.string.msg_error_form_desc)
            return
        }

        doAsync {

            // Cria um carroExtras ou utiliza o que veio como argumento para salvar/atualizar
            val carro = carroExtras ?: Carro()

            // Copia valores do form para as propriedades do Carro
            carro.nome = tNome.text.toString()
            carro.desc = tDesc.text.toString()
            carro.tipo = when (radioTipo.checkedRadioButtonId) {
                R.id.tipoClassico -> TipoCarro.classicos.name
                R.id.tipoEsportivo -> TipoCarro.esportivos.name
                else -> TipoCarro.luxo.name
            }

            // Salva o carroExtras no servidor
            val response = CarroService.save(carro)

            uiThread {

                // mensagem com a resposta do servidor
                response?.let {
                    toast(it.msg)
                }
                finish()
            }
        }
    }
}
