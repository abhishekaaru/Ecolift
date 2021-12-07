package com.example.ecolift.BookRideActivities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ecolift.R

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        supportActionBar?.hide()
        this.window.statusBarColor = Color.rgb(33, 34, 38)

        val searchbtn = findViewById<Button>(R.id.search_action)

        searchbtn.setOnClickListener{
            startActivity(Intent(this,BookActivity:: class.java))
        }
    }
}