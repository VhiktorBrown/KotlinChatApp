package com.theelitedevelopers.kotlinchatapp.data.model

class Message {
    var message : String? = null
    var senderId : String? = null
    var date : String? = null

    constructor(){}

    constructor(message: String?, senderId : String?, date : String?){
        this.message = message
        this.senderId = senderId
        this.date = date
    }
}