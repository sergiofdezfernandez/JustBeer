package com.uniovi.justbeer.ui.activities.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.gson.Gson
import com.uniovi.justbeer.R
import com.uniovi.justbeer.databinding.ActivityAuthBinding
import com.uniovi.justbeer.model.domain.ProviderType
import com.uniovi.justbeer.model.domain.UserProfile
import com.uniovi.justbeer.ui.activities.MainActivity


class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    companion object {
        const val TITLE = "Autenticación"
        const val GOOGLE_SIGN_IN = 100
        const val TAG = "AuthActivity"
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
        val userProfile = prefs.getString("userProfile", null)

        if (userProfile != null) {
            binding.authLayout.visibility = View.INVISIBLE
            val userProfile = Gson().fromJson(userProfile, UserProfile::class.java)
            showHome(userProfile)
        }
    }

    private fun onSignUp() {
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
                }
            }.addOnFailureListener {
                Log.d(TAG, it.message)
                showAlert("Error",it.message.toString())
            }
        } else {
            showAlert("Info","Introduzca valores en los campos porfavor")
        }
    }

    private fun onLogin() {
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
                }
            }.addOnFailureListener {
                Log.d(TAG, it.message)
                showAlert("Error",it.message.toString())
            }
        }else{
            showAlert("Info","Introduzca valores en los campos porfavor")
        }
    }

    private fun onGoogleLogin() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleConf)
        googleClient.signOut()
        startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
    }

    private fun setUp() {
        title = TITLE
        binding.signUpButton.setOnClickListener { onSignUp() }
        binding.loginButton.setOnClickListener { onLogin() }
        binding.googleButton.setOnClickListener { onGoogleLogin() }
    }

    private fun showHome(userProfile: UserProfile) {
        val homeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("userProfile", userProfile)
        }
        startActivity(homeIntent)
    }

    private fun showAlert(title: String, msg: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(msg)
        builder.setPositiveButton("OK", null)
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
                            }
                        }.addOnFailureListener {
                            Log.d(TAG, it.message)
                            showAlert("Error",it.message.toString())
                        }
                }
            } catch (e: ApiException) {
                Log.d(TAG, e.stackTraceToString())
            }
        }
    }
}