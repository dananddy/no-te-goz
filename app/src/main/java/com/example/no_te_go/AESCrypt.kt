package com.example.no_te_go

import android.content.SharedPreferences
import android.os.Build
import android.util.Base64DataException
import androidx.annotation.RequiresApi
import java.util.*
import java.util.Base64.Encoder
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

fun String.addSalt(): String {
    val count = this.count()
    val symbolsCount = count % 4
    val salt = "@".repeat(symbolsCount)
    return this.plus(salt)
}

fun String.removeSalt(): String {
    return this.replace("@", "")
}

object AESCrypt {

    var vector: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateKey(): String {
        val keygen = KeyGenerator.getInstance("AES")
        keygen.init(256)

        return Base64.getEncoder().withoutPadding().encodeToString(keygen.generateKey().encoded)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getString(array: ByteArray): String {
        val str = Base64.getEncoder().withoutPadding().encodeToString(array)
        return  str
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getByteArray(str: String): ByteArray {
        val password = Base64.getDecoder().decode(str)
        return password
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPasswordValue(userKey: String, vectorKey: String, encPassword: String): String {
        val password: String
        val passwordBytes : ByteArray?

        val decriptor = AESDecrypt()

        decriptor.setVector(vectorKey)
        decriptor.setSecretKey(userKey)

        passwordBytes = decriptor.decrypt(getByteArray(encPassword))
        password = getString(passwordBytes)

        return password

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getNoteValue(userKey: String, vectorKey: String, encNote: String): String {
        val note: String
        val noteBytes : ByteArray?

        val decriptor = AESDecrypt()

        decriptor.setVector(vectorKey)
        decriptor.setSecretKey(userKey)

        noteBytes = decriptor.decrypt(getByteArray(encNote))
        note = getString(noteBytes)

        return note

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setPasswordValue(userKey: String, newPassword: String): String {
        val password: String

        val encripter = AESEncrypt()

        encripter.setSecretKey(userKey)

        password = getString(encripter.encrypt(getByteArray(newPassword)))

        this.vector = encripter.getVector()

        return password
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setNoteValue(userKey: String, note: String): String {
        val password: String

        val encripter = AESEncrypt()

        encripter.setSecretKey(userKey)

        password = getString(encripter.encrypt(getByteArray(note)))

        this.vector = encripter.getVector()

        return password
    }
}
