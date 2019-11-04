package livroandroid.com.br.domain

import livroandroid.com.br.domain.dao.DatabaseManager

object FavoritosService {

    // Retorna todos os carros favoritos
    fun getCarros(): List<Carro> {
        val dao = DatabaseManager.getCarroDAO()
        val carros = dao.findAll()
        return carros
    }

    // Salva o carro ou deleta
    fun favoritar(carro: Carro): Boolean {
        val dao = DatabaseManager.getCarroDAO()
        val favorito = isFavotiro(carro)
        if (favorito) {
            // Remove dos favotiros
            dao.delete(carro)
            return false
        }
        // Adiciona nos favoritos
        dao.insert(carro)
        return true
    }

    // Verifica se um carro está favotirado
    fun isFavotiro(carro: Carro): Boolean {
        val dao = DatabaseManager.getCarroDAO()
        val exists = dao.getById(carro.id) != null
        return exists
    }
}