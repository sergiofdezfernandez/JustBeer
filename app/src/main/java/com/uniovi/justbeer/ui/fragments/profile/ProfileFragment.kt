package com.uniovi.justbeer.ui.fragments.profile

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.uniovi.justbeer.R
import com.uniovi.justbeer.databinding.ProfileFragmentBinding
import com.uniovi.justbeer.model.domain.UserProfile


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
        binding.logOutButton.setOnClickListener {
            val prefs =
                activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
                    ?.edit()
            prefs?.clear()
            prefs?.apply()
            FirebaseAuth.getInstance().signOut()
            activity?.finish()
        }
        val prefs =
            activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val userProfile =
            Gson().fromJson(prefs?.getString("userProfile", null), UserProfile::class.java)
        binding.emailTextView.text = userProfile?.email
        binding.userIdTextView.text = userProfile?.id
        binding.providerTextView.text = userProfile?.provider.toString()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}