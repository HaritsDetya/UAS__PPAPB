package com.example.uas__ppapb.loginRegister

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.uas__ppapb.R
import com.example.uas__ppapb.admin.ListMovieActivity
import com.example.uas__ppapb.databinding.LoginPageBinding
import com.example.uas__ppapb.user.Home
import com.example.uas__ppapb.user.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Login : Fragment() {
    private lateinit var binding: LoginPageBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val firestore = FirebaseFirestore.getInstance()

    companion object {
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_PASS = "extra_pass"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        with(binding) {
            login.setOnClickListener {
                val email = emailInput.text.toString().trim()
                val password = passwordInput.text.toString().trim()

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show()
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                isUserAdmin(email)
                            } else {
                                Toast.makeText(requireContext(), "Username or Password is incorrect", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }



    private fun isUserAdmin(email: String) {
        val user = firebaseAuth.currentUser
        if (user != null) {
            if (user.email == "harits@gmail.com") {
                startActivity(Intent(requireContext(), ListMovieActivity::class.java).apply {
                    putExtra(EXTRA_EMAIL, email)
                    putExtra(EXTRA_PASS, binding.passwordInput.text.toString().trim())
                })
            } else {
                startActivity(Intent(requireContext(), MainActivity::class.java).apply {
                    putExtra(EXTRA_EMAIL, email)
                    putExtra(EXTRA_PASS, binding.passwordInput.text.toString().trim())
                })
            }
        }

//        firestore.collection("admin").document(email).get()
//            .addOnCompleteListener { adminTask ->
//                if (adminTask.isSuccessful) {
//                    val documentSnapshot = adminTask.result
//
//                    if (documentSnapshot != null && documentSnapshot.exists()) {
//                        // Get all fields and their values as a map
//                        val fieldMap = documentSnapshot.data ?: emptyMap()
//
//                        // Iterate through the fields and their values
//                        for ((fieldName, value) in fieldMap) {
//                            Log.d("LoginFragment", "Field: $fieldName, Value: $value")
//
//                            // Adjust the condition based on the field you're looking for
//                            if (fieldName == "email" && value == "harits@gmail.com") {
//                                // Perform the action for the specific field value
//                                Log.d("LoginFragment", "User is an admin")
//                                startActivity(Intent(requireContext(), ListMovieActivity::class.java).apply {
//                                    putExtra(EXTRA_EMAIL, email)
//                                    putExtra(EXTRA_PASS, binding.passwordInput.text.toString().trim())
//                                })
//                                return@addOnCompleteListener
//                            }
//                        }
//
//                        // If the specific condition is not met, you can handle it here
//                        Log.d("LoginFragment", "User is not an admin")
//                        startActivity(Intent(requireContext(), MainActivity::class.java).apply {
//                            putExtra(EXTRA_EMAIL, email)
//                            putExtra(EXTRA_PASS, binding.passwordInput.text.toString().trim())
//                        })
//                    } else {
//                        // Document does not exist
//                        Log.d("LoginFragment", "Admin document does not exist for user")
//                        startActivity(Intent(requireContext(), MainActivity::class.java).apply {
//                            putExtra(EXTRA_EMAIL, email)
//                            putExtra(EXTRA_PASS, binding.passwordInput.text.toString().trim())
//                        })
//                    }
//                } else {
//                    // Handle errors
//                    Log.e("LoginFragment", "Error checking admin status", adminTask.exception)
//                    startActivity(Intent(requireContext(), MainActivity::class.java).apply {
//                        putExtra(EXTRA_EMAIL, email)
//                        putExtra(EXTRA_PASS, binding.passwordInput.text.toString().trim())
//                    })
//                }
//            }
    }
}
