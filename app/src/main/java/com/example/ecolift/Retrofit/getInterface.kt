package com.example.ecolift.Retrofit

import com.example.ecolift.Data_Classes.LoginRequest
import com.example.ecolift.Data_Classes.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface getInterface {

    @POST("/Login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

}