package com.example.uas__ppapb.model

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class RepoMovie(private val movieFun: MovieFun, private val firestore: FirebaseFirestore) {

    fun getAllMovie() = movieFun.getAllMovie()
    fun insertAll(dataMovie: List<DataMovie>) = movieFun.insertAll(dataMovie)
    fun insertMovie(dataMovie: List<DataMovie>) = movieFun.insertMovie(dataMovie)

    fun fetchDataFromFirestoreAndSaveToLocal() {
        val query = firestore.collection("movies")

        query.get().addOnSuccessListener { documents ->
            val movieList = mutableListOf<DataMovie>()
            for (document in documents) {
                val movie = document.toObject<DataMovie>()
                movieList.add(movie)
            }
            saveMoviesToLocalDatabase(movieList)
        }.addOnFailureListener {
        }
    }

    private fun saveMoviesToLocalDatabase(movieList: List<DataMovie>) {
        movieFun.insertMovie(movieList)
    }
}