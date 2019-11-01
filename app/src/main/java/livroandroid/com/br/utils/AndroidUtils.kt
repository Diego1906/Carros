package livroandroid.com.br.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import livroandroid.com.br.R

object AndroidUtils {

    fun isNetworkAvailable(context: Context?): Boolean {
        val connectivity =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networks = connectivity.allNetworks

        return networks
            .map { connectivity.getNetworkInfo(it) }
            .any { it?.state == NetworkInfo.State.CONNECTED }
    }
}

fun Activity.showMessageNoInternet() {
    AlertDialog.Builder(this).apply {
        setTitle("Informação")
        setMessage("Aparelho sem conexão com a internet.\n\nLigue a internet e tente novamente.")
        setPositiveButton(getString(R.string.ok)) { dialog, which -> }
        create()
        show()
    }
}