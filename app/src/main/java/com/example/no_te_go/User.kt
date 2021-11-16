package com.example.no_te_go

import java.lang.Exception
import javax.crypto.SecretKey

object User {

    init {
    }
    var login = ""

    fun getLoginName() :String {
        return login
    }

    fun setLoginName(login: String) {
        this.login = login
    }

}