package com.bangkit.narsumku.ui.favorite

import com.bangkit.narsumku.ui.adapter.FavoriteAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.narsumku.databinding.ActivityFavoriteBinding
import com.bangkit.narsumku.ui.ViewModelFactory
import com.bangkit.narsumku.ui.detail.SpeakerDetailActivity
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModels { ViewModelFactory.getInstance(this) }
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFavoriteRecyclerView()

        binding.ivArrowBack.setOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }

        viewModel.getUserSession().observe(this) { user ->
            if (user.isLogin) {
                fetchFavorites(user.userId)
            }
        }

        viewModel.favorites.observe(this) { favorites ->
            adapter.updateItems(favorites)
            binding.tvEmptyMessage.visibility = if (favorites.isEmpty()) View.VISIBLE else View.GONE
            binding.recyclerViewSpeakers.visibility = if (favorites.isEmpty()) View.GONE else View.VISIBLE
        }

        viewModel.isLoading.asLiveData().observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserSession().value?.let { user ->
            if (user.isLogin) {
                fetchFavorites(user.userId)
            }
        }
    }

    private fun setupFavoriteRecyclerView() {
        adapter = FavoriteAdapter(mutableListOf(), object : FavoriteAdapter.OnItemClickListener {
            override fun onItemClick(speakerId: String, optionsCompat: ActivityOptionsCompat) {
                openSpeakerDetail(speakerId, optionsCompat)
            }
        })
        binding.recyclerViewSpeakers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSpeakers.adapter = adapter
    }

    private fun fetchFavorites(userId: String) {
        lifecycleScope.launch {
            viewModel.getFavorite(userId)
        }
    }

    private fun openSpeakerDetail(speakerId: String, optionsCompat: ActivityOptionsCompat) {
        val intent = Intent(this, SpeakerDetailActivity::class.java)
        intent.putExtra("SPEAKER_ID", speakerId)
        startActivity(intent, optionsCompat.toBundle())
    }
}