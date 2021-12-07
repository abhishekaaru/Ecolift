package com.example.ecolift.MainActivities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecolift.Data_Classes.AllPostedRide
import com.example.ecolift.Data_Classes.User
import com.example.ecolift.Retrofit.ServiceBuilder
import com.example.ecolift.Retrofit.SessionManager
import com.example.ecolift.StartActivities.LoginActivity
import com.example.ecolift.databinding.FragmentProfileBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProfile()
        getAllPostedRide()
        binding.logoutBtn.setOnClickListener {
            logout()
        }

    }

    fun getAllPostedRide(){

        val retrofit = ServiceBuilder()
        val retrofitBuilder = retrofit.retrofitBuilder
        val sessionManager = SessionManager(this.requireContext())
        binding.progressBarForList.visibility = View.VISIBLE


        retrofitBuilder.getAllPostedRide(token = "Bearer ${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<List<AllPostedRide>>{
                override fun onResponse(
                    call: Call<List<AllPostedRide>>,
                    response: Response<List<AllPostedRide>>
                ) {

                    if(response.isSuccessful){

                        binding.progressBarForList.visibility = View.GONE
                        val responseBody = response.body()!!
                        binding.allPostedrideRecylerView.layoutManager = LinearLayoutManager(requireContext())
                        val adapter = PostRideListRecyclerView()
                        adapter.updateAll(responseBody)
                        binding.allPostedrideRecylerView.adapter = adapter
                        Log.d("succes", responseBody.toString())

                    }
                    else{
                        binding.progressBarForList.visibility = View.GONE
                        Log.d("unsucces",response.toString())
                        Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<AllPostedRide>>, t: Throwable) {
                    binding.progressBarForList.visibility = View.GONE
                    Log.d("unsucces",t.toString())
                    Toast.makeText(requireContext(), "Connection Problem", Toast.LENGTH_SHORT).show()
                }

            })

    }


    fun getProfile(){

        val retrofit = ServiceBuilder()
        val retrofitBuilder = retrofit.retrofitBuilder
        val sessionManager = SessionManager(this.requireContext())
        binding.progressBar.visibility = View.VISIBLE


        retrofitBuilder.getProfile(token = "Bearer ${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<User>{
                override fun onResponse(
                    call: Call<User>,
                    response: Response<User>
                ) {

                    if(response.isSuccessful){
                        binding.progressBar.visibility = View.GONE
                        val responseBody = response.body()!!
                        binding.userName.text = responseBody.name
                        binding.userEmail.text = responseBody.email
                        binding.userMobile.text = responseBody.mobile
                    }
                    else{
                        binding.progressBar.visibility = View.GONE
                        Log.d("unsucces",response.toString())
                        Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    Log.d("unsucces",t.toString())
                    Toast.makeText(requireContext(), "Connection Problem", Toast.LENGTH_SHORT).show()
                }

            })

    }

    fun logout(){

        val retrofit = ServiceBuilder()
        val retrofitBuilder = retrofit.retrofitBuilder
        val sessionManager = SessionManager(this.requireContext())
        binding.progressBar.visibility = View.VISIBLE


        retrofitBuilder.logout(token = "Bearer ${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {

                    if(response.isSuccessful){
                        binding.progressBar.visibility = View.GONE
                        sessionManager.clearAllTokens()
                        startActivity(Intent(requireContext(),LoginActivity::class.java))
                        requireActivity().finish()
                    }
                    else{
                        binding.progressBar.visibility = View.GONE
                        Log.d("unsucces",response.toString())
                        Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    binding.progressBar.visibility = View.GONE
                    Log.d("unsucces",t.toString())
                    Toast.makeText(requireContext(), "Connection Problem", Toast.LENGTH_SHORT).show()
                }

            })

    }


}