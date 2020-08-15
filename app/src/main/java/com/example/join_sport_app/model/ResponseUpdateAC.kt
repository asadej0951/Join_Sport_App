package com.example.myapplicationproject.model

data class ResponseUpdateAC (
    val user_id:String,
    val ac_name :String,
    val ac_type :String,
    val ac_time :String,
    val ac_number :Int,
    val ac_numberjoin:Int,
    val ac_lat :String,
    val ac_long :String
)