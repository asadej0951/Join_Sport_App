package com.example.myapplicationproject.body

data class BodylnsertUser(
    val u_user: String,
    val u_pass: String,
    val u_name: String,
    val u_lname: String,
    val u_email: String,
    val u_tel: String,
    val u_status: String
)
//
//    class Data(
//        u_user: String,
//        u_pass: String,
//        u_name: String,
//        u_lname: String,
//        u_email: String,
//        u_tel: String,
//        u_img: String,
//        u_status: String,
//        img :String
//    )
//    {
//        var u_user =""
//        var u_pass=""
//        var u_name = ""
//        var u_lname=""
//        var u_email = ""
//        var u_tel =""
//        var u_img=""
//        var u_status =""
//        var img = ""
//        init {
//            this.u_user = u_user
//            this.u_pass = u_pass
//            this.u_name = u_name
//            this.u_lname = u_lname
//            this.u_email = u_email
//            this.u_tel = u_tel
//            this.u_img = u_img
//            this.u_status = u_status
//            this.img = img
//        }
//    }