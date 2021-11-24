package com.example.no_te_go

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.no_te_go.R
import kotlinx.android.synthetic.main.activity_note.*
import kotlinx.android.synthetic.main.fragment_edit_password.*

class NoteActivity : AppCompatActivity() {
    private var preferenceNote: SharedPreferences? = null
    private var preferenceKey: SharedPreferences? = null
    private var sharedPreferencesUser: SharedPreferences? = null
    private var preferenceVector: SharedPreferences? = null

    var note = Note("", false)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        preferenceNote = getSharedPreferences("NOTE", Context.MODE_PRIVATE)
        preferenceKey = getSharedPreferences("KEY", Context.MODE_PRIVATE)
        preferenceVector = getSharedPreferences("VECTOR_NOTE", Context.MODE_PRIVATE)

        val login = User.getLoginName()

        val noteText = preferenceNote?.getString(login, null)
        if (noteText != null) {
            note.text = noteText
            note.isEncrypted = true
            textView_note.text.append(noteText)
        }


        button_settings.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.edit_text_view, EditPassword.newInstance()).commit()
        }

        button_save.setOnClickListener{
            val userKey = preferenceKey?.getString(login, "FAIL_LOGIN_KEY")
            if (userKey != null) {
                val text = textView_note.text.toString()
                val newNote = text.replace(" ","#")
                val editor = preferenceNote?.edit()

                val noteStr = AESCrypt.setNoteValue(userKey, text)
                editor?.putString(login, noteStr)
                editor?.apply()

                val editorKey = preferenceVector?.edit()
                editorKey?.putString(userKey, AESCrypt.vector)
                editorKey?.apply()

                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(this,"Saved!",duration)
                toast.show()

                note.text = noteStr
                note.isEncrypted = false
            }
        }

        button_encrypte.setOnClickListener {
            if (note.isEncrypted) {
                textView_note.text.clear()
                val str = getNoteText(login)
                textView_note.text.append(str)
            } else {
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(this,"Already decrypted!",duration)
                toast.show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getNoteText(login: String): String {
        val userKey = preferenceKey?.getString(login, null)
        val vectorKey = preferenceVector?.getString(userKey, null)

        val newNote = note.text.replace("#"," ")
        return if (vectorKey != null && userKey != null) {
            val note = AESCrypt.getNoteValue(
                userKey,
                vectorKey,
                newNote
            )
            note
        } else {
            "ERROR"
        }

    }
}