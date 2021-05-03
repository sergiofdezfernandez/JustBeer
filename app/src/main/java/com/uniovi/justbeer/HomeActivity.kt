package com.uniovi.justbeer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.uniovi.justbeer.databinding.ActivityHomeBinding

enum class ProviderType {
    BASIC
}

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("email")
        val provider: String? = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")
    }

    private fun setup(email: String, provider: String) {
        title = "Inicio"
        binding.emailTextView.text = email
        binding.providerTextView.text = provider
        binding.logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }
}