package com.example.ecolift.BookRideActivities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecolift.Data_Classes.AllPostedRide
import com.example.ecolift.Data_Classes.Search_dataClass
import com.example.ecolift.R
import com.example.ecolift.Retrofit.ServiceBuilder
import com.example.ecolift.Retrofit.SessionManager
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        supportActionBar?.hide()
        this.window.statusBarColor = Color.rgb(33, 34, 38)


        val searchbtn = findViewById<Button>(R.id.search_action_btn)

        searchbtn.setOnClickListener{
            getAvilableRide()
        }
    }


    fun getAvilableRide(){

        val retrofit = ServiceBuilder()
        val retrofitBuilder = retrofit.retrofitBuilder
        val sessionManager = SessionManager(this)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val pickup = findViewById<TextInputEditText>(R.id.search_pickup)
        val destination = findViewById<TextInputEditText>(R.id.search_dest)
        val AllAvailableRide = findViewById<RecyclerView>(R.id.allAvailableRide_RV)
        progressBar.visibility = View.VISIBLE


        if (isEntryValid()) {

            val pickup_text = pickup.text.toString().trim()
            val destination_text = destination.text.toString().trim()

            retrofitBuilder.getAvailableRide(token = "Bearer ${sessionManager.fetchAuthToken()}", Search_dataClass(pickup = pickup_text, destination = destination_text))
                .enqueue(object : Callback<List<AllPostedRide>>{
                    override fun onResponse(
                        call: Call<List<AllPostedRide>>,
                        response: Response<List<AllPostedRide>>
                    ) {
                        if(response.isSuccessful){

                            progressBar.visibility = View.GONE
                            val responseBody = response.body()!!
                            if(responseBody.isNotEmpty()) {
                                AllAvailableRide.layoutManager = LinearLayoutManager(this@SearchActivity)
                                val adapter = SearchAvailableRideRecylerView()
                                adapter.updateAll(responseBody)
                                AllAvailableRide.adapter = adapter
                                Log.d("succes", responseBody.toString())
                            }
                            else
                                Toast.makeText(this@SearchActivity, "Not Found Any Ride", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            progressBar.visibility = View.GONE
                            Log.d("unsucces",response.toString())
                            Toast.makeText(this@SearchActivity, "Something Wrong", Toast.LENGTH_SHORT).show()

                        }

                    }

                    override fun onFailure(call: Call<List<AllPostedRide>>, t: Throwable) {
                        progressBar.visibility = View.GONE
                        Log.d("error",t.toString())
                    }

                })

        }
        else{
            progressBar.visibility = View.GONE
            Toast.makeText(this, "Fill Details Properly", Toast.LENGTH_SHORT).show()
        }


    }

    // checking that is any form content is blank or not. if blank return false or true
    private fun isEntryValid(): Boolean {

        val pickup = findViewById<TextInputEditText>(R.id.search_pickup)
        val destination = findViewById<TextInputEditText>(R.id.search_dest)

        return !(pickup.text!!.isBlank()
                || destination.text!!.isBlank())

    }


}