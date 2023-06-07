package com.example.myloginapplication.database

import android.content.Context
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

@ProvidedTypeConverter
class PasswordEncryptionConverter(private val context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    @TypeConverter
    fun encrypt(password: String): String {
        val sharedPreferences = EncryptedSharedPreferences.create(
            "encrypted_shared_preferences",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        sharedPreferences.edit().apply {
            putString("password", password)
            apply()
        }
        return password
    }

    @TypeConverter
    fun decrypt(encryptedPassword: String): String {
        val sharedPreferences = EncryptedSharedPreferences.create(
            "encrypted_shared_preferences",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return sharedPreferences.getString("password", "") ?: ""
    }
}
