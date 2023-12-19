package com.example.uas__ppapb.admin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.uas__ppapb.databinding.AddMovieBinding
import com.example.uas__ppapb.model.DataMovie
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.text.DateFormat
import java.util.*

class AddMovie : AppCompatActivity() {
    private lateinit var binding: AddMovieBinding
    private var imageURL: String? = null
    private var uri: Uri? = null

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
            finish()
        }
    }

    private fun saveData() {
        val storageReference: StorageReference = FirebaseStorage.getInstance().reference.child("Android Images")
            .child(uri?.lastPathSegment!!)

        storageReference.putFile(uri!!)
            .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                val uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
                while (!uriTask.isComplete);
                val urlImage = uriTask.result
                imageURL = urlImage.toString()
                uploadData()
            })
            .addOnFailureListener(OnFailureListener {
            })
    }

    private fun uploadData() {
        val title: String = binding.titleInput.text.toString()
        val desc: String = binding.descInput.text.toString()
        val genre: String = binding.genreInput.text.toString()
        val director: String = binding.directorInput.text.toString()
        val date: String = binding.dateInput.text.toString()

        val dataClass = imageURL?.let { DataMovie(title, desc, genre, director, date, it) }

        // We are changing the child from title to currentDate,
        // because we will be updating title as well and it may affect child value.
        val currentDate: String = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)

        FirebaseDatabase.getInstance().getReference("Android Tutorials").child(currentDate)
            .setValue(dataClass)
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@AddMovie, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            })
            .addOnFailureListener(OnFailureListener { e ->
                Toast.makeText(this@AddMovie, e.message.toString(), Toast.LENGTH_SHORT).show()
            })
    }
}
