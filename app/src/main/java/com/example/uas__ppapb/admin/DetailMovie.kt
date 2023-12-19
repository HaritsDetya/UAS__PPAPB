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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DetailMovie : AppCompatActivity() {
    private lateinit var binding: DetailMovieBinding
    private lateinit var detailDesc: TextView
    private lateinit var detailImage: ImageView
    private lateinit var detailTitle: TextView
    private lateinit var deleteButton: FloatingActionButton
    private lateinit var editButton: FloatingActionButton
    private lateinit var detailLang: TextView
    private var key = ""
    private var imageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_movie)

        val bundle = intent.extras
        if (bundle != null) {
            detailDesc.text = bundle.getString("Description")
            detailTitle.text = bundle.getString("Title")
            detailLang.text = bundle.getString("Language")
            key = bundle.getString("Key") ?: ""
            imageUrl = bundle.getString("Image") ?: ""
            Glide.with(this).load(bundle.getString("Image")).into(detailImage)
        }

        deleteButton.setOnClickListener {
            val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials")
            val storage: FirebaseStorage = FirebaseStorage.getInstance()

            val storageReference: StorageReference = storage.getReferenceFromUrl(imageUrl)
            storageReference.delete().addOnSuccessListener(OnSuccessListener {
                reference.child(key).removeValue()
                Toast.makeText(this@DetailMovie, "Deleted", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, ListMovieActivity::class.java))
                finish()
            })
        }

        editButton.setOnClickListener {
            val intent = Intent(this@DetailMovie, EditMovie::class.java)
                .putExtra("Title", detailTitle.text.toString())
                .putExtra("Description", detailDesc.text.toString())
                .putExtra("Language", detailLang.text.toString())
                .putExtra("Image", imageUrl)
                .putExtra("Key", key)
            startActivity(intent)
        }
    }
}
