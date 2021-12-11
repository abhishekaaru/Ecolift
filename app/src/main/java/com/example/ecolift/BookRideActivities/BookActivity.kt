package com.example.ecolift.BookRideActivities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.ecolift.R
import org.w3c.dom.Text

class BookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        supportActionBar?.hide()
        this.window.statusBarColor = Color.rgb(33,34,38)


        val _id = intent.getStringExtra("_id")
        val pickup = intent.getStringExtra("pickup")
        val destination = intent.getStringExtra("destination")
        val pickup_text = findViewById<TextView>(R.id.pickup_name)
        val owner_id = findViewById<TextView>(R.id.owner_name)
        val destination_text = findViewById<TextView>(R.id.destination_name)

        owner_id.text = _id
        pickup_text.text = pickup
        destination_text.text = destination

    }
}