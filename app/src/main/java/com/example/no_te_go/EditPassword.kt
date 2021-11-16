package com.example.no_te_go

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_edit_password.*
import kotlin.math.log

class EditPassword : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preference = this.activity?.getSharedPreferences("USER", Context.MODE_PRIVATE)
        button.setOnClickListener{
            val login = User.getLoginName()
            val password = preference?.getString(login, "")
            val oldPassword = oldPassword.text.toString()
            val newPassword = newPassword.text.toString()
            if (oldPassword.isNullOrBlank() || newPassword.isNullOrBlank() || password != oldPassword) {
               print("")
            } else {
                val editor = preference?.edit()
//                val encriter = AESEncrypt()
//                val PASS = encriter.encrypt(newPassword)
                //PASS.toString(Charsets.UTF_8)
                editor?.putString(login, newPassword)
                editor?.apply()
                val manager = requireActivity().supportFragmentManager
                manager.beginTransaction().remove(this).commit()
            }

        }
        cencel.setOnClickListener{
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction().remove(this).commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditPassword()
    }

}