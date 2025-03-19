package com.example.pertamax.ui.home

import GridSpacingItemDecoration
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pertamax.R
import com.example.pertamax.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup ViewModel
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(this) {
            textView.text = it
        }

        // Icon List
        val icons = listOf(
            Pair(R.drawable.ic_icon1, "Icon 1"),
            Pair(R.drawable.ic_icon2, "Icon 2"),
            Pair(R.drawable.ic_icon3, "Icon 3"),
            Pair(R.drawable.ic_icon4, "Icon 4"),
            Pair(R.drawable.ic_icon5, "Icon 5")
        )

        // Setup RecyclerView
        val adapter = IconAdapter(icons)
        binding.iconRecyclerView.layoutManager = GridLayoutManager(this, 3) // 3 Columns
        binding.iconRecyclerView.adapter = adapter
        binding.iconRecyclerView.addItemDecoration(GridSpacingItemDecoration(48, 32))
    }
}
