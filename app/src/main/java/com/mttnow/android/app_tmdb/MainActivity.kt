package com.mttnow.android.app_tmdb

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.mttnow.android.app_tmdb.data.MAIN
import com.mttnow.android.app_tmdb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navControl: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MAIN = this // финт ушами чтоб постоянно иметь доступ к MainActivity

        val navView: BottomNavigationView = binding.navView

        navControl = findNavController(R.id.nav_host_fragment_activity_main)


//         val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_movie,
                R.id.navigation_movie_top,
                R.id.navigation_search
            )
        )
        setupActionBarWithNavController(navControl, appBarConfiguration)
        navView.setupWithNavController(navControl)
//        navView.setOnLongClickListener {  }


    }
}