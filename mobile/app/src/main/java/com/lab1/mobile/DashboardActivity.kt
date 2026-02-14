package com.lab1.mobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // 1. Retrieve the Token
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("jwt_token", null)

        // 2. Security Check: If no token, kick them back to Login
        if (token == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        tvWelcome.text = "You are Logged In!\n(Token Valid)"

        // 3. Logout Logic
        btnLogout.setOnClickListener {
            // Clear the token
            with(sharedPref.edit()) {
                remove("jwt_token")
                apply()
            }

            // Go back to Login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}