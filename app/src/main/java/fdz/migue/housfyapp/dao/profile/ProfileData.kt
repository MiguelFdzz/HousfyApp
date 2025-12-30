package fdz.migue.housfyapp.dao.profile

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class Profile (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val status: String,
    //TODO: Por alguna razon no se guarda correctamente o no carga correctamente, al no poner una imagen y guardar se crashea, y no deja volver a entrar
    val profileImageUri: String? = null
)