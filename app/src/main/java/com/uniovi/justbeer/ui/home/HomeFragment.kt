package com.uniovi.justbeer.ui.home

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.uniovi.justbeer.R
import com.uniovi.justbeer.databinding.HomeFragmentBinding
import com.uniovi.justbeer.databinding.ProfileFragmentBinding
import com.uniovi.justbeer.ui.favorites.FavoritesViewModel
import com.uniovi.justbeer.ui.home.adapters.BeerListAdapter

class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.home_fragment, container, false)
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.beerRecyclerView.layoutManager = LinearLayoutManager(activity)
        activity?.let {
            viewModel.beerList.observe(it) { beerList ->
                binding.beerRecyclerView.adapter = BeerListAdapter(beerList) {
                   // TODO: item click
                }
            }
        }

        viewModel.requestBeers()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}