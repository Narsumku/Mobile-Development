package com.bangkit.narsumku.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.narsumku.data.Results
import com.bangkit.narsumku.databinding.ActivitySearchBinding
import com.bangkit.narsumku.ui.ViewModelFactory
import com.bangkit.narsumku.ui.adapter.SearchAdapter
import com.bangkit.narsumku.ui.detail.SpeakerDetailActivity
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private val viewModel by viewModels<SearchViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val field = intent.getStringExtra("FIELD") ?: ""
        Log.d("SearchActivity", "Field: $field") // Log field
        lifecycleScope.launch {
            speakerObserver(field)
        }

        binding.ivArrowBack.setOnClickListener {
            onBackPressed()
        }

        binding.recyclerViewSpeakers.layoutManager = LinearLayoutManager(this)
    }

    private suspend fun speakerObserver(field: String) {
        when (val search = viewModel.getSearch(field)) {
            is Results.Loading -> {
                Log.d("SearchActivity", "Loading data...") // Log loading state
                binding.progressBar.visibility = View.VISIBLE
            }

            is Results.Success -> {
                Log.d("SearchActivity", "Data loaded successfully") // Log success state
                binding.progressBar.visibility = View.GONE
                val adapter =
                    SearchAdapter(search.data, object : SearchAdapter.OnItemClickListener {
                        override fun onItemClick(
                            speakerId: String,
                            optionsCompat: ActivityOptionsCompat
                        ) {
                            openSpeakerDetail(speakerId, optionsCompat)
                        }
                    })
                binding.recyclerViewSpeakers.adapter = adapter
            }

            is Results.Error -> {
                Log.e("SearchActivity", "Error loading data") // Log error state
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun openSpeakerDetail(speakerId: String, optionsCompat: ActivityOptionsCompat) {
        val intent = Intent(this, SpeakerDetailActivity::class.java)
        intent.putExtra("SPEAKER_ID", speakerId)
        startActivity(intent, optionsCompat.toBundle())
    }
}
