package livroandroid.com.br.activity.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import livroandroid.com.br.R

class AboutDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Cria o HTML com o texto de sobre do aplicativo
        val aboutBody = SpannableStringBuilder()

        // Versão do aplicativo
        val versionName = getAppVersionName()

        // Converte o texto do strings.xml para HTML
        val html = Html.fromHtml(getString(R.string.about_dialog_text, versionName))
        aboutBody.append(html)

        // Infla o layout
        val inflater = LayoutInflater.from(activity)

        val view = inflater.inflate(R.layout.dialog_about, null) as TextView
        view.text = aboutBody
        view.movementMethod = LinkMovementMethod()

        // Cria o dialog customizado
        return AlertDialog.Builder(activity)
            .setTitle(R.string.about_dialog_title)
            .setView(view)
            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
            .create()
    }

    // Retorna a versão do app registrada no build.gradle
    private fun getAppVersionName(): String {
        val packageManager = activity?.packageManager
        val packageName = activity?.packageName
        lateinit var versionName: String

        try {
//            val info = packageManager?.getPackageInfo(packageName, 0)
//            info?.let {
//                versionName = it.versionName
//            }

            packageManager?.let {
                versionName = it.getPackageInfo(packageName, 0).versionName
            }
        } catch (ex: PackageManager.NameNotFoundException) {
            versionName = "N/A"
        }
        return versionName
    }

    companion object {

        // Método utilitário para mostrar o dialog
        fun showAbout(fragmentManager: FragmentManager) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            val fragment = fragmentManager.findFragmentByTag("about_dialog")
            if (fragment != null) {
                fragmentTransaction.remove(fragment)
            }
            fragmentTransaction.addToBackStack(null)
            AboutDialog().show(fragmentTransaction, "about_dialog")
        }
    }
}