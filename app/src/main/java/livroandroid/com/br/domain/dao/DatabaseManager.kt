package livroandroid.com.br.domain.dao

import androidx.room.Room
import livroandroid.com.br.CarrosApplication

object DatabaseManager {

    // Singleton do Room: Banco de dados
    private var dbInstance: CarrosDataBase

    init {
        val appContext = CarrosApplication.getInstance().applicationContext

        // Configura o Room
        dbInstance = Room.databaseBuilder(
            appContext,                     // Context global
            CarrosDataBase::class.java,    // Referência da classe de Application
            "carros.sqlite"         // Nome do arquivo do banco de dados
        ).build()
    }

    fun getCarroDAO(): CarroDAO {
        return dbInstance.carroDAO()
    }
}