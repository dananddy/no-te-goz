package com.example.no_te_go

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_log_in.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class LogInActivity : AppCompatActivity() {
    private var sharedPreferencesUser: SharedPreferences? = null
    private var sharedPreferencesKey: SharedPreferences? = null
    private var sharedPreferencesVector: SharedPreferences? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val intent = Intent(this, NoteActivity::class.java)

        sharedPreferencesUser = getSharedPreferences("USER", Context.MODE_PRIVATE)
        sharedPreferencesKey = getSharedPreferences("KEY", Context.MODE_PRIVATE)
        sharedPreferencesVector = getSharedPreferences("VECTOR", Context.MODE_PRIVATE)

        registrBtn.setOnClickListener{

            val login = loginTxt.text.toString()
            val password = passwordTxt.text.toString()

            if (password.isEmpty() && login.isNotEmpty()) {
                User.setLoginName(login)
                val key = AESCrypt.generateKey()
                val editorKey = sharedPreferencesKey?.edit()
                editorKey?.putString(login, key)
                editorKey?.apply()

                User.newUser = true

                startActivity(intent)
            } else {
                passwordTxt.error = "Eneter credentials"
            }
        }

        loginBtn.setOnClickListener{
            val login = loginTxt.text.toString()
            val password = passwordTxt.text.toString()

            User.setLoginName(login)
            var valid = false


            val userKey = sharedPreferencesKey?.getString(login, null)
            val vectorKey = sharedPreferencesVector?.getString(userKey, null)
            val encPassword = sharedPreferencesUser?.getString(login, null)
            if (vectorKey != null && userKey != null && encPassword != null) {
                val passValue = AESCrypt.getPasswordValue(
                                    userKey,
                                    vectorKey,
                                    encPassword
                                )
                valid = passValue == password
            }

            if (!valid) {
                passwordTxt.error = "Wrong password"
            } else {
                startActivity(intent)
            }
        }
    }


}