package com.example.ecolift.viewModels

import android.app.Application
import com.example.ecolift.Database.PostDatabase

class EcoliftApplication: Application() {

    val database: PostDatabase by lazy { PostDatabase.getDatabase(this) }
}