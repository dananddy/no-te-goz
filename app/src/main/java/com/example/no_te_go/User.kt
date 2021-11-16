package com.example.no_te_go

class User(
    private val login: String,
    private val password: String) {

    fun getUserLogin() : String {
        return login
    }
    fun getUserPassword() : String {
        return password
    }
}