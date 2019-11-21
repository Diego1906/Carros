package livroandroid.com.br.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class CameraHelper {

    companion object {
        private val TAG = "camera"
    }

    // Arquivo pode ser nulo
    var file: File? = null
        private set

    // Se girou a tela recupera o estado
    fun init(saveInstanceState: Bundle?) {

        saveInstanceState?.let {
            file = it.getSerializable("file") as File
        }
    }

    // Salva o estado
    fun onSaveInstanceState(outState: Bundle) {

        file?.let {
            outState.putSerializable("file", it)
        }
    }

    // Intent para abrir a câmera
    fun open(context: Context, fileName: String): Intent {

        file = getSdCardFile(context, fileName)

        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            file?.let {
                val uri = FileProvider.getUriForFile(
                    context, "${context.applicationContext.packageName}.provider", it
                )
                this.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
        }
    }

    // Cria o arquivo da foto no diretório privado do app
    private fun getSdCardFile(context: Context, fileName: String): File {

        val dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        dir?.let {
            if (it.exists().not()) {
                it.mkdir()
            }
        }
        return File(dir, fileName)
    }

    // Lê o bitmap no tamanho desejado
    fun getBitmap(width: Int, height: Int): Bitmap? {

        file?.apply {
            if (exists()) {
                Log.d(TAG, absolutePath)

                // Resize
                val bitmap = ImageUtils.resize(this, width, height)

                Log.d(TAG, "getBitmap width/height: ${bitmap.width} / ${bitmap.height}")

                return bitmap
            }
        }
        return null
    }

    // Salva o bitmap reduzido no arquivo (para upload)
    fun save(bitmap: Bitmap) {

        file?.apply {
            FileOutputStream(this).use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            }
            Log.d(TAG, "Foto salva: $absolutePath")
        }
    }
}