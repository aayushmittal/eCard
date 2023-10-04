package com.example.ecard


import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.hardware.biometrics.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.postDelayed
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val eCardLogo: TextView = findViewById(R.id.Logo_eCard)
        val loginStatus : TextView = findViewById(R.id.Login_Status)
        eCardLogo.text = "eCard"

        Log.v("Main Activity","onCreate: Starting Biometric")

        val biometricPromptInfo = BiometricPrompt.Builder(this).setTitle("Log In")
            .setAllowedAuthenticators(DEVICE_CREDENTIAL or BIOMETRIC_STRONG).build()

        val executor = ContextCompat.getMainExecutor(this)
        val cancelSignal = CancellationSignal()
        cancelSignal.setOnCancelListener { loginStatus.text = "Login Cancelled"  }
        val authenticationCallBack = object: BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.e("Main Activity","onAuthenticationFailed : Authentication Failed")
                loginStatus.text = "Login Failed"
            }
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                Log.v("Main Activity","onAuthenticationSucceeded : Authentication Done")
                loginStatus.text = "Logged In"
                Handler().postDelayed(Runnable {
                    Log.v("Main Activity","onAuthenticationSucceeded : Starting Tag List")
                    startActivity(Intent(this@MainActivity, TagList::class.java))
                }, 1000)
            }
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                Log.e("Main Activity","onAuthenticationError : Authentication Error")
                loginStatus.text = "Login Error"
            }
        }
        biometricPromptInfo.authenticate(cancelSignal, executor, authenticationCallBack)
    }
}