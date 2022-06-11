package com.theelitedevelopers.kotlinchatapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.theelitedevelopers.kotlinchatapp.data.model.User
import com.theelitedevelopers.kotlinchatapp.databinding.ActivityMainBinding
import com.theelitedevelopers.kotlinchatapp.ui.login.Login
import com.theelitedevelopers.kotlinchatapp.ui.main.adapter.UserAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var userList : ArrayList<User>
    private lateinit var auth : FirebaseAuth
    private lateinit var reference : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        //initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        reference = FirebaseDatabase.getInstance().reference

        userList = ArrayList()
        adapter = UserAdapter(this, userList)

        binding.usersRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.usersRecyclerView.adapter = adapter;

        reference.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()

                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)

                    if(currentUser?.uid != auth.currentUser?.uid){
                        //add user object to array list
                        userList.add(currentUser!!)
                    }
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //logout User
        binding.logout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, Login::class.java)
            finish()
            startActivity(intent)
        }
    }
}