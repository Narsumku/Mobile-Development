package com.bangkit.narsumku.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.databinding.ActivitySpeakerDetailBinding
import com.bangkit.narsumku.ui.ViewModelFactory
import com.bumptech.glide.Glide

class SpeakerDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpeakerDetailBinding
    private val viewModel by viewModels<SpeakerDetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpeakerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val speakerId = intent.getStringExtra("SPEAKER_ID") ?: return

        viewModel.speakerDetail.observe(this) { result ->
            when (result) {
                is Results.Loading -> {
                    // Tampilkan loading
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Results.Success -> {
                    // Tampilkan data speaker
                    val speaker = result.data
                    val speakerRating = speaker.recentExperience
                    val quality = when {
                        speakerRating < 2 -> "Bad"
                        speakerRating in 2..3 -> "Medium"
                        speakerRating > 3 -> "Good"
                        else -> "Unknown"
                    }
                    binding.progressBar.visibility = View.GONE
                    Glide.with(binding.root.context)
                        .load(speaker.profilePicUrl)
                        .into(binding.ivSpeakerAvatar)
                    binding.tvSpeakerName.text = speaker.fullName
                    binding.tvStars.text = speakerRating.toString()
                    binding.tvQuality.text = quality
                    binding.tvSpeakerExperience.text = speaker.experience
                    binding.tvCategory1.text = speaker.category1
                    binding.tvCategory2.text = speaker.category2
                    binding.tvCategory3.text = speaker.category3
                }

                is Results.Error -> {
                    // Tampilkan pesan error
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        viewModel.getSpeakerDetail(speakerId)
    }
}