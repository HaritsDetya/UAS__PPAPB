package com.example.uas__ppapb.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.uas__ppapb.databinding.EditMovieBinding
import com.example.uas__ppapb.model.DataMovie
import com.google.firebase.firestore.FirebaseFirestore
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
            saveData()
            // You can remove the following line to stay on the EditMovie activity after saving
            val intent = Intent(this@EditMovie, ListMovieActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveData() {
        // Check if uri is not null before using it
        if (uri != null) {
            val storageReference: StorageReference =
                FirebaseStorage.getInstance().reference.child("Android Images")
                    .child(uri?.lastPathSegment!!)

            storageReference.putFile(uri!!)
                .addOnSuccessListener {
                    storageReference.downloadUrl.addOnCompleteListener { uriTask ->
                        if (uriTask.isSuccessful) {
                            val urlImage = uriTask.result.toString()
                            imageUrl = urlImage
                            updateData()
                        } else {
                            Toast.makeText(
                                this@EditMovie,
                                "Failed to get download URL",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this@EditMovie,
                        "Failed to upload image: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            Toast.makeText(this@EditMovie, "Uri is null", Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateData() {
        val title = binding.titleInput.text.toString().trim()
        val desc = binding.descInput.text.toString().trim()
        val genre = binding.genreInput.text.toString().trim()
        val director = binding.directorInput.text.toString().trim()
        val date = binding.dateInput.text.toString().trim()

        val dataClass = DataMovie(id, title, desc, genre, director, date, imageUrl)

        db.collection("data_movie").document(id).set(dataClass)
            .addOnSuccessListener {
                // Delete the old image from Firebase Storage
                val reference: StorageReference =
                    FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl)
                reference.delete()
                Toast.makeText(this@EditMovie, "Updated", Toast.LENGTH_SHORT).show()
                // You might want to finish() the activity here or handle it differently
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@EditMovie, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}
