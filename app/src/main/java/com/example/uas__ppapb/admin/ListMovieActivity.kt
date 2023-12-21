package com.example.uas__ppapb.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.uas__ppapb.databinding.ListMovieBinding
import com.example.uas__ppapb.model.DataMovie
import com.google.firebase.firestore.FirebaseFirestore

class ListMovieActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var dataList: MutableList<DataMovie>
    private lateinit var adapter: AdminAdapter

    private lateinit var context: Context
    private lateinit var binding: ListMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this

        binding = ListMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.recycleMovie.layoutManager = gridLayoutManager

        dataList = mutableListOf<DataMovie>()

        adapter = AdminAdapter(this, dataList)
        binding.recycleMovie.adapter = adapter

        firestore = FirebaseFirestore.getInstance()

        // Fetch movie data from Firestore
        firestore.collection("data_movie").get()
            .addOnSuccessListener { result ->
                dataList.clear()
                for (document in result) {
                    val dataClass = document.toObject(DataMovie::class.java)
                    dataList.add(dataClass)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                // Handle failures
            }

        binding.addIcon.setOnClickListener {
            val intent = Intent(this, AddMovie::class.java)
            startActivity(intent)
        }
    }
}
