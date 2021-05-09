package com.uniovi.justbeer.ui.favorites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.uniovi.justbeer.R
import com.uniovi.justbeer.databinding.FavoritesFragmentBinding
import com.uniovi.justbeer.databinding.HomeFragmentBinding
import com.uniovi.justbeer.ui.home.HomeViewModel
import com.uniovi.justbeer.ui.home.adapters.BeerListAdapter

class FavoritesFragment : Fragment() {

    private var _binding: FavoritesFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoritesFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        viewModel.recommendation.observe(viewLifecycleOwner, {
            Picasso.get().load(it.image).resize(250, 550).into(binding.recommendationImageView)
            binding.recommendationTextView.text = it.name
        })
        viewModel.requestRecomendation()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}