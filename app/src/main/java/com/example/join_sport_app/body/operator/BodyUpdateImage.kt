package com.example.join_sport_app.body.operator

data class BodyUpdateImage (
    var dataStadium: ArrayList<Data>
) {

    class Data(
        imgS_id:String,
        s_id : String,
        s_img: String,
        img: String
    ) {
        var imgS_id = ""
        var s_id = ""
        var s_img = ""
        var img = ""

        init {
            this.imgS_id = imgS_id
            this.s_id = s_id
            this.s_img = s_img
            this.img = img
        }
    }
}
