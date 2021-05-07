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
import androidx.recyclerview.widget.RecyclerView
import com.uniovi.justbeer.R
import com.uniovi.justbeer.databinding.HomeFragmentBinding
import com.uniovi.justbeer.databinding.ProfileFragmentBinding
import com.uniovi.justbeer.model.domain.BeerList
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
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.beerRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            viewModel.beerList.observe(viewLifecycleOwner) { beerList ->
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