package com.example.join_sport_app.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.join_sport_app.R
import com.example.join_sport_app.model.ResponsePost
import com.example.join_sport_app.presenter.PresenterPost
import com.example.join_sport_app.rest.Utils
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_update__post_.*
import java.util.*

class Update_Post_Activity : AppCompatActivity() {
    var mPreferrences = Preferrences(this)
    var mPresenterPost = PresenterPost()
    var p_message =""
    var p_id :Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update__post_)
        initView()
        initButton()
    }

    private fun initButton() {
        btn_back_UpdatePost.setOnClickListener {
            startActivity(Intent(applicationContext,Post_ProfileActivity::class.java))
            finish()
        }
        btn_CancelUpdatePost.setOnClickListener {
            startActivity(Intent(applicationContext,Post_ProfileActivity::class.java))
            finish()
        }
        btn_saveUpdatePost.setOnClickListener {
            UpdateAPI()
        }
    }

    private fun UpdateAPI() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        var DD =""
        var Mon = ""
        var HH = ""
        var MM = ""
        if (day.toString().length ==1){DD = "0${day}"}else{DD = "${day}"}
        if (month.toString().length ==1){Mon = "0${month}"}else{Mon = "${month}"}
        if (hour.toString().length==1){HH ="0${hour}"}else{HH ="${hour}"}
        if (min.toString().length==1){MM ="0${min}"}else{MM ="${min}"}
        val p_time = "${DD}/${Mon}/${year} - ${HH}:${MM}"

        mPresenterPost.updateDataPostPresenterRX(
            p_id!!,
            mPreferrences.getID(),
            mPreferrences.getName_lname(),
            mPreferrences.getImage(),
            ET_messagePost_Update.text.toString(),p_time,
            mPreferrences.getstatus(),
            this::UpdateNext,this::UpdateError
        )
    }

    private fun UpdateError(message:String) {

    }

    private fun UpdateNext(responsePost: ResponsePost) {
        startActivity(Intent(applicationContext,Post_ProfileActivity::class.java))
        finish()
    }

    private fun initView() {
        p_id = intent.getIntExtra("p_id",0)
        p_message = intent.getStringExtra("p_message")
        tv_Update_post.setText(mPreferrences.getName_lname())
        Picasso.get().load(Utils.host+"/imageregister/"+mPreferrences.getImage()).into(Img_Update)
        ET_messagePost_Update.setText(p_message)
    }
}