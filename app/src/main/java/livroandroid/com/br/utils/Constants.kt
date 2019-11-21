package livroandroid.com.br.utils

object TAB {
    val KEY: String = "tabIndex"
}

object BASE_URL {
    private val BASE = "https://carros-springboot.herokuapp.com/api/v1/"

    object CARROS {
        val CARROS = "${BASE}carros/"
        val TIPO = "${CARROS}tipo/"
    }

    val UPLOAD = "${BASE}upload/"
}

object TAG {
    val HTTP = "http"
}