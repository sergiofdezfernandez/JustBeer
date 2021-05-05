package com.uniovi.justbeer.ui.profile

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.uniovi.justbeer.databinding.ProfileFragmentBinding


class ProfileFragment : Fragment() {

    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.userProfile.observe(viewLifecycleOwner, {
            binding.emailTextView.text = it.email
            binding.userIdTextView.text = it.uid
            if (it.photoUrl != null) {
                setImage(it.photoUrl)
            }
        })
        binding.logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            activity?.finish()
        }
    }

    private fun setImage(url: Uri) {
        Picasso.get().load(url).into(binding.userImageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}