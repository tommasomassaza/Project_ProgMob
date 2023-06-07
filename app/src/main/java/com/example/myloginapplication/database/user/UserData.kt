package com.example.myloginapplication.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myloginapplication.database.PasswordEncryptionConverter

@Entity(tableName = "users")
open class UserData(
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    @TypeConverters(PasswordEncryptionConverter::class)
    @ColumnInfo(typeAffinity = ColumnInfo.TEXT)
    var password: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    // Altre funzioni e metodi della classe User

    companion object {
        var currentUser: UserData? = null
    }
}