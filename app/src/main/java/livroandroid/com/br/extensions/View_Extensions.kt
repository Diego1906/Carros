package livroandroid.com.br.extensions

// Utilizar onClick ao invés de setOnClickListener
fun android.view.View.onClick(block: (v: android.view.View?) -> Unit) {
    setOnClickListener(block)
}