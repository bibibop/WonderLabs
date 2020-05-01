package com.android.wonderlabs.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.android.wonderlabs.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bundle = intent.extras
        val navHost =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment?
        supportFragmentManager.beginTransaction().setPrimaryNavigationFragment(navHost).commit()

        val navController = findNavController(R.id.navHostFragment)
        val navGraph = navController.navInflater.inflate(R.navigation.main_navigation)
        navController.setGraph(navGraph, bundle)
    }

    inline fun <reified T: androidx.fragment.app.Fragment>startFragment(f: T, tag: String = T::class.java.name) {
        val bundle = intent.extras
        val navHost =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment?
        supportFragmentManager.beginTransaction().setPrimaryNavigationFragment(navHost).commit()

        val navController = findNavController(R.id.navHostFragment)
        val navGraph = navController.navInflater.inflate(R.navigation.main_navigation)
        navController.setGraph(navGraph, bundle)
    }
}
