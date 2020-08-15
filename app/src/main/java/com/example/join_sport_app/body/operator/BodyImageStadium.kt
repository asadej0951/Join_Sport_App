package com.example.join_sport_app.body.operator

data class BodyImageStadium(var dataStadium: ArrayList<Data>) {

    class Data(
        s_id : String,
        s_img: String,
        img: String
    ) {

        var s_id = ""
        var s_img = ""
        var img = ""

        init {
            this.s_id = s_id
            this.s_img = s_img
            this.img = img
        }
    }

}