package com.example.join_sport_app.model

data class ResponseStadiam(
    val message: Message,
    val s_id: Int,
    val status: Int
)

data class Message(
    val o_id: String,
    val o_user: String,
    val s_address: String,
    val s_lat: String,
    val s_long: String,
    val s_name: String,
    val s_price: String,
    val s_timeclose: String,
    val s_timeopen: String,
    val s_type: String
)
data class ResponseUpdateStadium(
    val o_id: String,
    val o_user: String,
    val s_address: String,
    val s_lat: String,
    val s_long: String,
    val s_name: String,
    val s_price: String,
    val s_timeclose: String,
    val s_timeopen: String,
    val s_type: String
)