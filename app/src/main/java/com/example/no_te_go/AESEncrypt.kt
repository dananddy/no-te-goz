package com.example.no_te_go

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class AESEncrypt {

    var mySecretKey: SecretKey? = null
    var myInitializationVector: ByteArray? = null



    fun encrypt(password: ByteArray): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, mySecretKey)
        myInitializationVector = cipher.iv
        val cipherText = cipher.doFinal(password)


        return cipherText
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setSecretKey(secret: String) {
        val decodedKey = Base64.getDecoder().decode(secret)
        val originalKey: SecretKey = SecretKeySpec(decodedKey, 0, decodedKey.size, "AES")

        this.mySecretKey = originalKey
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getVector(): String {
        return Base64.getEncoder().withoutPadding().encodeToString(myInitializationVector)
    }

}