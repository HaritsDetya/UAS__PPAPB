package com.example.uas__ppapb.admin

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.uas__ppapb.R
import com.example.uas__ppapb.databinding.AddMovieBinding
import com.example.uas__ppapb.model.DataMovie
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.DateFormat
import java.util.*

class AddMovie : AppCompatActivity() {
    private lateinit var binding: AddMovieBinding
    private val db = FirebaseFirestore.getInstance()
    private lateinit var uri: Uri
    private lateinit var id: String
    private lateinit var imageURL: String
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var storage: FirebaseStorage

    companion object {
        const val CHANNEL_ID = "channel_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    111
                )
            }
        }

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        id = sharedPreferences.getString("id", "").toString()

        storage = FirebaseStorage.getInstance()

        val activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    uri = data?.data ?: Uri.EMPTY
                    binding.imgAddMovie.setImageURI(uri)
                } else {
                    Toast.makeText(this@AddMovie, "No Image Selected", Toast.LENGTH_SHORT).show()
                }
            }

        binding.imgAddMovie.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        binding.saveButtonMovie.setOnClickListener {
            saveData()
            notification()

            val intent = Intent(this@AddMovie, ListMovieActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, "Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "This is channel"

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun notification() {
        createNotificationChannel()

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setContentTitle("New Movie Added")
            .setContentText("A new movie has been added to the database")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val intent = Intent(this, ListMovieActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("message", "A new movie has been added to the database")

        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = notificationManager.getNotificationChannel(CHANNEL_ID)
            if (channel == null) {
                createNotificationChannel()
            }
        }
        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    private fun saveData() {
        val storageReference: StorageReference = FirebaseStorage.getInstance().reference.child("Android Images")
            .child(uri.lastPathSegment!!)

        storageReference.putFile(uri)
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
