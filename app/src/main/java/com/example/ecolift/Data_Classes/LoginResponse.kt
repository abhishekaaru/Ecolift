package com.example.ecolift.Data_Classes

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("token")
    var authToken: String,

    @SerializedName("user")
    var user: User

)
