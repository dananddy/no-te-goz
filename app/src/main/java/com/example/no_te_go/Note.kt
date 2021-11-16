package com.example.no_te_go

import android.content.Context

data class Note(val noteText: String,
                val date: String){
    override fun toString(): String {
        return "$noteText: $date"
    }

}