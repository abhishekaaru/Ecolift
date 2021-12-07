package com.example.ecolift.MainActivities

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecolift.BookRideActivities.SearchActivity
import com.example.ecolift.databinding.FragmentMainBinding

import com.example.ecolift.PostRideActivities.PostActivity


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.BookRideBtn.setOnClickListener{
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }
        binding.PostRideBtn.setOnClickListener{
            val intent = Intent(requireContext(), PostActivity::class.java)
            startActivity(intent)
        }
    }

}