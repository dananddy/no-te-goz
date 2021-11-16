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


//        val editor = sharedPreferences.edit()
//        val strSet = ArrayList<User>();
//
//        val u1 = User("dasga","1234")
//        val u2 = User("busido","1234")
//        val u3 = User("moloka","1234")
//
//        strSet.add(u1)
//        strSet.add(u2)
//        strSet.add(u3)
//
//        strSet.forEach{
//            editor.putString(it.getUserLogin(), it.getUserPassword())
//        }
//
//        editor.apply()
        loginBtn.setOnClickListener{
            val intent = Intent(this, NoteActivity::class.java)
            val login = loginTxt.text.toString()
            val password = passwordTxt.text.toString()

            val passwordForLogin = sharedPreferences.getString(login, "")
            val valid = (passwordForLogin.toString() == password)
            if (passwordForLogin.isNullOrBlank() || !valid) {
                print("not right")
            } else {
                startActivity(intent)
            }

        }
    }
}