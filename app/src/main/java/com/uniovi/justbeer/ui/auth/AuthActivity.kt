package com.uniovi.justbeer.ui.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.gson.Gson
import com.uniovi.justbeer.ui.MainActivity
import com.uniovi.justbeer.R
import com.uniovi.justbeer.databinding.ActivityAuthBinding
import com.uniovi.justbeer.model.domain.ProviderType
import com.uniovi.justbeer.model.domain.UserProfile

private const val GOOGLE_SIGN_IN = 100

class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    companion object {
        const val USER_PROFILE = "userProfile"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
        session()
    }

    override fun onStart() {
        super.onStart()
        binding.authLayout.visibility = View.VISIBLE
    }

    private fun session() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val userProfile = prefs.getString(USER_PROFILE, null)

        if (userProfile != null) {
            binding.authLayout.visibility = View.INVISIBLE
            val userProfile = Gson().fromJson(userProfile, UserProfile::class.java)
            showHome(userProfile)
        }
    }

    private fun setUp() {
        title = getString(R.string.authentication)
        binding.signUpButton.setOnClickListener {
            if (binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showHome(
                            UserProfile(
                                it.result?.user?.uid,
                                it.result?.user?.email,
                                ProviderType.BASIC
                            )
                        )
                    } else {
                        showAlert()
                    }
                }
            }
        }
        binding.loginButton.setOnClickListener {
            if (binding.emailEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showHome(
                            UserProfile(
                                it.result?.user?.uid,
                                it.result?.user?.email,
                                ProviderType.BASIC
                            )
                        )
                    } else {
                        showAlert()
                    }
                }
            }
        }
        binding.googleButton.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }
    }

    private fun showHome(userProfile: UserProfile) {
        val homeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra(USER_PROFILE, userProfile)
        }
        startActivity(homeIntent)
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.error)
        builder.setMessage(R.string.auth_error_message)
        builder.setPositiveButton(R.string.ok, null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                showHome(
                                    UserProfile(
                                        it.result?.user?.uid,
                                        it.result?.user?.email,
                                        ProviderType.GOOGLE
                                    )
                                )
                            } else {
                                showAlert()
                            }
                        }
                }
            } catch (e: ApiException) {
                showAlert()
            }
        }
    }
}