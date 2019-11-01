package livroandroid.com.br.utils

import android.content.SharedPreferences
import livroandroid.com.br.CarrosApplication

object Prefs {

    val PREF_ID = "carros"

    private fun prefs(): SharedPreferences {
        val context = CarrosApplication.getInstance().applicationContext
        return context.getSharedPreferences(PREF_ID, 0)
    }

    fun setInt(key: String, value: Int) = prefs().edit().putInt(key, value).apply()
    fun getInt(key: String) = prefs().getInt(key, 0)

    fun setString(key: String, value: String) = prefs().edit().putString(key, value).apply()
    fun getString(key: String) = prefs().getString(key, "") ?: ""

    var tabIndex: Int
        get() = getInt(TAB.KEY)
        set(value) = setInt(TAB.KEY, value)
}