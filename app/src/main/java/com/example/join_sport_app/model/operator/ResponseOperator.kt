package com.example.join_sport_app.model.operator

data class ResponseOperator(
    val message: Message,
    val o_id: Int,
    val status: Int
)

data class Message(
    val o_Sname: String,
    val o_address: String,
    val o_email: String,
    val o_lname: String,
    val o_name: String,
    val o_pass: String,
    val o_status: String,
    val o_tel: String,
    val o_user: String
)