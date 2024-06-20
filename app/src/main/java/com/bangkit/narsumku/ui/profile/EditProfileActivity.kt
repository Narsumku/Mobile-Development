package com.bangkit.narsumku.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.databinding.ActivityEditProfileBinding
import com.bangkit.narsumku.ui.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

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

                updateUser(userId, username, email, password)
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
}