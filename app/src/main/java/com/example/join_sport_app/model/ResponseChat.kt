package com.example.join_sport_app.model

class ResponseChat(
    val message: DataChat,
    val status: Int
)

data class DataChat(
    val message: String,
    val u_id: String
)
data class ResponseGetChat(
    val chat_id:Int,
    val u_id:String,
    val message:String,
    val img:String,
    val lnameUser: String,
    val nameUser:String
)