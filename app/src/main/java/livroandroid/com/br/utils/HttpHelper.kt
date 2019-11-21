package livroandroid.com.br.utils

import android.util.Log
import livroandroid.com.br.extensions.toJson
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object HttpHelper {

    private val LOG_ON = true

    val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
    var client = OkHttpClient()

    // GET
    fun get(url: String): Pair<Int, String> {
        log("HttpHelper.get: $url")

        val request = Request.Builder().url(url).get().build()
        return getJson(request)
    }

    // POST com JSON
    fun post(url: String, json: String): Pair<Int, String> {
        log("HttpHelper.post: $url > $json")

        val body = json.toRequestBody(JSON)
        val request = Request.Builder().url(url).post(body).build()
        return getJson(request)
    }

    // POST com parâmetros (form-urlencoded)
    fun postForm(url: String, params: Map<String, String>): Pair<Int, String> {
        log("HttpHelper.postForm: $url > $params")

        // Adiciona os parâmetros chave=valor na request POST
        val builder = FormBody.Builder()
        for ((key, value) in params) {
            builder.add(key, value)
        }

        val body = builder.build()
        body.contentType().toJson().toRequestBody(JSON)


        // Faz a request
        val request = Request.Builder().url(url).post(body).build()
        return getJson(request)
    }

    // DELETE
    fun delete(url: String): Pair<Int, String> {
        log("HttpHelper.delete: $url")

        val request = Request.Builder().url(url).delete().build()
        return getJson(request)
    }

    // Lê a resposta do servidor no formato JSON
    private fun getJson(request: Request): Pair<Int, String> {
        val response = client.newCall(request).execute()
        val responseBody = response.body
        val code = response.code
        val message = response.message

        if (responseBody != null) {
            val json = responseBody.string()

            log("   << : $json")

            return Pair(code, json)
        }
        throw IOException("Erro ao fazer a requisição")
    }

    private fun log(text: String) {
        if (LOG_ON) {
            Log.d(TAG.HTTP, text)
        }
    }
}