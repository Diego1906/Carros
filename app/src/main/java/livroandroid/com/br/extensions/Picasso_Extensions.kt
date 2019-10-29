package livroandroid.com.br.extensions

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.Picasso
import java.lang.Exception

// Faz download da foto e mostra o progressBar até o termíno
fun ImageView.loadUrl(url: String?, progress: ProgressBar? = null) {
    if (url == null || url.trim().isEmpty()) {
        setImageBitmap(null)
        return
    }

    if (progress == null) {
        Picasso.get().load(url).fit().into(this)
    } else {
        Picasso.get().load(url).fit().into(this,
            object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    // Download OK
                    progress.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    progress.visibility = View.GONE
                }
            })
    }
}