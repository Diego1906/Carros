package livroandroid.com.br.domain

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carro")
class Carro() : Parcelable {
    @PrimaryKey
    var id: Long = 0

    var tipo: String? = ""
    var nome: String? = ""
    var descricao: String? = ""
    var urlFoto: String? = ""
    var urlInfo: String? = ""
    var urlVideo: String? = ""

    var latitude: String? = ""
        get() = if (field?.trim().isNullOrEmpty()) "0.0" else field

    var longitude: String? = ""
        get() = if (field?.trim().isNullOrEmpty()) "0.0" else field

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        tipo = parcel.readString()
        nome = parcel.readString()
        descricao = parcel.readString()
        urlFoto = parcel.readString()
        urlInfo = parcel.readString()
        urlVideo = parcel.readString()
        latitude = parcel.readString()
        longitude = parcel.readString()
    }

    override fun toString(): String {
        return "Carro(nome='$nome')"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(tipo)
        parcel.writeString(nome)
        parcel.writeString(descricao)
        parcel.writeString(urlFoto)
        parcel.writeString(urlInfo)
        parcel.writeString(urlVideo)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Carro> {
        override fun createFromParcel(parcel: Parcel): Carro {
            return Carro(parcel)
        }

        override fun newArray(size: Int): Array<Carro?> {
            return arrayOfNulls(size)
        }
    }
}