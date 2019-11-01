package livroandroid.com.br.activity

import android.os.Bundle
import livroandroid.com.br.R
import livroandroid.com.br.extensions.addFragment
import livroandroid.com.br.extensions.setupToolbar
import livroandroid.com.br.fragments.CarrosFragment
import livroandroid.com.br.utils.AndroidUtils
import livroandroid.com.br.utils.TipoCarro
import livroandroid.com.br.utils.showMessageNoInternet

class CarrosActivity : BaseActivity() {

    private val tipo by lazy {
        intent.getSerializableExtra("tipo") as TipoCarro
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carros)

        val title = getString(tipo.string)

        // Toolbar: configura o título e o "up navigation"
        setupToolbar(R.id.toolbar, title, true)

        if (AndroidUtils.isNetworkAvailable(context).not()) {
            showMessageNoInternet()
            return
        }

        if (savedInstanceState == null) {
            // Adiciona o fragment no layout de marcação
            // Dentre os argumentos que foram passados para a activity, está o tipo do carroExtras.
            addFragment(R.id.container, CarrosFragment())
        }
    }
}
