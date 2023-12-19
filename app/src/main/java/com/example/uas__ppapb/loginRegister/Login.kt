package com.example.uas__ppapb.loginRegister

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.uas__ppapb.R
import com.example.uas__ppapb.admin.ListMovieActivity
import com.example.uas__ppapb.databinding.LoginPageBinding
import com.example.uas__ppapb.model.DataUser
import com.example.uas__ppapb.model.preferences
import com.example.uas__ppapb.user.Home
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class Login : Fragment() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var context: Context
    private var db = Firebase.firestore
    private lateinit var binding: LoginPageBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var pref: preferences

    companion object{
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PASS = "extra_pass"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginPageBinding.inflate(layoutInflater)
        val view = binding.root
        context = view.context
        pref = preferences(context)

        firebaseAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        with(binding) {
            login.setOnClickListener {
                val username = usernameInput.text.toString()
                val password = passwordInput.text.toString()
                val sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

                if (username.isEmpty() || password.isEmpty()){
                    binding.usernameInput.error = "Please fill all the fields"
                    binding.passwordInput.error = "Please fill all the fields"
                } else {
                    val query: com.google.firebase.database.Query = database.child("users").orderByChild("username").equalTo(username)
                    query.addListenerForSingleValueEvent(object : com.google.firebase.database.ValueEventListener {
                        override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                            if (snapshot.exists()) {
                                for (item in snapshot.children){
                                    val user = item.getValue<DataUser>()
                                    if (user != null){
                                        if (user.password == password){
                                            val editor = sharedPreferences.edit()
                                            editor.putString("username", username)
                                            editor.putString("password", password)
                                            editor.apply()
                                            pref.prefStatus = true
                                            pref.prefLevel = user.role
                                            if (user.role == "admin"){
                                                val intentToLoginPageActivity = Intent(requireContext(), ListMovieActivity::class.java)
                                                intentToLoginPageActivity.putExtra(EXTRA_NAME, username)
                                                intentToLoginPageActivity.putExtra(EXTRA_PASS, password)
                                                startActivity(intentToLoginPageActivity)
                                            } else {
                                                val intentToLoginPageActivity = Intent(requireContext(), Home::class.java)
                                                intentToLoginPageActivity.putExtra(EXTRA_NAME, username)
                                                intentToLoginPageActivity.putExtra(EXTRA_PASS, password)
                                                startActivity(intentToLoginPageActivity)
                                            }
                                        } else {
                                            Toast.makeText(requireContext(), "Username atau Password tidak sesuai", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(requireContext(), "Username atau Password tidak sesuai", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: com.google.firebase.database.DatabaseError) {
                            Toast.makeText(requireContext(), "Username atau Password tidak sesuai", Toast.LENGTH_SHORT).show()
                        }
                    })
                    firebaseAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                val intentToLoginPageActivity =
                                    Intent(requireContext(), Home::class.java)
                                intentToLoginPageActivity.putExtra(EXTRA_NAME, username)
                                intentToLoginPageActivity.putExtra(EXTRA_PASS, password)
                                startActivity(intentToLoginPageActivity)
                            } else {
                                Toast.makeText(requireContext(), "Username atau Password tidak sesuai", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (pref.prefStatus){
            if (pref.prefLevel == "admin"){
                val intentToLoginPageActivity = Intent(requireContext(), ListMovieActivity::class.java)
                startActivity(intentToLoginPageActivity)
            } else {
                val intentToLoginPageActivity = Intent(requireContext(), Home::class.java)
                startActivity(intentToLoginPageActivity)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_page, container, false)
    }
}