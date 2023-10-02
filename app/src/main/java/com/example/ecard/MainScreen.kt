package com.example.ecard

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.IsoDep
import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import android.nfc.tech.Ndef
import android.nfc.tech.NfcA
import android.nfc.tech.NfcB
import android.nfc.tech.NfcF
import android.nfc.tech.NfcV
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        val textBox: TextView = findViewById(R.id.textView)
        textBox.text = "No! :("

        var action : String ?= intent.action

        if (action.equals(NfcAdapter.ACTION_TAG_DISCOVERED)
            || action.equals(NfcAdapter.ACTION_TECH_DISCOVERED))
        {
            Log.v("eCard", "New Intent")
            textBox.text = "YESSS! :)"
        }
    }
    public override fun onPause() {
        super.onPause()
        Log.v("eCard","Main Screen Pause")
        NfcAdapter.getDefaultAdapter(this).disableForegroundDispatch(this)
    }

    public override fun onResume() {
        super.onResume()
        Log.v("eCard","Main Screen Resume")
        var pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        var intentFiltersArray : Array<IntentFilter> ?= null
        var techListsArray: Array<Array<String>> ?= null

        Log.v("eCard", "Main Screen Started")

        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        Log.v("eCard", "Intent Flag added")

        val tags = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED).apply {
            try {
                addDataType("*/*")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                Log.v("eCard", "Main Screen: Intent Filter Fail")
            }
        }

        Log.v("eCard", "Tags Collected")

        techListsArray = arrayOf(arrayOf<String>(NfcA::class.java.name))
        techListsArray = techListsArray.plus(arrayOf(arrayOf<String>(NfcB::class.java.name)))
        techListsArray = techListsArray.plus(arrayOf(arrayOf<String>(NfcF::class.java.name)))
        techListsArray = techListsArray.plus(arrayOf(arrayOf<String>(NfcV::class.java.name)))
        techListsArray = techListsArray.plus(arrayOf(arrayOf<String>(IsoDep::class.java.name)))
        techListsArray = techListsArray.plus(arrayOf(arrayOf<String>(Ndef::class.java.name)))
        techListsArray = techListsArray.plus(arrayOf(arrayOf<String>(MifareClassic::class.java.name)))
        techListsArray = techListsArray.plus(arrayOf(arrayOf<String>(MifareUltralight::class.java.name)))
        intentFiltersArray =  arrayOf(tags)
        NfcAdapter.getDefaultAdapter(this).enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)
    }
}

