package livroandroid.com.br.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

// Mostra um toast
fun Fragment.toast(message: CharSequence, length: Int = Toast.LENGTH_SHORT): Toast = Toast
    .makeText(activity, message, length)
    .apply {
        show()
    }

fun Fragment.toast(@StringRes message: Int, length: Int = Toast.LENGTH_SHORT): Toast = Toast
    .makeText(activity, message, length)
    .apply {
        show()
    }