package com.lab1.mobile.api

import com.lab1.mobile.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/auth/login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>
    @POST("/api/auth/register")
    fun register(@Body request: RegisterRequest): Call<AuthResponse>
}