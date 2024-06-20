package com.bangkit.narsumku.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.narsumku.R
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.databinding.ActivitySignupBinding
import com.bangkit.narsumku.ui.ViewModelFactory
import com.bangkit.narsumku.ui.custom.EmailEditText
import com.bangkit.narsumku.ui.custom.NameEditText
import com.bangkit.narsumku.ui.custom.PasswordEditText
import com.bangkit.narsumku.ui.login.LoginActivity
import com.bangkit.narsumku.ui.welcome.WelcomeActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val viewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var nameEditText: NameEditText
    private lateinit var emailEditText: EmailEditText
    private lateinit var passwordEditText: PasswordEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showLoading(false)
        setupAction()
        playAnimation()
    }

    private fun playAnimation() {

        val register = ObjectAnimator.ofFloat(binding.btnSignup, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val tvFullName = ObjectAnimator.ofFloat(binding.tvFullName, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val name = ObjectAnimator.ofFloat(binding.FullNameEditText, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val layoutName = ObjectAnimator.ofFloat(binding.FullNameEditTextLayout, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val password = ObjectAnimator.ofFloat(binding.PasswordEditText, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val layoutPassword = ObjectAnimator.ofFloat(binding.PasswordEditTextLayout, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val tvEmail =
            ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(ANIMATION_DURATION)
        val email = ObjectAnimator.ofFloat(binding.EmailEditText, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val layoutEmail = ObjectAnimator.ofFloat(binding.EmailEditTextLayout, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val tvMobileNumber = ObjectAnimator.ofFloat(binding.tvMobileNumber, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val mobileNumber = ObjectAnimator.ofFloat(binding.MobileNumberEditText, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val layoutMobilePhone =
            ObjectAnimator.ofFloat(binding.MobileNumberEditTextLayout, View.ALPHA, 1f)
                .setDuration(ANIMATION_DURATION)
        val tvDate = ObjectAnimator.ofFloat(binding.tvDateOfBirth, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val date = ObjectAnimator.ofFloat(binding.DateOfBirthEditText, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val layoutDate = ObjectAnimator.ofFloat(binding.DateOfBirthEditTextLayout, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val tvContinue = ObjectAnimator.ofFloat(binding.tvByContinue, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val tvTerms =
            ObjectAnimator.ofFloat(binding.tvTerms, View.ALPHA, 1f).setDuration(ANIMATION_DURATION)
        val tvAnd =
            ObjectAnimator.ofFloat(binding.tvAnd, View.ALPHA, 1f).setDuration(ANIMATION_DURATION)
        val tvPolicy =
            ObjectAnimator.ofFloat(binding.tvPolicy, View.ALPHA, 1f).setDuration(ANIMATION_DURATION)
        val account = ObjectAnimator.ofFloat(binding.tvNoAccount, View.ALPHA, 1f)
            .setDuration(ANIMATION_DURATION)
        val signup =
            ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(ANIMATION_DURATION)

        val together1 = AnimatorSet().apply {
            playTogether(tvContinue, tvTerms, tvAnd, tvPolicy)
        }
        val together2 = AnimatorSet().apply {
            playTogether(account, signup)
        }
        AnimatorSet().apply {
            playSequentially(
                tvFullName,
                name,
                layoutName,
                tvEmail,
                email,
                layoutEmail,
                tvPassword,
                password,
                layoutPassword,
                tvMobileNumber,
                mobileNumber,
                layoutMobilePhone,
                tvDate,
                date,
                layoutDate,
                register,
                together1,
                together2
            )
            start()
        }
    }

    private fun setupAction() {
        binding.btnSignup.setOnClickListener {
            val name = binding.FullNameEditText.text.toString()
            val email = binding.EmailEditText.text.toString()
            val password = binding.PasswordEditText.text.toString()

            nameEditText = binding.FullNameEditText
            emailEditText = binding.EmailEditText
            passwordEditText = binding.PasswordEditText

            val isNameValid = name.isNotBlank()
            val isEmailValid = email.isNotBlank()
            val isPasswordValid = password.length >= MIN_PASSWORD_LENGTH

            if (isNameValid && isEmailValid && isPasswordValid) {
                viewModel.register(name, email, password)

                viewModel.registrationResult.observe(this) { result ->
                    showLoading(result is Results.Loading)

                    when (result) {
                        is Results.Success -> {
                            showLoading(false)
                            result.data.let { data ->
                                data.message?.let { message -> showSuccessDialog(message) }
                            }
                        }

                        is Results.Error -> {
                            showLoading(false)
                            showErrorDialog(result.error)
                        }

                        is Results.Loading -> showLoading(true)
                    }
                }
            } else {
                if (!isNameValid) {
                    nameEditText.error = getString(R.string.error_empty_name)
                }
                if (!isEmailValid) {
                    emailEditText.error = getString(R.string.error_empty_email)
                }
                if (!isPasswordValid) {
                    passwordEditText.error = getString(R.string.password_too_short)
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.ivArrowBack.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showSuccessDialog(message: String) {
        showDialog(getString(R.string.success_title), message) { finish() }
    }

    private fun showErrorDialog(errorMessage: String) {
        showDialog(
            getString(R.string.error_title),
            errorMessage
        ) {
            Log.e("SignupActivity", "Error: $errorMessage")
        }
    }

    private fun showDialog(title: String, message: String, positiveAction: () -> Unit) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { _, _ -> positiveAction.invoke() }
            show()
        }
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
        const val MIN_PASSWORD_LENGTH = 8
    }
}