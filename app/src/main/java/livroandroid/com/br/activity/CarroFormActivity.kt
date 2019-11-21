package livroandroid.com.br.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_carro_form.*
import kotlinx.android.synthetic.main.activity_carro_form_contents.*
import livroandroid.com.br.R
import livroandroid.com.br.domain.Carro
import livroandroid.com.br.domain.CarroServiceOkHttp
import livroandroid.com.br.domain.Response
import livroandroid.com.br.domain.event.SaveCarroEvent
import livroandroid.com.br.extensions.*
import livroandroid.com.br.utils.CameraHelper
import livroandroid.com.br.utils.TipoCarro
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class CarroFormActivity : BaseActivity() {

    private val camera: CameraHelper by lazy {
        CameraHelper()
    }

    // O carro pode ser nulo no caso de um novo carro
    private val carro: Carro? by lazy {
        intent.getParcelableExtra<Carro>("carroExtras")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro_form)

        // Título da Toolbar (Nome do carroExtras ou novo carro)
        setupToolbar(R.id.toolbarCarroForm, carro?.nome ?: getString(R.string.novo_carro))

        // Atualiza os dados do formulário
        initViews()

        // Inicializa a camera
        camera.init(savedInstanceState)
    }

    // Inicializa as views
    private fun initViews() {

        // Ao clicar no header da foto abre a câmera
        appBarImgCarroForm.onClick {
            onClickAppBarImg()
        }

        // A função apply somente é executada se o objeto não for nulo
        carro?.apply {
            // Foto do carro
            appBarImgCarroForm.loadUrl(carro?.urlFoto)

            // Dados do carro
            descricao?.let {
                tDesc.string = it
            }

            nome?.let {
                tNome.string = it
            }

            // Tipo do carroExtras
            when (tipo) {
                "classicos" -> radioTipo.check(R.id.tipoClassico)
                "esportivos" -> radioTipo.check(R.id.tipoEsportivo)
                "luxo" -> radioTipo.check(R.id.tipoLuxo)
            }
        }
    }

    // Ao clickar na imagem do AppHeader abre a câmera
    private fun onClickAppBarImg() {
        val ms = System.currentTimeMillis()

        val namePicture = "foto_carro_"

        // Nome do arquivo da foto
        val fileName = carro?.let { "$namePicture${it.id}.jpg" } ?: "$namePicture${ms}.jpg"

        // Abre a câmera
        startActivityForResult(camera.open(this, fileName), 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Salva o estado do arquivo caso gire a tela
        camera.onSaveInstanceState(outState)
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

    // Salva o carroExtras no ws ( WebService )
    private fun taskSalvar() {

        if (tNome.isEmpty()) {
            tNome.error = getString(R.string.msg_error_form_nome)
            return
        }

        if (tDesc.isEmpty()) {
            tDesc.error = getString(R.string.msg_error_form_desc)
            return
        }

        val dialog = ProgressDialog.show(
            this,
            getString(R.string.download),
            getString(R.string.salvando_carro),
            false, true
        )

        doAsync {

            // Cria um carro ou utiliza o que veio como argumento para salvar/atualizar
            val car = carro ?: Carro()

            // Copia valores do form para as propriedades do Carro
            car.nome = tNome.string
            car.descricao = tDesc.string
            car.tipo = when (radioTipo.checkedRadioButtonId) {
                R.id.tipoClassico -> TipoCarro.classicos.name
                R.id.tipoEsportivo -> TipoCarro.esportivos.name
                else -> TipoCarro.luxo.name
            }

            lateinit var responsePostFoto: Response
            lateinit var responseCarroSave: Response

            camera.file?.let {

                if (it.exists()) {

                    responsePostFoto = CarroServiceOkHttp.postFoto(it)

                    if (responsePostFoto.isUrlOK() && responsePostFoto.isCodeSucess()) {

                        // Atualiza a URL da foto no carro
                        car.urlFoto = responsePostFoto.url
                    }
                }
            }

            // Salva o carro no servidor
            responseCarroSave = CarroServiceOkHttp.save(car)

            uiThread {

                // Mensagem com a resposta do servidor
                if (responseCarroSave.isCodeSucess()) {
                    toast("Carro salvo com sucesso")
                }

                dialog.dismiss()
                finish()

                // Dispara um evento para atualizar a lista de carros
                EventBus.getDefault().post(SaveCarroEvent(car))
            }
        }
    }

    // Lê a foto quando a câmera retornar
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            // Resize da imagem
            camera.getBitmap(600, 600)?.let {

                // Salva arquivo neste tamanho
                camera.save(it)

                // Mostra a foto do carro
                appBarImgCarroForm.setImageBitmap(it)
            }
        }
    }
}
