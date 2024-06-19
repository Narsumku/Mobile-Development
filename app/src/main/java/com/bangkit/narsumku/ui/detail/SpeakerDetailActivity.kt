package com.bangkit.narsumku.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.response.SpeakerDetailResponse
import com.bangkit.narsumku.databinding.ActivitySpeakerDetailBinding
import com.bangkit.narsumku.ui.ViewModelFactory
import com.bangkit.narsumku.ui.favorite.FavoriteViewModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class SpeakerDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpeakerDetailBinding
    private val viewModel by viewModels<SpeakerDetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpeakerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val speakerId = intent.getStringExtra("SPEAKER_ID") ?: return

        setupFavorite(speakerId)
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

    private fun setupFavorite(speakerId: String) {
        viewModel.getUserSession().observe(this) { user ->
            if (user.isLogin) {
                binding.btnFavorite.setOnClickListener {
                    lifecycleScope.launch {
                        try {
                            val result = favoriteViewModel.addFavorite(user.userId, speakerId)
                            if (result is Results.Success && result.data.message == "Speaker is already a favorite for this user.") {
                                // Jika speaker sudah menjadi favorit, hapus dari favorit
                                Log.d("SpeakerDetailActivity", "Speaker already a favorite, attempting to remove favorite")
                                favoriteViewModel.deleteFavorite(user.userId, speakerId)
                                Toast.makeText(this@SpeakerDetailActivity, "Removed from favorites", Toast.LENGTH_SHORT).show()
                            } else if (result is Results.Success) {
                                Log.d("SpeakerDetailActivity", "Successfully added to favorites")
                                Toast.makeText(this@SpeakerDetailActivity, "Added to favorites", Toast.LENGTH_SHORT).show()
                            } else if (result is Results.Error) {
                                Log.e("SpeakerDetailActivity", "Failed to add favorite: ${result.error}")
                                Toast.makeText(this@SpeakerDetailActivity, "Failed to add favorite: ${result.error}", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Log.e("SpeakerDetailActivity", "Exception while adding/removing favorite", e)
                            Toast.makeText(this@SpeakerDetailActivity, "Failed to add/remove favorite", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Log.w("SpeakerDetailActivity", "User not logged in")
                Toast.makeText(this@SpeakerDetailActivity, "User not logged in", Toast.LENGTH_SHORT).show()
            }
        }
    }
}