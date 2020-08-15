package com.example.join_sport_app.body.user

data class BodyUploadImage (var data: ArrayList<Data>) {

    class Data(
        u_id : String,
        u_img: String,
        img: String
    ) {

        var u_id = ""
        var u_img = ""
        var img = ""

        init {
            this.u_id = u_id
            this.u_img = u_img
            this.img = img
        }
    }

}