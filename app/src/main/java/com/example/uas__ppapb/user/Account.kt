package com.example.uas__ppapb.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.uas__ppapb.databinding.AccountBinding
import com.example.uas__ppapb.databinding.HomeBinding
import com.example.uas__ppapb.loginRegister.Login
import com.example.uas__ppapb.model.preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Account : Fragment() {
    private lateinit var context: Context
    private lateinit var pref: preferences
    private lateinit var binding: AccountBinding

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = AccountBinding.inflate(inflater, container, false)
        val view = binding.root

        context = requireContext()
        pref = preferences(context)

        val sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        val username1 = sharedPreferences.getString("username", "")
        val email1 = sharedPreferences.getString("email", "")

        binding.username.text = "$username1"
        binding.email.text = "$email1"

        binding.logout.setOnClickListener {
            val intent = Intent(context, Login::class.java)
            startActivity(intent)
        }

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}