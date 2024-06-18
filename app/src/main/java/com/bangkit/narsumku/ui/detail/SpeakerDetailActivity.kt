package com.bangkit.narsumku.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.response.SpeakerDetailResponse
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
                    binding.progressBar.visibility = View.GONE
                    Glide.with(binding.root.context)
                        .load(speaker.profilePicUrl)
                        .into(binding.ivSpeakerAvatar)
                    binding.tvSpeakerName.text = speaker.fullName
                    binding.tvOccupation.text = speaker.occupation
                    binding.tvEmail.text = speaker.email
                    binding.tvHeadline.text = speaker.headline
                    if(speaker.summary != null) {
                        binding.tvSummary.text = speaker.summary
                    } else {
                        binding.tvSummary.text = "Summary for this speaker is not set yet"
                    }
                    binding.tvRecent.text = speaker.recentExperience.toString()
                    binding.tvExperience.text = speaker.experience
                    val category1 = speaker.category1
                    val category2 = speaker.category2
                    val category3 = speaker.category3


                    binding.tvCategory.text = listOfNotNull(category1, category2, category3).joinToString("\n")
                    setupShareButton(speaker)
                }

                is Results.Error -> {
                    // Tampilkan pesan error
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        viewModel.getSpeakerDetail(speakerId)
    }

    private fun setupShareButton(speaker: SpeakerDetailResponse) {
        binding.btnShare.setOnClickListener {
            val shareText = """
                Speaker: ${speaker.fullName}
                Occupation: ${speaker.occupation}
                Email: ${speaker.email}
                Headline: ${speaker.headline}
                Summary: ${speaker.summary ?: "Not set"}
                Recent Experience: ${speaker.recentExperience}
                Experience: ${speaker.experience}
                Categories: ${listOfNotNull(speaker.category1, speaker.category2, speaker.category3).joinToString("\n")}
            """.trimIndent()

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Share Speaker Details via"))
        }
    }
}