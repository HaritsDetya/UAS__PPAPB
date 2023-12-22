package com.example.uas__ppapb.admin

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.uas__ppapb.R
import com.example.uas__ppapb.databinding.DetailMovieBinding
import com.github.clans.fab.FloatingActionButton
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DetailMovie : AppCompatActivity() {
    private lateinit var binding: DetailMovieBinding
    private var imageUrl = ""
    private var id = ""
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailMovieBinding.inflate(layoutInflater)
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

        binding.deleteButton.setOnClickListener {
            delete()
            deleteMovie(id)
        }

        binding.editButton.setOnClickListener {
            val originalDate = binding.date.text.toString().replace("(", "").replace(")", "")
            val intent = Intent(this@DetailMovie, EditMovie::class.java)
                .putExtra("Title", binding.title.text.toString())
                .putExtra("Description", binding.deskripsi.text.toString())
                .putExtra("Genre", binding.genreButton.text.toString())
                .putExtra("Date", originalDate)
                .putExtra("Director", binding.director.text.toString())
                .putExtra("Image", imageUrl)
                .putExtra("Id", id)
            startActivity(intent)
        }
    }

    private fun delete() {
        db.collection("data_movie").document()
            .delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Movie deleted successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@DetailMovie, ListMovieActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error deleting movie: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteMovie(movieId: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("data_movie").document()
            .delete()
            .addOnSuccessListener {
                // Document successfully deleted
                Toast.makeText(this, "Movie deleted successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                // Handle errors here
                Toast.makeText(this, "Error deleting movie: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
