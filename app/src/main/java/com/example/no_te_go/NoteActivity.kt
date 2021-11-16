package com.example.no_te_go

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.no_te_go.R
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        val sharedPreferences = getSharedPreferences("USER", Context.MODE_PRIVATE)

        //textView_note.text = sharedPreferences.getString("dasga", "")

        button_settings.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.edit_text_view, EditPassword.newInstance()).commit()
        }
    }
}