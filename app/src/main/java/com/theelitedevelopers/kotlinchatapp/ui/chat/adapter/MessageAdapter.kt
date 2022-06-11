package com.theelitedevelopers.kotlinchatapp.ui.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.theelitedevelopers.kotlinchatapp.data.model.Message
import com.theelitedevelopers.kotlinchatapp.databinding.MessageInLayoutBinding
import com.theelitedevelopers.kotlinchatapp.databinding.MessageOutLayoutBinding
import com.theelitedevelopers.kotlinchatapp.databinding.UserLayoutBinding
import com.theelitedevelopers.kotlinchatapp.ui.main.adapter.UserAdapter
import com.theelitedevelopers.kotlinchatapp.utils.AppUtils

class MessageAdapter(var context : Context, var messageList : ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val RECEIVE = 0
    val SEND = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 0){
            //inflate the Received Layout
            val binding : MessageInLayoutBinding = MessageInLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return ReceivedViewHolder(binding)
        }else {
            //inflate the Send Layout
            val binding : MessageOutLayoutBinding = MessageOutLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            return SentViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder

            //set the message
            holder.binding.buzzOutMessage.text = messageList[position].message

            holder.binding.buzzOutDate.text = AppUtils.convertStringToDate(messageList[position].date!!)

        }else {
            val viewHolder = holder as ReceivedViewHolder

            //set the message
            holder.binding.buzzInMessage.text = messageList[position].message

            holder.binding.buzzInDate.text = AppUtils.convertStringToDate(messageList[position].date!!)

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(FirebaseAuth.getInstance().uid.equals(messageList[position].senderId)){
            SEND
        }else {
            RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class SentViewHolder(var binding : MessageOutLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    class ReceivedViewHolder(var binding : MessageInLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}