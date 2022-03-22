package com.mttnow.android.app_tmdb.ui.splash_screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mttnow.android.app_tmdb.MainActivity
import com.mttnow.android.app_tmdb.databinding.ActivitySplashScreenBinding

class SplashScreenActivity: AppCompatActivity() {

    lateinit var bindingClass: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.ivScreen.alpha = 0f
        bindingClass.ivScreen.animate().setDuration(2000).alpha(1f).withEndAction{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

    }
}