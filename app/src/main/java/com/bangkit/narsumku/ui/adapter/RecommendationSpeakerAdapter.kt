package com.bangkit.narsumku.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.narsumku.data.response.RecommendationSpeakerResponse
import com.bangkit.narsumku.databinding.ItemRecommendationSpeakerBinding
import com.bumptech.glide.Glide

class RecommendationSpeakerAdapter(
    private val speakers: List<RecommendationSpeakerResponse>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<RecommendationSpeakerAdapter.RecommendedViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(speakerId: String, optionsCompat: ActivityOptionsCompat)
    }

    inner class RecommendedViewHolder(private val binding: ItemRecommendationSpeakerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(speaker: RecommendationSpeakerResponse) {
            Glide.with(binding.root.context)
                .load(speaker.profilePicture)
                .into(binding.ivSpeakerPhoto)
            binding.tvSpeakerName.text = speaker.fullName
            binding.tvSpeakerRating.text = speaker.rating.toString()
            binding.tvSpeakerExperience.text = speaker.experience
            binding.tvSpeakerField.text = speaker.field
            binding.root.setOnClickListener {
                val context = itemView.context as Activity
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context,
                    Pair(binding.ivSpeakerPhoto, "speakerAvatar"),
                    Pair(binding.tvSpeakerName, "speakerName"),
                    Pair(binding.tvSpeakerExperience, "speakerExperience"),
                    Pair(binding.tvSpeakerField, "speakerCategory")
                )
                itemClickListener.onItemClick(speaker.speakerId, optionsCompat)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        // Inflate layout item
        val binding = ItemRecommendationSpeakerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecommendedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        val speaker = speakers[position]
        holder.bind(speaker)
    }

    override fun getItemCount(): Int = speakers.size
}