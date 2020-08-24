package com.example.join_sport_app.model


data class ResponseJoinActivityItem(
    val ac_id: String,
    val j_id: Int,
    val j_status: String,
    val name_activity: String,
    val name_user: String,
    val type_activity: String,
    val user_id: String
)