package com.example.myapplicationproject.model

data class ResponseUser(
    val u_id:String,
    val u_user: String,
    val u_pass: String,
    val u_name: String,
    val u_lname: String,
    val u_email: String,
    val u_tel: String,
    val u_status: String,
    val img :String
)