package livroandroid.com.br.domain

data class Response(
    val id: Long,
    val status: String?,
    val msg: String?,
    val url: String?,
    var code: Int
) {
    fun isOK() = "OK".equals(status, ignoreCase = true)

    fun isUrlOK() = url.isNullOrEmpty().not()

    fun isCodeSucess() = code in 200..299
}