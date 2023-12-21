package com.example.uas__ppapb.loginRegister

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.uas__ppapb.databinding.RegisterPageBinding
import com.example.uas__ppapb.user.Home
import com.example.uas__ppapb.user.MainActivity
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.firestore.firestore

class Register : Fragment() {
    private lateinit var database: DatabaseReference
    private val db = Firebase.firestore
    private lateinit var binding: RegisterPageBinding
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        const val EXTRA_NAME = "extra_name"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegisterPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseApp.initializeApp(requireContext())
        firebaseAuth = FirebaseAuth.getInstance()

        binding.register.setOnClickListener {
            val name = binding.usernameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val confirmPassword = binding.confirmPasswordInput.text.toString()

            database = FirebaseDatabase.getInstance().reference

            if (name.isNotEmpty() || email.isNotEmpty() || password.isNotEmpty() || confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val userId = firebaseAuth.currentUser?.uid
//                                saveUserDataToRealtimeDatabase(userId, name, email)
                                saveUserDataToFirestore(userId, name, email)
                                startActivity(Intent(requireContext(), MainActivity::class.java).apply {
                                    putExtra(EXTRA_NAME, name)
                                    putExtra(Login.EXTRA_EMAIL, email)
                                    putExtra(Login.EXTRA_PASS, password)
                                })
                            } else {
                                showError("Registration failed")
                            }
                        }
                } else {
                    showError("Password and confirm password must be same")
                }
            } else {
                showError("Please fill all the fields")
            }
        }
    }

//    private fun saveUserDataToRealtimeDatabase(userId:String?, name: String, email: String) {
////        val idUser = database.child("users").push().key
//
//        userId?.let {
//            val user = hashMapOf(
//                "name" to name,
//                "email" to email
//            )
//
//            database.child(name).child(userId).setValue(user)
//                .addOnSuccessListener {
//                    Toast.makeText(requireContext(), "User data saved to Realtime Database", Toast.LENGTH_SHORT).show()
//                }
//                .addOnFailureListener {
//                    Toast.makeText(requireContext(), "Failed to save user data", Toast.LENGTH_SHORT).show()
//                }
//        }
//    }
    private fun saveUserDataToFirestore(userId: String?, name: String, email: String) {
        userId?.let {
            val user = hashMapOf(
                "name" to name,
                "email" to email
            )

            db.collection("users").document(userId).set(user)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "User data saved to Firestore", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Failed to save user data to Firestore", Toast.LENGTH_SHORT).show()
                }
        }
    }


    private fun showError(errorMessage: String) {
        binding.usernameInput.error = null
        binding.emailInput.error = null
        binding.passwordInput.error = null
        binding.confirmPasswordInput.error = null

        if (errorMessage.isNotEmpty()) {
            if (binding.usernameInput.text.isNullOrBlank()) {
                binding.usernameInput.error = errorMessage
                binding.usernameInput.requestFocus()
            }
            if (binding.emailInput.text.isNullOrBlank()) {
                binding.emailInput.error = errorMessage
                binding.emailInput.requestFocus()
            }
            if (binding.passwordInput.text.isNullOrBlank()) {
                binding.passwordInput.error = errorMessage
                binding.passwordInput.requestFocus()
            }
            if (binding.confirmPasswordInput.text.isNullOrBlank()) {
                binding.confirmPasswordInput.error = errorMessage
                binding.confirmPasswordInput.requestFocus()
            }
        }
    }

}
