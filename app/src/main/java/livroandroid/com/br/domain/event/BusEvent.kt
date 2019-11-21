package livroandroid.com.br.domain.event

import livroandroid.com.br.domain.Carro

data class SaveCarroEvent(val carro: Carro)
data class FavoritoEvent(val carro: Carro)

