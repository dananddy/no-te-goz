package com.example.no_te_go

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object AESCrypt {

    var vector: String = ""


    @RequiresApi(Build.VERSION_CODES.O)
    fun generateKey(): String {
        val keygen = KeyGenerator.getInstance("AES")
        keygen.init(256)

        return Base64.getEncoder().encodeToString(keygen.generateKey().encoded)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPasswordString(array: ByteArray): String {
        val str = Base64.getEncoder().encodeToString(array)
        return  str
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPasswordArray(passwordStr: String): ByteArray {
        val password = Base64.getDecoder().decode(passwordStr)
        return password
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPasswordValue(userKey: String, vectorKey: String, encPassword: String): String {
        val password: String
        val passwordBytes : ByteArray?

        val decriptor = AESDecrypt()

        decriptor.setVector(vectorKey)
        decriptor.setSecretKey(userKey)

        passwordBytes = decriptor.decrypt(getPasswordArray(encPassword))
        password = getPasswordString(passwordBytes)

        return password

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setPasswordValue(userKey: String, newPassword: String): String {
        val password: String

        val encripter = AESEncrypt()

        encripter.setSecretKey(userKey)

        password = getPasswordString(encripter.encrypt(getPasswordArray(newPassword)))

        this.vector = encripter.getVector()

        return password
    }
}