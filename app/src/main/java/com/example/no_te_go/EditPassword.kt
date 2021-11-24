package com.example.no_te_go

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.fragment_edit_password.*
import java.util.concurrent.ThreadPoolExecutor
import kotlin.math.log

class EditPassword : Fragment() {
    private var preferenceUser: SharedPreferences? = null
    private var preferenceKey: SharedPreferences? = null
    private var preferencevector: SharedPreferences? = null

    var newUser = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_edit_password, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceUser = this.activity?.getSharedPreferences("USER", Context.MODE_PRIVATE)
        preferenceKey = this.activity?.getSharedPreferences("KEY", Context.MODE_PRIVATE)
        preferencevector = this.activity?.getSharedPreferences("VECTOR", Context.MODE_PRIVATE)

        if (User.newUser) {
            oldPassword.setText("ENTER YOUR NEW PASSWORD")
            oldPassword.isEnabled = false
            newUser = true
        }
        button.setOnClickListener {
            val login = User.getLoginName()
            val oldPassword = oldPassword.text.toString()
            val newPassword = newPassword.text.toString()

            if  (newUser) {
                editPassword(login, newPassword)
            } else if (oldPassword.isNullOrBlank() || newPassword.isNullOrBlank()) {
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(activity,"input old and new password",duration)
                toast.show()
            } else if (newPassword.length<5) {
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(activity,"short",duration)
                toast.show()
            } else if(!newPassword.matches(".*[0-9].*".toRegex())) {
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(activity,"should have digits",duration)
                toast.show()
            } else if(!newPassword.matches(".*[a-zA-Z].*".toRegex())) {
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(activity, "should have letters", duration)
                toast.show()
            } else if (!oldPasswordIsMatched(oldPassword, login)) {
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(activity,"does not match",duration)
                toast.show()
            } else {
                editPassword(login, newPassword)
            }

        }

        cencel.setOnClickListener{
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction().remove(this).commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun oldPasswordIsMatched(oldPassword: String, login: String): Boolean {
        val userKey = preferenceKey?.getString(login, null)
        val vectorKey = preferencevector?.getString(userKey, null)
        val encPassword = preferenceUser?.getString(login, null)

        var same = false

        if (vectorKey != null && userKey != null && encPassword != null) {
            val passValue = AESCrypt.getPasswordValue(
                userKey,
                vectorKey,
                encPassword
            )
            same = passValue == oldPassword
        }

        return same
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun editPassword(login: String, newPassword: String) {
        val userKey = preferenceKey?.getString(login, "FAIL_LOGIN_KEY")
        if (userKey != null) {
            val password = AESCrypt.setPasswordValue(userKey, newPassword)
            val editorKey = preferencevector?.edit()
            editorKey?.putString(userKey, AESCrypt.vector)
            editorKey?.apply()

            val editorUser = preferenceUser?.edit()
            editorUser?.putString(login, password)
            editorUser?.apply()

            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction().remove(this).commit()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = EditPassword()
    }

}