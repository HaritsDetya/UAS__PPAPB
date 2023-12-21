package com.example.uas__ppapb.user

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uas__ppapb.R
import com.example.uas__ppapb.databinding.HomeBinding
import com.example.uas__ppapb.model.preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Home : Fragment() {
    private lateinit var context: Context
    private lateinit var pref: preferences
    private lateinit var binding: HomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeBinding.inflate(inflater, container, false)
        val view = binding.root

        context = requireContext()
        pref = preferences(context)

        recyclerView = view.findViewById(R.id.rv_movie)
        adapter = UserAdapter()

        // Set Adapter on RecyclerView
//        recyclerView.adapter = adapter
//
//        // Add data to the adapter if needed
//        val dataList = // Your data source
//            adapter.submitList(dataList)

        val display: TextView = binding.username
        val sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        val username = sharedPreferences.getString("username", "")
        val password = sharedPreferences.getString("password", "")

        display.text = "$username"

        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val display: TextView = binding.username
//        val sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
//
//        val username = sharedPreferences.getString("username", "")
//        val password = sharedPreferences.getString("password", "")
//
//        display.text = "$username"
//
////        binding.buttonFirst.setOnClickListener {
////            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
////        }
//    }
}