package com.example.uas__ppapb.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.bumptech.glide.Glide
import com.example.uas__ppapb.databinding.EditMovieBinding
import com.example.uas__ppapb.model.DataMovie
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class EditMovie : AppCompatActivity() {
    private var uri: Uri? = null
    private lateinit var id: String
    private lateinit var oldImageURL: String
    private lateinit var imageUrl: String

    private lateinit var binding: EditMovieBinding
    private val db = FirebaseFirestore.getInstance()
    private val storageReference: StorageReference = FirebaseStorage.getInstance().reference

    companion object {
        const val request_code = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    uri = data?.data
                    if (uri != null) {
                        binding.imgAddMovie.setImageURI(uri)
                    } else {
                        Toast.makeText(this@EditMovie, "Failed to get image URI", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@EditMovie, "No Image Selected", Toast.LENGTH_SHORT).show()
                }
            }


        val bundle = intent.extras
        if (bundle != null) {
            binding.titleInput.setText(bundle.getString("Title"))
            binding.descInput.setText(bundle.getString("Description"))
            binding.genreInput.setText(bundle.getString("Genre"))
            binding.directorInput.setText(bundle.getString("Director"))
            binding.dateInput.setText(bundle.getString("Date"))
            id = bundle.getString("Id") ?: ""
            imageUrl = bundle.getString("Image") ?: ""
            Glide.with(this).load(bundle.getString("Image")).into(binding.imgAddMovie)
        }

        binding.imgAddMovie.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        binding.editButtonMovie.setOnClickListener {
            update()
            // You can remove the following line to stay on the EditMovie activity after saving
            val intent = Intent(this@EditMovie, ListMovieActivity::class.java)
            startActivity(intent)
        }
    }

    private fun update() {
        val movieId = intent.getStringExtra("Id") ?: ""

        // Get the existing movie data from Firestore
        db.collection("data_movie").document(movieId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    // Retrieve the existing image URL and other fields
                    val existingImageUrl = document.getString("image") ?: ""
                    val existingTitle = document.getString("title") ?: ""
                    val existingDescription = document.getString("description") ?: ""
                    val existingGenre = document.getString("genre") ?: ""
                    val existingDate = document.getString("date") ?: ""
                    val existingDirector = document.getString("director") ?: ""

                    // Get the updated values from the UI
                    val updatedTitle = binding.titleInput.text.toString()
                    val updatedDescription = binding.descInput.text.toString()
                    val updatedGenre = binding.genreInput.text.toString()
                    val updatedDate = binding.dateInput.text.toString()
                    val updatedDirector = binding.directorInput.text.toString()

                    // Use the updated values or keep the existing values if they are not changed
                    val finalImageUrl = if (uri != null) imageUrl else existingImageUrl

                    // Create an updated DataMovie object
                    val updatedMovie = DataMovie(
                        updatedTitle.takeIf { it.isNotBlank() } ?: existingTitle,
                        updatedDescription.takeIf { it.isNotBlank() } ?: existingDescription,
                        updatedGenre.takeIf { it.isNotBlank() } ?: existingGenre,
                        updatedDate.takeIf { it.isNotBlank() } ?: existingDate,
                        updatedDirector.takeIf { it.isNotBlank() } ?: existingDirector,
                        finalImageUrl
                    )

                    // Update the movie data in Firestore
                    db.collection("data_movie").document(movieId).set(updatedMovie, SetOptions.merge())
                        .addOnSuccessListener {
                            Toast.makeText(this, "Movie updated successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(this, "Error updating movie: $exception", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error retrieving movie data: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EditMovie.request_code && resultCode == RESULT_OK && data != null) {
            // Get selected image URI
            uri = data.data

            // Update the image view using Glide or your preferred image loading library
            Glide.with(this).load(uri).into(binding.imgAddMovie)
        }
    }
}
