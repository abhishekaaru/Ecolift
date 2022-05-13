package com.example.ecolift.BookRideActivities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecolift.Data_Classes.*
import com.example.ecolift.MainActivities.MainActivity
import com.example.ecolift.R
import com.example.ecolift.Retrofit.ServiceBuilder
import com.example.ecolift.Retrofit.SessionManager
import com.google.android.material.textfield.TextInputEditText
import okhttp3.ResponseBody
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)

        supportActionBar?.hide()
        this.window.statusBarColor = Color.rgb(33,34,38)

        getProfile()
        val _id = intent.getStringExtra("_id")
        val pickup = intent.getStringExtra("pickup")
        val destination = intent.getStringExtra("destination")
        val pickup_text = findViewById<TextView>(R.id.pickup_name)
        val destination_text = findViewById<TextView>(R.id.destination_name)
        pickup_text.text = " $pickup"
        destination_text.text = " $destination"

        val book_btn = findViewById<Button>(R.id.book_action_btn)
        book_btn.setOnClickListener {
            createTravller()
        }

    }

    fun getProfile() {

        val retrofit = ServiceBuilder()
        val retrofitBuilder = retrofit.retrofitBuilder
        val sessionManager = SessionManager(this)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val Bookername = findViewById<TextInputEditText>(R.id.book_name)
        val Bookermobileno = findViewById<TextInputEditText>(R.id.book_mobile)
        val Bookeremail = findViewById<TextInputEditText>(R.id.book_email)
        val owner = findViewById<TextView>(R.id.owner_name)
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
                        owner.text = responseBody.name
                        Bookername.setText(responseBody.name)
                        Bookermobileno.setText(responseBody.mobile)
                        Bookeremail.setText(responseBody.email)


                    } else {
                        progressBar.visibility = View.GONE
                        Log.d("unsucces", response.toString())
                        Toast.makeText(this@BookActivity, "Something Wrong", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Log.d("unsucces", t.toString())
                    Toast.makeText(this@BookActivity, "Connection Problem", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    fun createTravller() {

        val retrofit = ServiceBuilder()
        val retrofitBuilder = retrofit.retrofitBuilder
        val sessionManager = SessionManager(this)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val pickup_text = findViewById<TextView>(R.id.pickup_name)
        val destination_text = findViewById<TextView>(R.id.destination_name)
        val intent = Intent(this, MainActivity::class.java)
        progressBar.visibility = View.VISIBLE

        retrofitBuilder.createTravller(token = "Bearer ${sessionManager.fetchAuthToken()}", Search_dataClass(pickup = pickup_text.text.toString().removeRange(0,9), destination = destination_text.text.toString().removeRange(0,14)))
            .enqueue(object : Callback<CreateTravllerResponse>{

                override fun onResponse(
                    call: Call<CreateTravllerResponse>,
                    response: Response<CreateTravllerResponse>
                ) {
                    if(response.isSuccessful){

                        progressBar.visibility = View.GONE
                        val responseBody = response.body()!!
                        Toast.makeText(this@BookActivity, "Booked Succesfull", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    }
                    else{
                        progressBar.visibility = View.GONE
                        Log.d("unsucces",response.toString())
                        Toast.makeText(this@BookActivity, "Something Wrong", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<CreateTravllerResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@BookActivity, "Connection Problem", Toast.LENGTH_SHORT).show()
                    Log.d("error",t.toString())
                }

            })

    }

}