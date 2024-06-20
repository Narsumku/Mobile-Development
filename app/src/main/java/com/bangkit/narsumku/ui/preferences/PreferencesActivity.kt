package com.bangkit.narsumku.ui.preferences

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bangkit.narsumku.R
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.databinding.ActivityPreferencesBinding
import com.bangkit.narsumku.ui.ViewModelFactory
import com.bangkit.narsumku.ui.main.MainActivity
import com.bangkit.narsumku.ui.welcome.WelcomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PreferencesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreferencesBinding
    private val preferencesViewModel: PreferencesViewModel by viewModels { ViewModelFactory.getInstance(this) }
    private var userId: String? = null
    private val handler = Handler(Looper.getMainLooper())
    private val checkInterval: Long = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val checkBoxBusiness = findViewById<CheckBox>(R.id.checkBoxBussiness)
        val checkBoxEntertainment = findViewById<CheckBox>(R.id.checkBoxEntertainment)
        val checkBoxPolitics = findViewById<CheckBox>(R.id.checkBoxPolitics)
        val checkBoxSport = findViewById<CheckBox>(R.id.checkBoxSport)
        val checkBoxTech = findViewById<CheckBox>(R.id.checkBoxTech)
        val checkBoxHealthcare = findViewById<CheckBox>(R.id.checkBoxHealthcare)
        val checkBoxAcademic = findViewById<CheckBox>(R.id.checkBoxAcademic)
        val checkBoxMedia = findViewById<CheckBox>(R.id.checkBoxMedia)

        preferencesViewModel.getUserSession().observe(this) { sessionUserId ->
            userId = sessionUserId
        }

        binding.btnSetPreferences.setOnClickListener {
            userId?.let { id ->
                val business = if (checkBoxBusiness.isChecked) "business" else ""
                val entertainment = if (checkBoxEntertainment.isChecked) "entertainment" else ""
                val politics = if (checkBoxPolitics.isChecked) "politics" else ""
                val sport = if (checkBoxSport.isChecked) "sport" else ""
                val tech = if (checkBoxTech.isChecked) "tech" else ""
                val healthcare = if (checkBoxHealthcare.isChecked) "healthcare" else ""
                val academic = if (checkBoxAcademic.isChecked) "academic" else ""
                val media = if (checkBoxMedia.isChecked) "media" else ""

                lifecycleScope.launch {
                    val result = preferencesViewModel.setPreferences(
                        userId = id,
                        business = business,
                        entertainment = entertainment,
                        politics = politics,
                        sport = sport,
                        tech = tech,
                        healthcare = healthcare,
                        academic = academic,
                        media = media
                    )

                    if (result is Results.Success) {
                        Toast.makeText(this@PreferencesActivity, "Preferences updated successfully!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@PreferencesActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@PreferencesActivity, "Your preferences have been set.", Toast.LENGTH_SHORT).show()
                    }
                }
            } ?: run {
                Toast.makeText(this, "User ID not available.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnHome.setOnClickListener {
            startActivity(Intent(this@PreferencesActivity, MainActivity::class.java))
            finish()
        }
    }
}