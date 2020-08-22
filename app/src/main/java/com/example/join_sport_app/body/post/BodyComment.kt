package com.example.join_sport_app.body.post

data class BodyComment(
    val user_id : String,
    val p_id :String,
    val com_message :String,
    val username:String,
    val img:String,
    val com_time:String
)
data class BodyCommentImage(
    val img:String
)