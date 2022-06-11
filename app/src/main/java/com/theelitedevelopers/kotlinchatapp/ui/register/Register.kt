package com.theelitedevelopers.kotlinchatapp.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.theelitedevelopers.kotlinchatapp.ui.main.MainActivity
import com.theelitedevelopers.kotlinchatapp.R
import com.theelitedevelopers.kotlinchatapp.data.model.User
import com.theelitedevelopers.kotlinchatapp.databinding.ActivityRegisterBinding
import com.theelitedevelopers.kotlinchatapp.ui.login.Login

class Register : AppCompatActivity() {
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var reference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        //initialise FirebaseAuth class
        auth = FirebaseAuth.getInstance()

        binding.login.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            finish()
            startActivity(intent)
        }

        binding.register.setOnClickListener {
            val email = binding.email.text.toString()
            val name = binding.name.text.toString()
            val password = binding.password.text.toString()

            register(name, email, password)
        }

    }

    private fun register(name : String, email : String, password : String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    //save Data to Database.
                    saveDataToDatabase(name, email, auth.currentUser?.uid!!)

                    // Sign in success, take user to Main Activity
                    val intent = Intent(this@Register, MainActivity::class.java)

                    //destroy Activity
                    finish()

                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Register, "Something happened. Please, try again", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun saveDataToDatabase(name : String, email : String, uid : String){
        reference = FirebaseDatabase.getInstance().reference
        reference.child("user").child(uid).setValue(User(name, email, uid))
    }
}