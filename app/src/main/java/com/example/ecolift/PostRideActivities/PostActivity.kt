package com.example.ecolift.PostRideActivities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.ecolift.Data_Classes.User
import com.example.ecolift.R
import com.example.ecolift.Retrofit.ServiceBuilder
import com.example.ecolift.Retrofit.SessionManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        supportActionBar?.hide()
        this.window.statusBarColor = Color.rgb(33,34,38)
        getProfile()

//        timeBtn.setOnClickListener {
//            openTimePicker()
//        }
//
//        dateBtn.setOnClickListener {
//            openDatePicker()
//        }
    }


    fun getProfile(){

        val retrofit = ServiceBuilder()
        val retrofitBuilder = retrofit.retrofitBuilder
        val sessionManager = SessionManager(this)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val userName = findViewById<TextView>(R.id.user_name)
        val userEmail = findViewById<TextView>(R.id.user_email)
        val userMobile = findViewById<TextView>(R.id.user_mobile)
        progressBar.visibility = View.VISIBLE


        retrofitBuilder.getProfile(token = "Bearer ${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<User> {
                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {

                    if(response.isSuccessful){
                        progressBar.visibility = View.GONE
                        val responseBody = response.body()!!
                        userName.text = responseBody.name
                        userEmail.text = responseBody.email
                        userMobile.text = responseBody.mobile
                    }
                    else{
                        progressBar.visibility = View.GONE
                        Log.d("unsucces",response.toString())
                        Toast.makeText(this@PostActivity, "Something Wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Log.d("unsucces",t.toString())
                    Toast.makeText(this@PostActivity, "Connection Problem", Toast.LENGTH_SHORT).show()
                }
            })
    }

    //fun createRide(){


    // Material Time Picker
//    private fun openTimePicker(){
//        val isSystem24hour = is24HourFormat(requireContext())
//        val clockFormat = if(isSystem24hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
//
//        val picker = MaterialTimePicker.Builder()
//            .setTimeFormat(clockFormat)
//            .setHour(12)
//            .setMinute(0)
//            .setTitleText("Set Time")
//            .build()
//        picker.show(childFragmentManager,"Tag")
//
//        picker.addOnPositiveButtonClickListener {
//            var ampm:String
//
//            if(picker.hour >= 12){
//                ampm = "PM"
//            }
//            else{
//                ampm = "AM"
//            }
//
//            timeBtn.editableText.toString() = "${picker.hour} : ${picker.minute} ${ampm}"
//        }
//    }


}