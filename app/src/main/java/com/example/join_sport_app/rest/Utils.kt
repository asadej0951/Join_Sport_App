package com.example.join_sport_app.rest

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat

class Utils {
    companion object {
        //const val host = "http://192.168.1.7/"
        //const val host = "http://10.255.252.44/"
        const val host = "http://192.168.42.19:4000"

        val dateFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val timeFormatter: DateFormat = SimpleDateFormat("HH:mm")
    }

    fun getGson(): Gson? {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat("M/d/yy hh:mm a")
        return gsonBuilder.create()
    }
}