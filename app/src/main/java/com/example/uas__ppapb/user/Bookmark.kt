package com.example.uas__ppapb.user

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uas__ppapb.databinding.BookmarkBinding
import com.example.uas__ppapb.databinding.HomeBinding
import com.example.uas__ppapb.model.preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Bookmark : Fragment() {
    private lateinit var context: Context
    private lateinit var pref: preferences
    private lateinit var binding: BookmarkBinding

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BookmarkBinding.inflate(inflater, container, false)
        val view = binding.root

        context = requireContext()
        pref = preferences(context)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}