package com.example.ecolift.StartActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ecolift.Data_Classes.LoginRequest
import com.example.ecolift.Data_Classes.LoginResponse
import com.example.ecolift.MainActivity
import com.example.ecolift.R
import com.example.ecolift.Retrofit.ServiceBuilder
import com.example.ecolift.Retrofit.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        val login_btn = findViewById<Button>(R.id.login_btn)
        val create_btn = findViewById<Button>(R.id.create_btn)
        val intent_to_skip = Intent(this, MainActivity::class.java)

        login_btn.setOnClickListener {
            Login()
        }

        create_btn.setOnClickListener {
            startActivity(intent_to_skip)
        }


    }


    private fun Login(){

        val retrofit = ServiceBuilder()
        val retrofitBuilder = retrofit.retrofitBuilder
        val sessionManager = SessionManager(this)

        val emailEditText = findViewById<EditText>(R.id.login_email_btn)
        val passwordEditText = findViewById<EditText>(R.id.login_password_btn)




        if(isEntryValid()){

            val intent = Intent(this, MainActivity::class.java)
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            retrofitBuilder.login(LoginRequest(email = email, password = password)).enqueue(object :
                Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val loginResponse = response.body()!!
                    val myStringBuilder = StringBuilder()

                    sessionManager.saveAuthToken(loginResponse.authToken)


                    Log.d("success login",myStringBuilder.append(loginResponse.user).toString())
                    Toast.makeText(this@LoginActivity, "Logged In", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("unsucces full login","this is not logged in")
                    Toast.makeText(this@LoginActivity, "Something Wrong", Toast.LENGTH_SHORT).show()

                }

            })
        }
        else{

            Toast.makeText(this, "Fill Details Properly", Toast.LENGTH_SHORT).show()
        }

    }


    // checking that is any form content is blank or not. if blank return false or true
    private fun isEntryValid():Boolean{

        val emailEditText = findViewById<EditText>(R.id.login_email_btn)
        val passwordEditText = findViewById<EditText>(R.id.login_password_btn)

        return !(emailEditText.text.isBlank()
                || passwordEditText.text.isBlank())

    }


}