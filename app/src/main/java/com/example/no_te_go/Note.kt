package com.example.no_te_go

import android.content.Context

 class Note(text: String, isEncrypted: Boolean){
    var text: String = ""
    var isEncrypted = true

     init {
         this.isEncrypted = isEncrypted
         this.text = text
     }
}