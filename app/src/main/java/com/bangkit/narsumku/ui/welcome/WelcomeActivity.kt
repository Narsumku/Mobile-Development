package com.bangkit.narsumku.ui.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.narsumku.databinding.ActivityWelcomeBinding
import com.bangkit.narsumku.ui.login.LoginActivity
import com.bangkit.narsumku.ui.signup.SignupActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {

        val logo = ObjectAnimator.ofFloat(binding.ivLogo, View.ALPHA, 1f).setDuration(ANIMATION_DURATION)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(ANIMATION_DURATION)
        val signup = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1f).setDuration(ANIMATION_DURATION)
        val appName = ObjectAnimator.ofFloat(binding.tvAppName, View.ALPHA, 1f).setDuration(ANIMATION_DURATION)
        val motto = ObjectAnimator.ofFloat(binding.tvMotto, View.ALPHA, 1f).setDuration(ANIMATION_DURATION)
        val greetings = ObjectAnimator.ofFloat(binding.tvGreetings, View.ALPHA, 1f).setDuration(ANIMATION_DURATION)

        val together = AnimatorSet().apply {
            playTogether(login, signup)
        }
        AnimatorSet().apply {
            playSequentially(logo, appName, motto, greetings, together)
            start()
        }
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private companion object {
        const val ANIMATION_DURATION = 150L
    }
}