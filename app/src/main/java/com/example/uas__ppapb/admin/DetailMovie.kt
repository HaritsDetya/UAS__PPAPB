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
            val firestore = FirebaseFirestore.getInstance()
            val storage = FirebaseStorage.getInstance()

            val docRef = firestore.collection("data_movie").document(id)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        // Correctly retrieve imageUrl from Firestore document
                        val imageUrl = document.get("imageUrl").toString()

                        // Delete image from Storage
                        if (imageUrl != null) {
                            val storageReference = storage.getReferenceFromUrl(imageUrl)
                            storageReference.delete()
                                .addOnSuccessListener {
                                    // Delete document from Firestore
                                    firestore.collection("data_movie").document(id).delete()
                                        .addOnSuccessListener {
                                            Toast.makeText(this@DetailMovie, "Deleted", Toast.LENGTH_SHORT).show()
                                            startActivity(Intent(applicationContext, ListMovieActivity::class.java))
                                            finish()
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(this@DetailMovie, "Error deleting document: $e", Toast.LENGTH_SHORT).show()
                                        }
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this@DetailMovie, "Error deleting image: $e", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(this@DetailMovie, "Image URL is null", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@DetailMovie, "Document not found", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this@DetailMovie, "Error getting document: $exception", Toast.LENGTH_SHORT).show()
                }
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
}
