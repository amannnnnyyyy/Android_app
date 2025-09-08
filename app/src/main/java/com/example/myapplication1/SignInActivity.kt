package com.example.myapplication1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge


class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.test_view)
        val button = findViewById<Button>(R.id.loginButton)
        val passwordView = findViewById<TextView>(R.id.passwordField)

        var username: String
        var password:String

        button.setOnClickListener {
            username = findViewById<EditText>(R.id.usernameField).text.toString()
            password = passwordView.text.toString()

            if((username == "admin" && password == "admin")){
                val intent = Intent(this, MainActivity::class.java)
                setResult(200, intent)
                finish()
            }else{
                val errorMessageView = findViewById<TextView>(R.id.error_message)
                val params = errorMessageView.layoutParams

                params.height = 50
                errorMessageView.layoutParams = params
                Handler(Looper.getMainLooper()).postDelayed({
                    params.height = 0
                    errorMessageView.layoutParams = params
                },2000)
            }
        }
    }
}