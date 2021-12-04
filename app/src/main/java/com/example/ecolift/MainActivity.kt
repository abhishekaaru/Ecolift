package com.example.ecolift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val homeFragment = MainFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val  rlTop = findViewById<LinearLayout>(R.id.leftToRightSwipe)
//        rlTop.setOnTouchListener(RelativeLayoutTouchListener(this))
        replaceFragment(homeFragment)

        val bottomBar: BottomNavigationView = findViewById(R.id.main_menu)
        // calling replace funtion if id selected
        bottomBar.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(homeFragment)
                R.id.profile -> replaceFragment(profileFragment)
            }
            true
        }



    }


    // called when you want to replace fragment
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

}