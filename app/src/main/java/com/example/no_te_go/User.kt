package com.example.no_te_go

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.Exception
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object User {

    var login = ""
    var newUser = false

    fun getLoginName() :String {
        return User.login
    }

    fun setLoginName(login: String) {
        this.login = login
    }
}