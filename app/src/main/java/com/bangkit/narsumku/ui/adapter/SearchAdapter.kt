package com.bangkit.narsumku.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.narsumku.data.response.Speaker
import com.bangkit.narsumku.databinding.ItemSpeakerBinding
import com.bumptech.glide.Glide

class SearchAdapter(
    private val speakers: List<Speaker>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(speakerId: String, optionsCompat: ActivityOptionsCompat)
    }

    inner class SearchViewHolder(private val binding: ItemSpeakerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(speaker: Speaker) {
            Glide.with(binding.root.context)
                .load(speaker.profilePicUrl)
                .into(binding.ivSpeakerPhoto)
            binding.tvSpeakerName.text = speaker.name
            if (speaker.field == "Media_News") {
                binding.tvSpeakerField.text = "Media & News"
            } else {
                binding.tvSpeakerField.text = speaker.field
            }
            binding.tvSpeakerRating.text = speaker.rating.toString()
            binding.root.setOnClickListener {
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    Pair(binding.ivSpeakerPhoto, "speakerAvatar"),
                    Pair(binding.tvSpeakerName, "speakerName"),
                    Pair(binding.tvSpeakerField, "speakerCategory")
                )
                itemClickListener.onItemClick(speaker.speakerId, optionsCompat)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSpeakerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val speaker = speakers[position]
        holder.bind(speaker)
    }

    override fun getItemCount(): Int = speakers.size
}