package com.theelitedevelopers.kotlinchatapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.theelitedevelopers.kotlinchatapp.ui.main.MainActivity
import com.theelitedevelopers.kotlinchatapp.databinding.ActivityLoginBinding
import com.theelitedevelopers.kotlinchatapp.ui.register.Register

class Login : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //pass a layout to the binding variable
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        //initialise FirebaseAuth class
        auth = FirebaseAuth.getInstance()

        binding.register.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            finish()
            startActivity(intent)
        };

        binding.login.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            login(email, password)
            binding.loadingLayout.isVisible = true
            }
        }

    private fun login(email : String, password : String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    //hide the Loading Layout
                    binding.loadingLayout.isVisible = false

                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    //hide the Loading Layout
                    binding.loadingLayout.isVisible = false

                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@Login, "User does not exist. Please, Type in correct credentials.", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if(FirebaseAuth.getInstance().currentUser != null){
            val intent = Intent(this@Login, MainActivity::class.java)
            finish()
            startActivity(intent)
        }
    }
}