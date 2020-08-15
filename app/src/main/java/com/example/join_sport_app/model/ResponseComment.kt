package com.example.join_sport_app.model

data class ResponseComment (
    val com_id:String,
    val user_id : String,
    val p_id :String,
    val com_message :String,
    val username:String,
    val img:String,
    val com_time:String
    )