package livroandroid.com.br.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {

    // Solicita as permissões
    fun validate(activity: Activity, requestCode: Int, vararg permissions: String): Boolean {

        val list = ArrayList<String>()
        for (permission in permissions) {
            val ok = ContextCompat.checkSelfPermission(
                activity,
                permission
            ) == PackageManager.PERMISSION_GRANTED

            if (ok.not()) {
                list.add(permission)
            }
        }

        if (list.isEmpty()) {
            // Tudo ok, retorna true
            return true
        }

        // Lista de permissões que falta acesso
        val newPermissions = arrayOfNulls<String>(list.size)
        list.toArray(newPermissions)

        // Solicita permissão
        ActivityCompat.requestPermissions(activity, newPermissions, 1)
        return false
    }
}