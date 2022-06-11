package com.theelitedevelopers.kotlinchatapp.utils

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    public fun displayToast(context : Context, message: String){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }


    fun convertStringToDate(date: String): String {

        val formatter = SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy")
        val dateFormat = SimpleDateFormat("dd MMM yyyy")
        val timeFormat = SimpleDateFormat("hh:mm aa")

        //parse the String to a Date Object
        val mainDate = formatter.parse(date)

        //then convert the date object into individual date and time string.
        return dateFormat.format(mainDate) + ". " + timeFormat.format(mainDate)
    }


}