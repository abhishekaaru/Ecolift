package com.example.ecolift.Retrofit

import com.example.ecolift.Data_Classes.CreateUser
import com.example.ecolift.Data_Classes.LoginRequest
import com.example.ecolift.Data_Classes.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface getInterface {

    @POST("/Login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/Logout")
    fun logout(@Header("Authorization")  token:String):Call<ResponseBody>

    @POST("/user")
    fun createUser(@Body request: CreateUser):Call<LoginResponse>

}