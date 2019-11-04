package livroandroid.com.br.domain.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import livroandroid.com.br.domain.Carro

// Define as classes que precisam ser persistidas e a versão do banco
// Classe gerencia o banco de dados inteiro
@Database(entities = arrayOf(Carro::class), version = 1)
abstract class CarrosDataBase : RoomDatabase() {
    abstract fun carroDAO(): CarroDAO
}