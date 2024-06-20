package com.bangkit.narsumku.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bangkit.narsumku.R
import com.bangkit.narsumku.data.UserRepository
import com.bangkit.narsumku.data.pref.UserPreference
import com.bangkit.narsumku.data.pref.dataStore
import com.bangkit.narsumku.data.retrofit.ApiConfig
import com.bangkit.narsumku.ui.ViewModelFactory
import com.bangkit.narsumku.ui.login.LoginActivity
import com.bangkit.narsumku.ui.profile.EditProfileActivity

class ProfileFragment : Fragment() {

    private lateinit var fullNameEditText: TextView
    private lateinit var emailEditText: TextView
    private lateinit var btnEdit: Button
    private lateinit var btnDeleteAccount: Button
    private lateinit var btnLogout: Button

    private val profileViewModel: ProfileViewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        // Initialize views
        fullNameEditText = view.findViewById(R.id.FullNameEditText)
        emailEditText = view.findViewById(R.id.EmailEditText)
        btnEdit = view.findViewById(R.id.btnEdit)
        btnDeleteAccount = view.findViewById(R.id.btnDeleteAccount)
        btnLogout = view.findViewById(R.id.btnLogout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe user session
        profileViewModel.getUserSession().observe(viewLifecycleOwner) { user ->
            if (user != null) {
                fullNameEditText.text = user.username
                emailEditText.text = user.email
            }
        }

        // Set up click listeners
        btnEdit.setOnClickListener { handleEditButtonClick() }
        btnDeleteAccount.setOnClickListener { handleDeleteAccountButtonClick() }
        btnLogout.setOnClickListener { handleLogoutButtonClick() }
    }

    private fun handleEditButtonClick() {
        // Navigate to EditProfileActivity
        val intent = Intent(activity, EditProfileActivity::class.java)
        startActivity(intent)
    }

    private fun handleDeleteAccountButtonClick() {
        // Handle delete account button click (dummy functionality)
        // You can add your logic here for deleting account
    }

    private fun handleLogoutButtonClick() {
        // Tampilkan dialog konfirmasi
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmation")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { dialog, _ ->
                // Hapus data pengguna yang masuk secara lokal
                val sharedPref = activity?.getSharedPreferences("user_pref", Context.MODE_PRIVATE)
                val editor = sharedPref?.edit()
                editor?.clear()
                editor?.apply()

                // Panggil fungsi untuk kembali ke halaman login
                navigateToLogin()
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                // Tutup dialog
                dialog.dismiss()
            }
            .show()
    }

    private fun navigateToLogin() {
        // Intent untuk kembali ke halaman login
        val intent = Intent(activity, LoginActivity::class.java)
        // Tambahkan flag untuk membersihkan tumpukan aktivitas sehingga tidak bisa kembali ke ProfileFragment lagi
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish() // Selesaikan aktivitas saat kembali ke halaman login
    }
}