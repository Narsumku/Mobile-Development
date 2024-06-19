package com.bangkit.narsumku.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.narsumku.databinding.ActivitySplashBinding
import com.bangkit.narsumku.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            goToMainActivity()
        }, SPLASH_DELAY)
    }

    private fun goToMainActivity() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private companion object {
        private const val SPLASH_DELAY = 2500L
    }
}