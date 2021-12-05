package com.example.ecolift.Repository

import com.example.ecolift.Data_Classes.LoginRequest
import com.example.ecolift.Retrofit.getInterface

class Ecolift_Repository(private val getInterface:getInterface) {

    suspend fun logout(token:String){
        getInterface.logout(token)
    }
}