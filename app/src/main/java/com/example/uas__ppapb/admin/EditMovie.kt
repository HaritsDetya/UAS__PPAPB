package com.example.uas__ppapb.admin


import android.content.Context
import com.example.uas__ppapb.R
import com.example.uas__ppapb.databinding.EditMovieBinding
import com.example.uas__ppapb.model.preferences
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.uas__ppapb.model.DataMovie
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class EditMovie : AppCompatActivity() {
    private lateinit var uri: Uri
    private lateinit var id: String
    private lateinit var oldImageURL: String
    private lateinit var imageUrl: String
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference

    private lateinit var context: Context
    private lateinit var pref: preferences
    private lateinit var binding: EditMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_movie)

        val activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback<ActivityResult> { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    uri = data?.data ?: Uri.EMPTY
                    binding.imgAddMovie.setImageURI(uri)
                } else {
                    Toast.makeText(this@EditMovie, "No Image Selected", Toast.LENGTH_SHORT).show()
                }
            }
        )

        val bundle = intent.extras
        if (bundle != null) {
            Glide.with(this@EditMovie).load(bundle.getString("Image")).into(binding.imgAddMovie)
            binding.titleInput.setText(bundle.getString("Title"))
            binding.descInput.setText(bundle.getString("Description"))
            binding.genreInput.setText(bundle.getString("Genre"))
            binding.directorInput.setText(bundle.getString("Director"))
            binding.dateInput.setText(bundle.getString("Date"))
            id = bundle.getString("Id") ?: ""
            oldImageURL = bundle.getString("Image") ?: ""
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials").child(id)

        binding.imgAddMovie.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        binding.updateButtonMovie.setOnClickListener {
            saveData()
            val intent = Intent(this@EditMovie, ListMovieActivity::class.java)
            startActivity(intent)
        }
    }

    fun saveData() {
        storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
        uri?.lastPathSegment?.let {
            storageReference = storageReference.child(it)
        } ?: run {
            // Handle the case where uri is null
            Toast.makeText(this@EditMovie, "Image not selected", Toast.LENGTH_SHORT).show()
        }


        val builder = AlertDialog.Builder(this@EditMovie)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()

        storageReference.putFile(uri).addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
            val uriTask: Task<Uri> = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete);
            val urlImage: Uri = uriTask.result
            imageUrl = urlImage.toString()
            updateData()
            dialog.dismiss()
        }).addOnFailureListener(OnFailureListener { e ->
            dialog.dismiss()
        })
    }

    fun updateData() {
        val title = binding.titleInput.text.toString().trim()
        val desc = binding.descInput.text.toString().trim()
        val genre = binding.genreInput.text.toString().trim()
        val director = binding.directorInput.text.toString().trim()
        val date = binding.dateInput.text.toString().trim()

        val dataClass = DataMovie(title, desc, genre, director, date, imageUrl)

        databaseReference.setValue(dataClass).addOnCompleteListener(OnCompleteListener<Void> { task ->
            if (task.isSuccessful) {
                val reference: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL)
                reference.delete()
                Toast.makeText(this@EditMovie, "Updated", Toast.LENGTH_SHORT).show()
                finish()
            }
        }).addOnFailureListener(OnFailureListener { e ->
            Toast.makeText(this@EditMovie, e.message.toString(), Toast.LENGTH_SHORT).show()
        })
    }
}