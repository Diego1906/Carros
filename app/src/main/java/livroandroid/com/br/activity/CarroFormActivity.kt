package livroandroid.com.br.activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_carro_form.*
import kotlinx.android.synthetic.main.activity_carro_form_contents.*
import livroandroid.com.br.R
import livroandroid.com.br.domain.Carro
import livroandroid.com.br.domain.CarroServiceOkHttp
import livroandroid.com.br.domain.Response
import livroandroid.com.br.extensions.*
import livroandroid.com.br.utils.CameraHelper
import livroandroid.com.br.utils.TipoCarro
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class CarroFormActivity : BaseActivity() {

    private val TAG = javaClass.simpleName

    private val camera: CameraHelper by lazy {
        CameraHelper()
    }

    // O carro pode ser nulo no caso de um Novo Carro
    val carro: Carro? by lazy {
        intent.getParcelableExtra<Carro>("carroExtras")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carro_form)

        // Título da Toolbar (Nome do carroExtras ou Novo Carro)
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
            desc?.let {
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

        Log.d(TAG, "onClickAppBarImg")
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
            car.desc = tDesc.string
            car.tipo = when (radioTipo.checkedRadioButtonId) {
                R.id.tipoClassico -> TipoCarro.classicos.name
                R.id.tipoEsportivo -> TipoCarro.esportivos.name
                else -> TipoCarro.luxo.name
            }

//            // Se tiver foto, faz upload
//            camera.file?.let {
//
//                if (it.exists()) {
//                    val response = CarroServiceOkHttp.postFoto(it)
//
//                    if (response.isOK()) {
//                        // Atualiza a URL da foto no carro
//                        car.urlFoto = response.url
//                    }
//                }
//            }

            lateinit var responsePostFoto: Response
            lateinit var responseCarroSave: Response

            val file = camera.file

            if (file != null && file.exists()) {

                responsePostFoto = CarroServiceOkHttp.postFoto(file)

                if (responsePostFoto.isOK()) {
                    // Atualiza a URL da foto no carro
                    car.urlFoto = responsePostFoto.url

                    // Salva o carro no servidor
                    responseCarroSave = CarroServiceOkHttp.save(car)
                }
            }

            uiThread {
                if (responsePostFoto.isOK()) {

                    responseCarroSave?.let {
                        toast(it.msg)
                    }
                } else {
                    toast(responsePostFoto.msg)
                }

                dialog.dismiss()
                finish()
            }
        }
    }

    // Lê a foto quando a câmera retornar
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(TAG, "onActivityResult")

        if (resultCode == Activity.RESULT_OK) {

            // Resize da imagem
            // val bitmap = camera.getBitmap(600, 600)
//            bitmap?.let {
            //// Salva o arquivo neste tamanho
//                camera.save(it)
//
//                // Mostra a foto do carro
//                appBarImgCarroForm.setImageBitmap(it)
//            }//

            // Salva o arquivo neste tamanho
            camera.getBitmap(600, 600)?.let {

                // Salva arquivo neste tamanho
                camera.save(it)

                // Mostra a foto do carro
                appBarImgCarroForm.setImageBitmap(it)
            }
        }
    }
}
