package com.example.ecard


import android.content.Intent
import android.content.IntentFilter
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.hardware.biometrics.BiometricPrompt
import android.nfc.NfcAdapter
import android.nfc.tech.NfcA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textBox: TextView = findViewById(R.id.Text_Box)
        textBox.text = "NOPE! :("

        Log.v("eCard","Main: Starting Biometric")

        val biometricPromptInfo = BiometricPrompt.Builder(this).setTitle("Log In").setAllowedAuthenticators(DEVICE_CREDENTIAL or BIOMETRIC_STRONG).build()

        val executor = ContextCompat.getMainExecutor(this)
        val cancelSignal = CancellationSignal()
        cancelSignal.setOnCancelListener { textBox.text = "Cancelled"  }
        val authenticationCallBack = object: BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                textBox.text = "Login Failed"
            }
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                textBox.text = "Done5"
                Log.v("eCard","Main: Starting Main Screen")
                startActivity(Intent(this@MainActivity, MainScreen::class.java))
            }
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                textBox.text = "Error"
            }
        }
        biometricPromptInfo.authenticate(cancelSignal, executor, authenticationCallBack)
    }
}