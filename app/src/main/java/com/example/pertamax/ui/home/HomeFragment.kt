package com.example.pertamax.ui.home

import GridSpacingItemDecoration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pertamax.MainActivity
import com.example.pertamax.R
import com.example.pertamax.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Show Bottom Navigation and Action Bar
        (activity as? MainActivity)?.showBottomNav()
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Setup ViewModel TextView
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Icon List (Dynamically Add Icons Here)
        val icons = listOf(
            Pair(R.drawable.ic_icon1, "Icon 1"),
            Pair(R.drawable.ic_icon2, "Icon 2"),
            Pair(R.drawable.ic_icon3, "Icon 3"),
            Pair(R.drawable.ic_icon4, "Icon 4"),
            Pair(R.drawable.ic_icon5, "Icon 5")
        )

        // Setup RecyclerView
        val adapter = IconAdapter(icons)
        binding.iconRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3) // 3 Columns
        binding.iconRecyclerView.adapter = adapter
        binding.iconRecyclerView.addItemDecoration(GridSpacingItemDecoration(128,16))

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}