package com.example.myapplicationproject.rest.local

import android.content.Context
import android.content.SharedPreferences

class Preferrences(private var context: Context) {
    companion object {
        private const val FILENAME = "app_project"
        private const val USERNAME = "u_user"
        private const val ID = "u_id"
        private const val STATUS = "status"
        private const val IMAGE = "image"
        private const val NAME_LNAME = "name_lname"
    }

    fun getID(): String {
        return getString(ID) ?: ""
    }

    fun getusername(): String {
        return getString(USERNAME) ?: ""
    }

    fun getstatus(): String {
        return getString(STATUS) ?: ""
    }
    fun getImage(): String {
        return getString(IMAGE) ?: ""
    }
    fun getName_lname(): String {
        return getString(NAME_LNAME) ?: ""
    }

    private fun getString(key: String): String? {
        return getShareadPerferrences().getString(key, null)
    }

    fun clearDataLogout() {
        saveString(USERNAME, "")
    }

    internal fun clear() {
        getShareadPerferrences().edit().clear().apply()
    }

    fun saveId(password: String) {
        saveString(ID, password)
    }

    fun saveUsername(username: String) {
        saveString(USERNAME, username)
    }

    fun saveStatus(status: String) {
        saveString(STATUS, status)
    }
    fun saveImage(image: String) {
        saveString(IMAGE, image)
    }
    fun saveName_lname(name: String) {
        saveString(NAME_LNAME, name)
    }

    private fun saveString(key: String, value: String) {
        val editor = getShareadPerferrences().edit()
        editor.putString(key, value)
        editor.apply()
    }


    private fun getShareadPerferrences(): SharedPreferences {
        return context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
    }
}