package com.example.uas__ppapb.admin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.uas__ppapb.R
import com.example.uas__ppapb.databinding.ListMovieBinding
import com.example.uas__ppapb.model.preferences
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uas__ppapb.model.DataMovie
import com.example.uas__ppapb.model.MyAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class ListMovieActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dataList: MutableList<DataMovie>
    private lateinit var adapter: MyAdapter
//    private lateinit var dialog: AlertDialog

    private lateinit var context: Context
    private lateinit var pref: preferences
    private lateinit var binding: ListMovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        pref = preferences(context)

        binding = ListMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.recycleMovie.layoutManager = gridLayoutManager

        dataList = ArrayList()

        adapter = MyAdapter(this, dataList)
        binding.recycleMovie.adapter = adapter

        databaseReference = FirebaseDatabase.getInstance().getReference("Android Tutorials")
//        dialog.show()
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(DataMovie::class.java)

                    dataClass?.id = itemSnapshot.key

                    dataClass?.let { dataList.add(it) }
                }
                adapter.notifyDataSetChanged()
//                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
//                dialog.dismiss()
            }
        })

        binding.addIcon.setOnClickListener {
            val intent = Intent(this, AddMovie::class.java)
            startActivity(intent)
        }
    }
}
