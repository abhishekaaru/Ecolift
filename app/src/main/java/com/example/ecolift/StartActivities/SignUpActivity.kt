package com.example.ecolift.StartActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ecolift.R

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        val login_back_btn = findViewById<Button>(R.id.login_back_btn)

        login_back_btn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


    }
}