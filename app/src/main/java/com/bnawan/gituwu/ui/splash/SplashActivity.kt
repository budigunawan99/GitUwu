package com.bnawan.gituwu.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.bnawan.gituwu.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this, MainActivity::class.java).run {
                startActivity(this)
                finish()
            }
        }, SPLASH_TIME)
    }

    companion object {
        private const val SPLASH_TIME = 2000L
    }
}