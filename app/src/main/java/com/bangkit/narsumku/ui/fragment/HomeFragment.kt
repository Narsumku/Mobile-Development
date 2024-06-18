package com.bangkit.narsumku.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.data.response.PopularSpeaker
import com.bangkit.narsumku.databinding.FragmentHomeBinding
import com.bangkit.narsumku.ui.ViewModelFactory
import com.bangkit.narsumku.ui.adapter.PopularSpeakerAdapter
import com.bangkit.narsumku.ui.adapter.SearchAdapter
import com.bangkit.narsumku.ui.detail.SpeakerDetailActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            observeHomeResponse()
        }
        setupRecyclerViews()
    }

    private fun setupRecyclerViews() {
        binding.recyclerViewPopularSpeakers.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewRecommendedSpeakers.layoutManager = LinearLayoutManager(context)
    }

    private suspend fun observeHomeResponse() {
        when (val popular = homeViewModel.getHomeForPopular()) {
            is Results.Loading -> {
                Log.d("SearchActivity", "Loading data...") // Log loading state
                binding.progressBar.visibility = View.VISIBLE
            }
            is Results.Success -> {
                Log.d("SearchActivity", "Data loaded successfully") // Log success state
                binding.progressBar.visibility = View.GONE
                val adapter = PopularSpeakerAdapter(popular.data, object : PopularSpeakerAdapter.OnItemClickListener {
                    override fun onItemClick(speakerId: String) {
                        openSpeakerDetail(speakerId)
                    }
                })
                binding.recyclerViewPopularSpeakers.adapter = adapter
            }
            is Results.Error -> {
                Log.e("SearchActivity", "Error loading data") // Log error state
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setupPopularSpeakerRecyclerView(speakers: List<PopularSpeaker>) {
        val adapter = PopularSpeakerAdapter(speakers, object : PopularSpeakerAdapter.OnItemClickListener {
            override fun onItemClick(speakerId: String) {
                openSpeakerDetail(speakerId)
            }
        })
        binding.recyclerViewPopularSpeakers.adapter = adapter
    }

    private fun openSpeakerDetail(speakerId: String) {
        val intent = Intent(requireContext(), SpeakerDetailActivity::class.java)
        intent.putExtra("SPEAKER_ID", speakerId)
        startActivity(intent)
    }
}