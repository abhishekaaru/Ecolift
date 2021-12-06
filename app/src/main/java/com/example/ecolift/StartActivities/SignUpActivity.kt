package com.example.ecolift.StartActivities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.ecolift.Data_Classes.CreateUser
import com.example.ecolift.Data_Classes.LoginResponse
import com.example.ecolift.MainActivity
import com.example.ecolift.R
import com.example.ecolift.Retrofit.ServiceBuilder
import com.example.ecolift.Retrofit.SessionManager
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()
        this.window.statusBarColor = Color.rgb(12,104,55)

        val login_back_btn = findViewById<Button>(R.id.login_back_btn)
        val signUpBtn = findViewById<Button>(R.id.signUp_btn)
        sessionManager = SessionManager(this)

        login_back_btn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        signUpBtn.setOnClickListener {
            createNewUser()
        }


    }


    fun createNewUser(){

        val signupName = findViewById<TextInputEditText>(R.id.signup_name)
        val email = findViewById<TextInputEditText>(R.id.signup_email)
        val mobileNo = findViewById<TextInputEditText>(R.id.signup_mobile)
        val password = findViewById<TextInputEditText>(R.id.signup_password)
        val signUpProgressBar = findViewById<ProgressBar>(R.id.sign_up_progress_bar)
        val retrofit = ServiceBuilder()
        val retrofitBuilder = retrofit.retrofitBuilder
        signUpProgressBar.visibility = View.VISIBLE

        if(isEntryValid()){

            val Name = signupName.text.toString().trim()
            val Email = email.text.toString().trim()
            val MobileNo = mobileNo.text.toString().trim()
            val Password = password.text.toString().trim()
            val intent = Intent(this, MainActivity::class.java)


            retrofitBuilder.createUser(CreateUser(name = Name,email = Email, mobile = MobileNo, password = Password))
                .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {

                    if(response.isSuccessful){

                        signUpProgressBar.visibility = View.GONE
                        val loginResponse = response.body()!!
                        sessionManager.clearAllTokens()
                        sessionManager.saveAuthToken(loginResponse.authToken)
                        Toast.makeText(this@SignUpActivity, "Logged In", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        finish()
                    }
                    else{
                        signUpProgressBar.visibility = View.GONE
                        Log.d("unsuccess login", response.toString())
                        Toast.makeText(this@SignUpActivity, "User Already Exist", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    signUpProgressBar.visibility = View.GONE
                    Log.d("unsucces full login", t.toString())
                    Toast.makeText(this@SignUpActivity, "Connection Problem", Toast.LENGTH_LONG).show()
                }

            })

        }
        else {
            signUpProgressBar.visibility = View.GONE
            Toast.makeText(this, "Fill Details Properly", Toast.LENGTH_SHORT).show()
        }

    }


    // checking that is any form content is blank or not. if blank return false or true
    private fun isEntryValid(): Boolean {

        val signupName = findViewById<TextInputEditText>(R.id.signup_name)
        val email = findViewById<TextInputEditText>(R.id.signup_mobile)
        val mobileNo = findViewById<TextInputEditText>(R.id.signup_email)
        val password = findViewById<TextInputEditText>(R.id.signup_password)

        return !signupName.text?.isBlank()!! && !email.text?.isBlank()!! && !mobileNo.text?.isBlank()!! && !password.text?.isBlank()!!

    }


}