package com.example.ecolift.PostRideActivities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.ecolift.Data_Classes.CreateRide
import com.example.ecolift.Data_Classes.User
import com.example.ecolift.MainActivities.MainActivity
import com.example.ecolift.R
import com.example.ecolift.Retrofit.ServiceBuilder
import com.example.ecolift.Retrofit.SessionManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        supportActionBar?.hide()
        this.window.statusBarColor = Color.rgb(33, 34, 38)
        getProfile()

        val timeBtn = findViewById<TextInputEditText>(R.id.post_time)
        val dateBtn = findViewById<TextInputEditText>(R.id.post_date)
        val postRideBtn = findViewById<Button>(R.id.post_action_btn)


        timeBtn.setOnClickListener {
            openTimePicker()
        }

        dateBtn.setOnClickListener {
            openDatePicker()
        }

        postRideBtn.setOnClickListener {
            createRide()
        }


    }

    fun getProfile() {

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

                    if (response.isSuccessful) {
                        progressBar.visibility = View.GONE
                        val responseBody = response.body()!!
                        userName.text = responseBody.name
                        userEmail.text = responseBody.email
                        userMobile.text = responseBody.mobile
                    } else {
                        progressBar.visibility = View.GONE
                        Log.d("unsucces", response.toString())
                        Toast.makeText(this@PostActivity, "Something Wrong", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Log.d("unsucces", t.toString())
                    Toast.makeText(this@PostActivity, "Connection Problem", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }


    fun createRide() {


        val retrofit = ServiceBuilder()
        val retrofitBuilder = retrofit.retrofitBuilder
        val sessionManager = SessionManager(this)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val PostPickup = findViewById<TextInputEditText>(R.id.post_pickup)
        val PostDestination = findViewById<TextInputEditText>(R.id.post_dest)
        val PostDate = findViewById<TextInputEditText>(R.id.post_date)
        val PostTime = findViewById<TextInputEditText>(R.id.post_time)
        val PostSeats = findViewById<TextInputEditText>(R.id.post_seat)
        val PostAmount = findViewById<TextInputEditText>(R.id.post_price)
        progressBar.visibility = View.VISIBLE


        if (isEntryValid()) {

            val Pickup = PostPickup.text.toString().trim()
            val Destination = PostDestination.text.toString().trim()
            val Date = PostDate.text.toString().trim()
            val Time = PostTime.text.toString().trim()
            val Seats = PostSeats.text.toString().trim()
            val Amount = PostAmount.text.toString().trim()

            retrofitBuilder.createRide(
                token = "Bearer ${sessionManager.fetchAuthToken()}",
                CreateRide(
                    pickup = Pickup,
                    destination = Destination,
                    date = Date,
                    time = Time,
                    seats = Seats,
                    amount = Amount
                )
            )
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {

                        if (response.isSuccessful) {
                            progressBar.visibility = View.GONE
                            Toast.makeText(this@PostActivity, "Ride Created", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(Intent(this@PostActivity, MainActivity::class.java))
                            finish()
                        } else {
                            progressBar.visibility = View.GONE
                            Toast.makeText(this@PostActivity, "Something Wrong", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@PostActivity, "Connection Problem", Toast.LENGTH_SHORT)
                            .show()
                    }

                })

        } else {
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Fill Details Properly", Toast.LENGTH_SHORT).show()
        }

    }

    private fun isEntryValid(): Boolean {

        val PostPickup = findViewById<TextInputEditText>(R.id.post_pickup)
        val PostDestination = findViewById<TextInputEditText>(R.id.post_dest)
        val PostDate = findViewById<TextInputEditText>(R.id.post_date)
        val PostTime = findViewById<TextInputEditText>(R.id.post_time)
        val PostSeats = findViewById<TextInputEditText>(R.id.post_seat)
        val PostAmount = findViewById<TextInputEditText>(R.id.post_price)

        return !(PostPickup.text!!.isBlank()
                || PostDestination.text!!.isBlank()
                || PostDate.text!!.isBlank()
                || PostTime.text!!.isBlank()
                || PostSeats.text!!.isBlank()
                || PostAmount.text!!.isBlank())


    }


    // Material Time Picker
    private fun openTimePicker() {
        val isSystem24hour = is24HourFormat(this)
        val clockFormat = if (isSystem24hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        var timeBtn = findViewById<TextInputEditText>(R.id.post_time)

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(clockFormat)
            .setHour(12)
            .setMinute(10)
            .setTitleText("Set Time")
            .build()
        picker.show(this.supportFragmentManager, "Tag")

        picker.addOnPositiveButtonClickListener {
            val ampm: String

            if (picker.hour > 12) {
                ampm = "PM"
                timeBtn.setText("${picker.hour - 12} : ${picker.minute} ${ampm}")
            } else if (picker.hour == 12) {
                ampm = "PM"
                timeBtn.setText("${picker.hour} : ${picker.minute} ${ampm}")
            } else {
                ampm = "AM"
                timeBtn.setText("${picker.hour} : ${picker.minute} ${ampm}")
            }
        }
    }

    fun openDatePicker() {
        val today = Calendar.getInstance()

        var dateBtn = findViewById<TextInputEditText>(R.id.post_date)
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
        datePicker.show(this.supportFragmentManager, "Tag")

        datePicker.addOnPositiveButtonClickListener {
            dateBtn.setText(SimpleDateFormat("dd MMMM yyyy").format(datePicker.selection))
        }


    }


}