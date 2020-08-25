package com.example.join_sport_app.model

data class ResponseListNotification(
    val message: Message2,
    val status: Int
)

data class Message2(
    val message: String,
    val subject: String,
    val u_id: String
)