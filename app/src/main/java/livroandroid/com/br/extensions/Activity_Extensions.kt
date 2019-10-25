package livroandroid.com.br.extensions

import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

// findViewById + setOnClickListener
fun AppCompatActivity.onClick(@IdRes viewId: Int, onClick: (v: View?) -> Unit) {
    val view = findViewById<View>(viewId)
    view.setOnClickListener {
        onClick(it)
    }
}

// Configura a Toolbar
fun AppCompatActivity.setupToolbar(
    @IdRes id: Int,
    title: String? = null,
    upNavigation: Boolean = false
): ActionBar {
    val toolbar = findViewById<Toolbar>(id)
    setSupportActionBar(toolbar)

    if (title != null) {
        supportActionBar?.title = title
    }
    supportActionBar?.setDisplayHomeAsUpEnabled(upNavigation)
    return supportActionBar!!
}

// Adiciona o fragment no layout
fun AppCompatActivity.addFragment(@IdRes layoutId: Int, fragment: Fragment) {
    fragment.arguments = intent.extras
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.add(layoutId, fragment)
    fragmentTransaction.commit()
}