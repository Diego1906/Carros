package livroandroid.com.br.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_site_livro.*
import kotlinx.android.synthetic.main.include_progress.*
import livroandroid.com.br.R
import livroandroid.com.br.activity.dialogs.AboutDialog
import livroandroid.com.br.extensions.setupToolbar

class SiteLivroActivity : BaseActivity() {

    private val URL_SOBRE = "http://livroandroid.com.br/sobre.htm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_livro)

        // Argumentos via intent: título
        val idTitle = intent.getSerializableExtra("title") as Int
        val title = getString(idTitle)

        // Toolbar
        setupToolbar(R.id.toolbar, title, true)

        // Carrega a página
        setWebViewClient(webview)
        webview.loadUrl(URL_SOBRE)

        // Swipe to Refresh Layout
        swipeToRefresh.setOnRefreshListener {
            webview.reload()
        }

        // Cores da animação
        swipeToRefresh.setColorSchemeColors(
            resources.getColor(R.color.refresh_progress_1, null),
            resources.getColor(R.color.refresh_progress_3, null),
            resources.getColor(R.color.refresh_progress_2, null)
        )
    }

    private fun setWebViewClient(webview: WebView) {
        webview.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

                if (swipeToRefresh.isRefreshing.not())
                    progress.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)

                // Desliga o progressCarro
                progress.visibility = View.INVISIBLE

                // Finaliza o swipe
                swipeToRefresh.isRefreshing = false
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                if (url.endsWith("sobre.htm")) {
                    // Mostra o dialog
                    AboutDialog.showAbout(supportFragmentManager)

                    // Retorna true para informar que interceptamos o evento
                    return true
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }
}
