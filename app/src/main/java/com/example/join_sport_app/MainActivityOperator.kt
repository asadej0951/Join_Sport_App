package com.example.join_sport_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivityOperator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_operator)
        val navView: BottomNavigationView = findViewById(R.id.nav_view_operator)
        val navController = findNavController(R.id.nav_host_fragment_operator)
        navView.setupWithNavController(navController)
    }
}