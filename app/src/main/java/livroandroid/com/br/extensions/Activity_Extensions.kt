package livroandroid.com.br.extensions

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
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

// Mostra um toast
fun Activity.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) = Toast
    .makeText(this, message, duration)
    .apply {
        show()
    }

fun Activity.toast(@StringRes message: Int, duration: Int = Toast.LENGTH_SHORT) = Toast
    .makeText(this, message, duration)
    .apply {
        show()
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