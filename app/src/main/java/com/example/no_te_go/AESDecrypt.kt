package com.example.no_te_go

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AESDecrypt {

    private var mySecretKey: SecretKey? = null
    private var initializationVector: ByteArray? = null

    fun decrypt(dataToDecrypt: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        val ivSpec = IvParameterSpec(initializationVector)
        cipher.init(Cipher.DECRYPT_MODE, mySecretKey, ivSpec)
        val cipherText = cipher.doFinal(dataToDecrypt)

        return cipherText
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setSecretKey(secret: String) {
        val decodedKey = Base64.getDecoder().decode(secret)
        val originalKey: SecretKey = SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")

        this.mySecretKey = originalKey
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setVector(vector: String) {
        val decodedVector = Base64.getDecoder().decode(vector)
        this.initializationVector = decodedVector
    }

}