package com.practice.ChatAI.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.practice.ChatAI.Fragments.BardAI
import com.practice.ChatAI.Fragments.ChatGpt
import com.practice.ChatAI.Fragments.Greeting
import com.practice.ChatAI.Fragments.NoNetwork
import com.practice.ChatAI.R

class MainActivity : AppCompatActivity() {

    val fragmentManager = supportFragmentManager
    lateinit var bottomview: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ChatAI)
        setContentView(R.layout.activity_main)
        bottomview = findViewById<BottomNavigationView>(R.id.bottomNav)
        val sharedPreferences = getSharedPreferences("Greetings", Context.MODE_PRIVATE)
        val FirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

        if (FirstLaunch) {
            replaceWithFragment(Greeting())
            sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
            navBarControl()
        } else {
            if (isConnectedToInternet()) {
                // Internet connection is available
                // Perform your desired actions here
                replaceWithFragment(BardAI())
                navBarControl()
            } else replaceWithFragment(NoNetwork())
        }
    }

    private fun navBarControl() {
        bottomview.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottomItem1 -> {
                    if (fragmentManager.findFragmentById(R.id.frameLayout) != ChatGpt())
                        replaceWithFragment(ChatGpt())
                }
                R.id.bottomItem2 -> {
                    if (fragmentManager.findFragmentById(R.id.frameLayout) != BardAI())
                        replaceWithFragment(BardAI())
                }
            }
            true
        }
    }

    @SuppressLint("InflateParams")
    override fun onBackPressed() {
        super.onBackPressed()
        if (fragmentManager.backStackEntryCount == 0) {
            finish()
        }
        val currentFragment = fragmentManager.findFragmentById(R.id.frameLayout)
        if (currentFragment is BardAI) bottomview.menu.findItem(R.id.bottomItem2).isChecked = true
        else bottomview.menu.findItem(R.id.bottomItem1).isChecked = true
    }

    private fun isConnectedToInternet(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }


    private fun replaceWithFragment(fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }
}