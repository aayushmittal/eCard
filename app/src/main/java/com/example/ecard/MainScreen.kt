package com.example.ecard

import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainScreen : AppCompatActivity() {

    val filter = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("Aayush","HereGG")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        val textView: TextView = findViewById(R.id.textView)
        textView.text = "YESSS!!!"
    }
    override fun onNewIntent(intent: Intent?) {
        Log.v("Aayush","Here3")
        super.onNewIntent(intent)
        Log.v("Aayush","Here4")
        startActivity(Intent(this, MainScreen::class.java))
    }
}

