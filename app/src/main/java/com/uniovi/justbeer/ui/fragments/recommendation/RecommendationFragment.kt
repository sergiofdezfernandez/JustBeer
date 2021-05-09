package com.uniovi.justbeer.ui.fragments.recommendation

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.uniovi.justbeer.databinding.RecommendationFragmentBinding


class RecommendationFragment : Fragment() {

    private var _binding: RecommendationFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: RecommendationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RecommendationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecommendationViewModel::class.java)
        viewModel.recommendation.observe(viewLifecycleOwner, {
            Picasso.get().load(it.image).resize(250, 550).into(binding.recommendationImageView)
            binding.recommendationTextView.text = it.name
            binding.recommendationAbvTextView.text = "${it.alcohol}%"
            binding.recommendationDescriptionTextView.text = it.description
        })
        viewModel.requestRecomendation()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}