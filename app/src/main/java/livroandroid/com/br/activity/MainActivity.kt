package livroandroid.com.br.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*
import livroandroid.com.br.R
import livroandroid.com.br.extensions.setupToolbar
import livroandroid.com.br.utils.TipoCarro
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar(R.id.toolbar)
        setupNavDrawer()
    }

    // Configura o Navigation Drawer
    private fun setupNavDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    // Trata o evento do Navigation Drawer
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_item_carros_todos -> {
                toast("Clicou em carros")
            }
            R.id.nav_item_carros_classicos -> {

                startActivity<CarrosActivity>("tipo" to TipoCarro.classicos)
            }
            R.id.nav_item_carros_esportivos -> {

                startActivity<CarrosActivity>("tipo" to TipoCarro.esportivos)
            }
            R.id.nav_item_carros_luxo -> {

                startActivity<CarrosActivity>("tipo" to TipoCarro.luxo)
            }
            R.id.nav_item_site_livro -> {
                startActivity<SiteLivroActivity>("title" to R.string.site_do_livro)
            }
            R.id.nav_item_settings -> {
                toast("Clicou em configurações")
            }
        }

        // Fecha o menu depois de tratar o evento
        // drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
