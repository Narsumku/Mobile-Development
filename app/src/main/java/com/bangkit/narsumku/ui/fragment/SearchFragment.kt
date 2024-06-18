package com.bangkit.narsumku.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.narsumku.databinding.FragmentSearchBinding
import com.bangkit.narsumku.ui.search.SearchActivity

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupClickListeners()

        return root
    }

    private fun setupClickListeners() {
        binding.BussinessLinearLayout.setOnClickListener {
            openSearchActivity("business")
        }

        binding.EntertainmentLinearLayout.setOnClickListener {
            openSearchActivity("entertainment")
        }

        binding.PoliticsLinearLayout.setOnClickListener {
            openSearchActivity("politics")
        }

        binding.SportLinearLayout.setOnClickListener {
            openSearchActivity("sport")
        }

        binding.TechLinearLayout.setOnClickListener {
            openSearchActivity("tech")
        }

        binding.HealthcareLinearLayout.setOnClickListener {
            openSearchActivity("healthcare")
        }

        binding.AcademicLinearLayout.setOnClickListener {
            openSearchActivity("academic")
        }

        binding.MediaLinearLayout.setOnClickListener {
            openSearchActivity("media")
        }
    }

    private fun openSearchActivity(field: String) {
        val intent = Intent(requireContext(), SearchActivity::class.java)
        intent.putExtra("FIELD", field)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}