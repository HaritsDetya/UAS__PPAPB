package com.example.uas__ppapb.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uas__ppapb.databinding.AccountBinding
import com.example.uas__ppapb.databinding.MainLoginRegisterBinding
import com.example.uas__ppapb.loginRegister.Login
import com.example.uas__ppapb.loginRegister.MainLoginRegisterActivity
import com.example.uas__ppapb.model.preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class Account : Fragment() {
    private lateinit var context: Context
    private lateinit var pref: preferences
    private lateinit var binding: AccountBinding
    private val db = FirebaseDatabase.getInstance()
    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AccountBinding.inflate(inflater, container, false)
        val view = binding.root

        context = requireContext()
        pref = preferences(context)
        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        val sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        val username1 = sharedPreferences.getString("username", "")
        val email1 = sharedPreferences.getString("email", "")

        binding.username.text = username1
        binding.email.text = email1

        binding.logout.setOnClickListener {
            sharedPreferences.edit().putBoolean("isLogin", false).apply()

            val intent = Intent(context, MainLoginRegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            requireActivity().finish()
        }

        val display: TextView = binding.username
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            val userDocRef = firestore.collection("users").document(userId)

            userDocRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val username = documentSnapshot.getString("name")
                        display.text = "$username"
                    } else {
                        // Handle the case where the document doesn't exist
                        display.text = "User"
                    }
                }
                .addOnFailureListener {
                    // Handle failures
                    display.text = "User" // Provide a default message
                }
        } else {
            // Handle the case where the user is not signed in
            display.text = "User" // Provide a default message
        }

        return view
    }
}
