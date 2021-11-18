package com.example.no_te_go

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.no_te_go.R
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        val sharedPreferences = getSharedPreferences("NOTE", Context.MODE_PRIVATE)
        val login = User.getLoginName()

        textView_note.text.append(sharedPreferences.getString(login, ""))

        button_settings.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.edit_text_view, EditPassword.newInstance()).commit()
        }

        button_save.setOnClickListener{
            val text = textView_note.text.toString()
            val editor = sharedPreferences?.edit()
            editor?.putString(login, text)
            editor?.apply()

            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(this,"Saved!",duration)
            toast.show()
        }
    }
}