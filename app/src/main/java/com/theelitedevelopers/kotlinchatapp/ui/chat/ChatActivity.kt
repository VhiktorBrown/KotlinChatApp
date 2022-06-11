package com.theelitedevelopers.kotlinchatapp.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.theelitedevelopers.kotlinchatapp.R
import com.theelitedevelopers.kotlinchatapp.data.model.Message
import com.theelitedevelopers.kotlinchatapp.data.model.User
import com.theelitedevelopers.kotlinchatapp.databinding.ActivityChatBinding
import com.theelitedevelopers.kotlinchatapp.ui.chat.adapter.MessageAdapter
import com.theelitedevelopers.kotlinchatapp.utils.AppUtils
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {
    private lateinit var binding :ActivityChatBinding
    private lateinit var adapter : MessageAdapter
    private lateinit var chatList : ArrayList<Message>
    private lateinit var reference : DatabaseReference

    var senderRoom : String? = null
    var receiverRoom : String? = null

    private var senderUid : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get Intent passed from the User Adapter
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        //initialize the sender UID with logged in user's UID
        senderUid = FirebaseAuth.getInstance().currentUser?.uid

        //initialize Database Reference
        reference = FirebaseDatabase.getInstance().reference

        //here, we'll create a unique key for saving 2 people's messages
        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        //hide Action Bar
        supportActionBar?.hide()

        //set the Title of Layout with the name of user clicked
        binding.chatTitle.text = name

        //initialize Arraylist
        chatList = ArrayList()

        //set Adapter
        adapter = MessageAdapter(this, chatList)

        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = adapter

        //set the Image close to the EditText
        Picasso.get()
            .load(R.drawable.profile)
            .into(binding.commentImage)

        //fetch messages from Firebase before anything
        reference.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    //clear Arraylist to prevent repetition of data
                    chatList.clear()

                    //loop through and fetch all messages with their time
                    for(postSnapshot in snapshot.children){
                        chatList.add(postSnapshot.getValue(Message::class.java)!!)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })



        //set a click listener to the send button
        binding.commentSend.setOnClickListener {
            //get text from EditText
            val messageTyped = binding.commentEditText.text.toString()
            if(messageTyped.isNotEmpty()){
                //save the necessary fields in the Message Object
                val message = Message(messageTyped, senderUid, Date().toString())

                reference.child("chats").child(senderRoom!!).child("messages").push()
                    .setValue(message).addOnSuccessListener {
                        reference.child("chats").child(receiverRoom!!).child("messages").push()
                            .setValue(message).addOnSuccessListener {
                                //after saving message in both sender and receiver's places
                                Toast.makeText(this@ChatActivity, "Message sent successfully", Toast.LENGTH_LONG).show()

                                //then clear the editText
                                binding.commentEditText.setText("")
                            }
                    }

            }
        }


        //set listener on back Image
        binding.backImage.setOnClickListener {
            onBackPressed()
        }
    }
}