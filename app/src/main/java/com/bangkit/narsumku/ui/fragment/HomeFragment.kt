package com.bangkit.narsumku.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.databinding.FragmentHomeBinding
import com.bangkit.narsumku.ui.ViewModelFactory
import com.bangkit.narsumku.ui.adapter.PopularSpeakerAdapter
import com.bangkit.narsumku.ui.adapter.RecommendationSpeakerAdapter
import com.bangkit.narsumku.ui.detail.SpeakerDetailActivity
import com.bangkit.narsumku.ui.favorite.FavoriteActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            observePopularResponse()
        }

        homeViewModel.getUserSession().observe(viewLifecycleOwner) { user ->
            if (user.isLogin) {
                binding.tvUsername.text = user.username
                lifecycleScope.launch {
                    observeRecommendationResponse(user.userId)
                }
            }
        }
        setupRecyclerViews()
        setupFavoriteButton()
    }

    private fun setupRecyclerViews() {
        binding.recyclerViewPopularSpeakers.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewRecommendedSpeakers.layoutManager = LinearLayoutManager(context)
    }

    private fun setupFavoriteButton() {
        binding.btnFavorite.setOnClickListener {
            val intent = Intent(requireContext(), FavoriteActivity::class.java)
            startActivity(intent)
        }
    }

    private suspend fun observePopularResponse() {
        when (val popular = homeViewModel.getHomeForPopular()) {
            is Results.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }

            is Results.Success -> {
                binding.progressBar.visibility = View.GONE
                val adapter = PopularSpeakerAdapter(
                    popular.data,
                    object : PopularSpeakerAdapter.OnItemClickListener {
                        override fun onItemClick(
                            speakerId: String,
                            optionsCompat: ActivityOptionsCompat
                        ) {
                            openSpeakerDetail(speakerId, optionsCompat)
                        }
                    })
                binding.recyclerViewPopularSpeakers.adapter = adapter
            }

            is Results.Error -> {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private suspend fun observeRecommendationResponse(userId: String) {
        when (val recommendation = homeViewModel.getHomeForRecommendation(userId)) {
            is Results.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }

            is Results.Success -> {
                binding.progressBar.visibility = View.GONE
                val adapter = RecommendationSpeakerAdapter(
                    recommendation.data,
                    object : RecommendationSpeakerAdapter.OnItemClickListener {
                        override fun onItemClick(
                            speakerId: String,
                            optionsCompat: ActivityOptionsCompat
                        ) {
                            openSpeakerDetail(speakerId, optionsCompat)
                        }
                    })
                binding.recyclerViewRecommendedSpeakers.adapter = adapter
            }

            is Results.Error -> {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun openSpeakerDetail(speakerId: String, optionsCompat: ActivityOptionsCompat) {
        val intent = Intent(requireContext(), SpeakerDetailActivity::class.java)
        intent.putExtra("SPEAKER_ID", speakerId)
        startActivity(intent, optionsCompat.toBundle())
    }
}