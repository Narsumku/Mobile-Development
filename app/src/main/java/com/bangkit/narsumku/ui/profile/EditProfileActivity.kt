package com.bangkit.narsumku.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bangkit.narsumku.R
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.databinding.ActivityEditProfileBinding
import com.bangkit.narsumku.ui.ViewModelFactory
import com.bangkit.narsumku.ui.custom.EmailEditText
import com.bangkit.narsumku.ui.custom.NameEditText
import com.bangkit.narsumku.ui.custom.PasswordEditText
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var nameEditText: NameEditText
    private lateinit var emailEditText: EmailEditText
    private lateinit var passwordEditText: PasswordEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getUserSession().observe(this) { userModel ->
            val userId = userModel.userId

            binding.btnUpdate.setOnClickListener {
                val username = binding.FullNameEditText.text.toString().trim()
                val email = binding.EmailEditText.text.toString().trim()
                val password = binding.PasswordEditText.text.toString().trim()

                nameEditText = binding.FullNameEditText
                emailEditText = binding.EmailEditText
                passwordEditText = binding.PasswordEditText

                val isNameValid = username.isNotBlank()
                val isEmailValid = email.isNotBlank()
                val isPasswordValid = password.length >= MIN_PASSWORD_LENGTH

                if (isNameValid && isEmailValid && isPasswordValid) {
                    updateUser(userId, username, email, password)
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
        }

        binding.progressBar.visibility = View.GONE
    }

    private fun updateUser(userId: String, username: String, email: String, password: String) {
        lifecycleScope.launch {
            showLoading(true)
            when (val result = viewModel.updateUser(userId, username, email, password)) {
                is Results.Loading -> {

                }
                is Results.Success -> {
                    showLoading(false)
                    showSuccessMessage(result.data.message)
                    finish()
                }
                is Results.Error -> {
                    showLoading(false)
                    showErrorMessage(result.error)
                }
            }
        }
    }

    private fun showSuccessMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showErrorMessage(error: String) {
        Snackbar.make(binding.root, error, Snackbar.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private companion object {
        const val MIN_PASSWORD_LENGTH = 8
    }
}