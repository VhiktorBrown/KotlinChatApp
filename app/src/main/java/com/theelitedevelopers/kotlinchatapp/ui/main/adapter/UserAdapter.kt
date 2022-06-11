package com.theelitedevelopers.kotlinchatapp.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theelitedevelopers.kotlinchatapp.data.model.User
import com.theelitedevelopers.kotlinchatapp.databinding.UserLayoutBinding
import com.theelitedevelopers.kotlinchatapp.ui.chat.ChatActivity

class UserAdapter(val context : Context, private val userList : ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding : UserLayoutBinding = UserLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.binding.name.text = userList[position].name

        holder.binding.root.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)

            intent.putExtra("name", userList[position].name)
            intent.putExtra("uid", userList[position].uid)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    class UserViewHolder(var binding: UserLayoutBinding) : RecyclerView.ViewHolder(binding.root){
    }
}