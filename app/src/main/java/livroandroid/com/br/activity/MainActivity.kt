package livroandroid.com.br.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*
import livroandroid.com.br.R
import livroandroid.com.br.adapter.TabsAdapter
import livroandroid.com.br.extensions.setupToolbar
import livroandroid.com.br.utils.AndroidUtils
import livroandroid.com.br.utils.TipoCarro
import livroandroid.com.br.utils.showMessageNoInternet
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupToolbar(R.id.toolbar)
        setupNavDrawer()
        setupViewPagerTabs()

        if (AndroidUtils.isNetworkAvailable(context).not()) {
            showMessageNoInternet()
            return
        }

        // FAB ( variável fab gerada automaticamente pelo Koltin Extensions )
        fab.setOnClickListener {
            startActivity<CarroFormActivity>()
        }
    }

    // Configura o ViewPager + Tabs
    private fun setupViewPagerTabs() {
        // As variáveis viewPager e tabLayout são geradas automaticamente pelo Kotlin Extensions
        viewPager.apply {
            offscreenPageLimit = 2
            adapter = TabsAdapter(context, supportFragmentManager)
        }

        // Cor branca no texto (o fundo azul é definido no layout)
        val cor = ContextCompat.getColor(context, R.color.white)

        tabLayout.apply {
            setupWithViewPager(viewPager, true)
            setTabTextColors(cor, cor)
        }
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
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
