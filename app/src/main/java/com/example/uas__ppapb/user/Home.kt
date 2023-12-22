package com.example.uas__ppapb.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas__ppapb.databinding.HomeBinding
import com.example.uas__ppapb.model.DataMovie
import com.example.uas__ppapb.model.preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Home : Fragment() {
    private lateinit var context: Context
    private lateinit var pref: preferences
    private lateinit var binding: HomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var dataList: MutableList<DataMovie>
    private val db = FirebaseFirestore.getInstance()

    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeBinding.inflate(inflater, container, false)
        val view = binding.root

        context = requireContext()
        pref = preferences(context)

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.recycleMovie.layoutManager = gridLayoutManager

        dataList = mutableListOf<DataMovie>()

        adapter = UserAdapter(requireContext(), dataList)
        binding.recycleMovie.adapter = adapter

        firestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        // Fetch movie data from Firestore
        firestore.collection("data_movie").get()
            .addOnSuccessListener { result ->
                dataList.clear()
                for (document in result) {
                    val dataClass = document.toObject(DataMovie::class.java)
                    dataList.add(dataClass)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                // Handle failures
            }

        // Set username from Firebase Authentication
        val display: TextView = binding.username
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            val userId = currentUser.uid
            val userDocRef = db.collection("users").document(userId)

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
