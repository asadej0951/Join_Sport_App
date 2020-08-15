package com.example.join_sport_app.body.operator

data class BodyUploadImageOPT (var dataOPT: ArrayList<Data>) {

    class Data(
        o_id : String,
        o_img: String,
        img: String
    ) {

        var o_id = ""
        var o_img = ""
        var img = ""

        init {
            this.o_id = o_id
            this.o_img = o_img
            this.img = img
        }
    }

}