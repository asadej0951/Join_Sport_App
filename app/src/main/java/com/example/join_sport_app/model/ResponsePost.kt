package com.example.join_sport_app.model

data class ResponsePost (
    val p_id :Int,
    val user_id:String,
    val username:String,
    val u_img:String,
    val p_message:String,
    val p_time:String,
    val user_status:String
)