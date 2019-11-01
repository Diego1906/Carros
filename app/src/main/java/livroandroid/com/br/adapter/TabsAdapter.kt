package livroandroid.com.br.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import livroandroid.com.br.fragments.CarrosFragment
import livroandroid.com.br.utils.TipoCarro

class TabsAdapter(private val context: Context, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {

    // Retorna o tipo de carroExtras pelo posição
    fun getTipoCarro(position: Int) = when (position) {
        0 -> TipoCarro.classicos
        1 -> TipoCarro.esportivos
        else -> TipoCarro.luxo
    }

    // Quantidade de Tabs
    override fun getCount(): Int = 3

    // Título da Tab
    override fun getPageTitle(position: Int): CharSequence? {
        val tipo = getTipoCarro(position)
        return context.getString(tipo.string)
    }

    // Fragment que vai mostrar a lista de carros
    override fun getItem(position: Int): Fragment {
        return CarrosFragment()
            .apply {
                arguments = Bundle().apply {
                    putSerializable("tipo", getTipoCarro(position))
                }
            }
    }
}