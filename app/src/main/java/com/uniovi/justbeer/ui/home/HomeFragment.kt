package com.uniovi.justbeer.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.uniovi.justbeer.databinding.HomeFragmentBinding
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
        binding.beerRecyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.beerList.observe(viewLifecycleOwner) { beerList ->
            binding.beerRecyclerView.adapter = BeerListAdapter(beerList) {
                // TODO: item click
            }
        }
        viewModel.requestBeers()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}