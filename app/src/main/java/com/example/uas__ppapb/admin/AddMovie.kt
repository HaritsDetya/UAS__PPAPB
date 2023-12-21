package com.example.uas__ppapb.admin

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.uas__ppapb.databinding.AddMovieBinding
import com.example.uas__ppapb.model.DataMovie
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.DateFormat
import java.util.*

class AddMovie : AppCompatActivity() {
    private lateinit var binding: AddMovieBinding
    private val db = Firebase.firestore
    private var uri: Uri? = null
    private lateinit var id: String
    private lateinit var imageURL: String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = result.data
                    uri = data?.data
                    binding.imgAddMovie.setImageURI(uri)
                } else {
                    Toast.makeText(this@AddMovie, "No Image Selected", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        binding.imgAddMovie.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        binding.saveButtonMovie.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        val storageReference: StorageReference = FirebaseStorage.getInstance().reference.child("Android Images")
            .child(uri?.lastPathSegment!!)

        storageReference.putFile(uri!!)
            .addOnSuccessListener {
                storageReference.downloadUrl.addOnCompleteListener { uriTask ->
                    if (uriTask.isSuccessful) {
                        val urlImage = uriTask.result.toString()
                        imageURL = urlImage
                        uploadData()
                    } else {
                        Toast.makeText(this@AddMovie, "Failed to get download URL", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@AddMovie, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun uploadData() {
        val id = intent.getStringExtra("id") ?: ""
        val title: String = binding.titleInput.text.toString()
        val desc: String = binding.descInput.text.toString()
        val genre: String = binding.genreInput.text.toString()
        val director: String = binding.directorInput.text.toString()
        val date: String = binding.dateInput.text.toString()

        val dataClass = DataMovie(
            id,
            title,
            desc,
            date,
            genre,
            director,
            imageURL
        )
        val currentDate: String =
            DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)

        db.collection("data_movie").document().set(dataClass)
            .addOnSuccessListener {
                Toast.makeText(this@AddMovie, "Saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@AddMovie, ListMovieActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this@AddMovie,
                    "Failed to save data: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
