package livroandroid.com.br.fragments

import androidx.fragment.app.Fragment
import livroandroid.com.br.CarrosApplication

open class BaseFragment : Fragment() {
    // Métodos comuns para todos fragments aqui...

    val app = CarrosApplication.getInstance()
    val context1 = app.applicationContext

}
