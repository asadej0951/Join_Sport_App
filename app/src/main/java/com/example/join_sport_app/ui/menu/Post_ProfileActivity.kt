package com.example.join_sport_app.ui.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterPost
import com.example.join_sport_app.adapterall.adaptermenudata.AdapterPostProfile
import com.example.join_sport_app.model.ResponsePost
import com.example.join_sport_app.presenter.PresenterPost
import com.example.join_sport_app.rest.Utils
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post__profile.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*

class Post_ProfileActivity : AppCompatActivity() {
    private var mPreferrences = Preferrences(this)
    var mPresenterPost = PresenterPost()
    var mResponsePost = ArrayList<ResponsePost>()
    private var Image = ""
    lateinit var mAdapterPost : AdapterPostProfile
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post__profile)
        Image = mPreferrences.getImage()
        Picasso.get().load(Utils.host+"/imageregister/"+Image).into(img_user_PostProfile)
        tv_username_PostProfile.setText(mPreferrences.getName_lname())
        ID_user_PostProfile.setText(mPreferrences.getusername())

        btn_back_toMenu_PostProfile.setOnClickListener {
            finish()
        }
        setAPIShowPostProfile()
    }

    private fun setAPIShowPostProfile() {
        mPresenterPost.PostProfilePresenterRX(
            mPreferrences.getID(),mPreferrences.getstatus(),
            this::ShowPostProfileNext,this::ShowPostProfileError
        )
    }

    private fun ShowPostProfileNext(response:List<ResponsePost>) {
        for (i in response.indices){
            mResponsePost.add(response[i])
        }
        mAdapterPost = AdapterPostProfile(this,mResponsePost)
        recyclerView_PostProfile.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterPost
            mAdapterPost.notifyDataSetChanged()
        }
    }

    private fun ShowPostProfileError(message:String) {

    }
}