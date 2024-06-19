package com.bangkit.narsumku.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.response.LoginResponse
import com.bangkit.narsumku.databinding.ActivityLoginBinding
import com.bangkit.narsumku.ui.ViewModelFactory
import com.bangkit.narsumku.ui.main.MainActivity
import com.bangkit.narsumku.ui.signup.SignupActivity
import com.bangkit.narsumku.ui.welcome.WelcomeActivity

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {

        val login =
            ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(ANIMATION_DURATION)
        val welcome = ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val intro = ObjectAnimator.ofFloat(binding.tvAppIntro, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmailOrMobileNumber, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val email = ObjectAnimator.ofFloat(binding.EmailEditText, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val layoutEmail = ObjectAnimator.ofFloat(binding.EmailEditTextLayout, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val password = ObjectAnimator.ofFloat(binding.PasswordEditText, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val layoutPassword = ObjectAnimator.ofFloat(binding.PasswordEditTextLayout, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val forgotPassword = ObjectAnimator.ofFloat(binding.tvForgetPassword, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val account = ObjectAnimator.ofFloat(binding.tvNoAccount, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val signup = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)

        val together = AnimatorSet().apply {
            playTogether(account, signup)
        }
        AnimatorSet().apply {
            playSequentially(
                welcome,
                intro,
                tvEmail,
                email,
                layoutEmail,
                tvPassword,
                password,
                layoutPassword,
                forgotPassword,
                login,
                together
            )
            start()
        }
    }

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            val email = binding.EmailEditText.text.toString()
            val password = binding.PasswordEditText.text.toString()

            viewModel.login(email, password)
            viewModel.loginResult.observe(this) { result ->
                when (result) {
                    is Results.Loading -> showLoading(true)
                    is Results.Success -> handleSuccessResult(result.data)
                    is Results.Error -> handleErrorResult(result.error)
                }
            }
        }

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.ivArrowBack.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun handleSuccessResult(response: LoginResponse) {
        showLoading(false)
        AlertDialog.Builder(this).apply {
            setTitle("Yeah!")
            setMessage(response.message)
            setPositiveButton("OKE") { _, _ ->
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            create()
            show()
        }
    }

    private fun handleErrorResult(errorMessage: String) {
        showLoading(false)
        AlertDialog.Builder(this).apply {
            setTitle("OOPS!")
            setMessage(errorMessage)
            setPositiveButton("OKE") { _, _ ->
                Log.d("LoginActivity", "User clicked OK on error dialog")
                Toast.makeText(this@LoginActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        @Suppress("DEPRECATION")
        super.onBackPressed()
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private companion object {
        const val ANIMATION_DURATION = 150L
    }
}