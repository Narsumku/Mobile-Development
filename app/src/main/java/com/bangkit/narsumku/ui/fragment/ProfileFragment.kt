package com.bangkit.narsumku.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.databinding.FragmentProfileBinding
import com.bangkit.narsumku.ui.ViewModelFactory
import com.bangkit.narsumku.ui.profile.EditProfileActivity
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var fullNameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var btnEdit: Button
    private lateinit var btnDeleteAccount: Button
    private lateinit var btnLogout: Button

    private val profileViewModel: ProfileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        fullNameTextView = binding.FullNameEditText
        emailTextView = binding.EmailEditText
        btnEdit = binding.btnEdit
        btnDeleteAccount = binding.btnDeleteAccount
        btnLogout = binding.btnLogout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.getUserSession().observe(viewLifecycleOwner) { user ->
            if (user != null) {
                viewLifecycleOwner.lifecycleScope.launch {
                    when (val result = profileViewModel.getUser(user.userId)) {
                        is Results.Success -> {
                            result.data.dataUser?.let { userData ->
                                fullNameTextView.text = userData.username
                                emailTextView.text = userData.email
                                Log.d("UserData", "Username: ${userData.username}, Email: ${userData.email}")
                            }
                        }
                        is Results.Error -> {
                            Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                        }
                        is Results.Loading -> {
                            // Handle loading state if needed
                        }
                    }
                }
            }

            btnEdit.setOnClickListener { handleEditButtonClick() }
            btnDeleteAccount.setOnClickListener { handleDeleteAccountButtonClick(user.userId) }
            btnLogout.setOnClickListener { handleLogoutButtonClick() }
        }
    }

    private fun handleEditButtonClick() {
        val intent = Intent(activity, EditProfileActivity::class.java)
        startActivity(intent)
    }

    private fun handleDeleteAccountButtonClick(userId: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmation")
            .setMessage("Are you sure you want to delete this account?")
            .setPositiveButton("Yes") { dialog, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    when (profileViewModel.deleteUser(userId)) {
                        is Results.Loading -> {

                        }
                        is Results.Success -> {
                            profileViewModel.logout()
                        }
                        is Results.Error -> {

                        }
                    }
                }
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun handleLogoutButtonClick() {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmation")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { dialog, _ ->
                profileViewModel.logout()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}