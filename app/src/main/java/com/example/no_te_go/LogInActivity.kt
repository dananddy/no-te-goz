package com.example.no_te_go

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val sharedPreferences = getSharedPreferences("USER", Context.MODE_PRIVATE)


        val editor = sharedPreferences.edit()

//        val encriter = AESEncrypt()
//        val PASS = encriter.encrypt("1234")
//
//        editor?.putString("dasga", PASS.toString(Charsets.UTF_8))
//        editor?.apply()
        loginBtn.setOnClickListener{
            val intent = Intent(this, NoteActivity::class.java)
            val login = loginTxt.text.toString()
            val password = passwordTxt.text.toString()

            val passwordForLogin = sharedPreferences.getString(login, "")
//            val decripter = AESDecrypt()
//            val PAASS = decripter.decrypt(passwordForLogin.toByteArray(Charsets.UTF_8))
            val valid = (passwordForLogin.toString() == password)
            if (passwordForLogin.isNullOrBlank() || !valid) {
                print("not right")
            } else {
                User.setLoginName(login)
                startActivity(intent)
            }

        }
    }
}