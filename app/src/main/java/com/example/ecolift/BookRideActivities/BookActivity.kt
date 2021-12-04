package com.example.ecolift.BookRideActivities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecolift.R

class BookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        supportActionBar?.hide()
        this.window.statusBarColor = Color.rgb(33,34,38)

    }
}