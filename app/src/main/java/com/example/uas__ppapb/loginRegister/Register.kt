package com.example.uas__ppapb.loginRegister

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uas__ppapb.databinding.RegisterPageBinding
import com.example.uas__ppapb.user.Home
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class Register : Fragment() {

    private val db = Firebase.firestore
    private lateinit var binding: RegisterPageBinding
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_PASS = "extra_pass"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.register.setOnClickListener {
            val name = binding.usernameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val confirmPassword = binding.confirmPasswordInput.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showError("Please fill all the fields")
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                showError("Passwords do not match")
                return@setOnClickListener
            }

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "password" to password
                        )
                        db.collection("users")
                            .add(user)
                            .addOnSuccessListener {
                                val intentToLoginPageActivity =
                                    Intent(requireContext(), Home::class.java)
                                intentToLoginPageActivity.putExtra(EXTRA_NAME, name)
                                intentToLoginPageActivity.putExtra(EXTRA_EMAIL, email)
                                intentToLoginPageActivity.putExtra(EXTRA_PASS, password)
                                startActivity(intentToLoginPageActivity)
                            }
                            .addOnFailureListener {
                                showError("Registration failed")
                            }
                    } else {
                        showError("Registration failed")
                    }
                }
        }
    }

    private fun showError(errorMessage: String) {
        binding.usernameInput.error = errorMessage
        binding.emailInput.error = errorMessage
        binding.passwordInput.error = errorMessage
        binding.confirmPasswordInput.error = errorMessage
        binding.usernameInput.requestFocus()
    }
}
