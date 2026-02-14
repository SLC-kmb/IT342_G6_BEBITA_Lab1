package com.lab1.mobile // Make sure this matches your package!

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lab1.mobile.api.RetrofitClient
import com.lab1.mobile.model.AuthResponse
import com.lab1.mobile.model.LoginRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvRegister = findViewById<TextView>(R.id.tvRegisterLink)

        // Handle Login Button Click
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            val request = LoginRequest(email, password)

            RetrofitClient.instance.login(request).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) =
                    if (response.isSuccessful && response.body() != null) {
                        val token = response.body()!!.token

                        // 1. Save Token to SharedPreferences
                        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putString("jwt_token", token)
                            apply()
                        }

                        Toast.makeText(this@LoginActivity, "Login Successful!", Toast.LENGTH_SHORT).show()

                        // 2. Navigate to Dashboard (We will create this later)
                        // val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                        // startActivity(intent)
                        // finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                    }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Handle Register Link Click
        tvRegister.setOnClickListener {
            // Navigate to Register Activity (We will create this later)
            Toast.makeText(this, "Go to Register", Toast.LENGTH_SHORT).show()
        }
    }
}