package com.example.uas__ppapb.user

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.uas__ppapb.R
import com.example.uas__ppapb.admin.EditMovie
import com.example.uas__ppapb.admin.ListMovieActivity
import com.example.uas__ppapb.databinding.ActivityMainBinding
import com.example.uas__ppapb.databinding.DetailMovieBinding
import com.example.uas__ppapb.databinding.HomeDeskripsiBinding
import com.example.uas__ppapb.model.preferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class HomeDeskripsiActivity : AppCompatActivity() {
    private lateinit var binding: HomeDeskripsiBinding
    private var imageUrl = ""
    private var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeDeskripsiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        if (bundle != null) {
            binding.deskripsi.text = bundle.getString("Description") // Use the correct key
            binding.title.text = bundle.getString("Title")
            binding.genreButton.text = bundle.getString("Genre")

            val date = bundle.getString("Date")
            val formattedDate = "($date)"
            binding.date.text = formattedDate

            binding.director.text = bundle.getString("Director")
            imageUrl = bundle.getString("Image") ?: ""
            Glide.with(this).load(bundle.getString("Image")).into(binding.image)
            id = bundle.getString("Id") ?: ""
        }
    }
}