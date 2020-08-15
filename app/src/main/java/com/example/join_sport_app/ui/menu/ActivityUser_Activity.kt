package com.example.join_sport_app.ui.menu

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterActivityUser
import com.example.join_sport_app.rest.Utils
import com.example.myapplicationproject.adapterall.AdapterActivity
import com.example.myapplicationproject.model.ResponseActivity
import com.example.myapplicationproject.presenter.PresenterActivity
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_menu.view.*

class ActivityUser_Activity : AppCompatActivity() {

    var mPreferrences = Preferrences(this)
    var mPresenterActivity = PresenterActivity()
    var mResponseActivity = ArrayList<ResponseActivity>()
    lateinit var mAdapterActivity : AdapterActivityUser
    private var Image =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_)
        name_ActivityProfile.setText(mPreferrences.getName_lname())
        ID_user_ActivityProfile.setText(mPreferrences.getusername())
        Image = mPreferrences.getImage()
        Picasso.get().load(Utils.host+"/imageregister/"+ Image).into(img_ActivityProfile)
        btn_back_toMenu_ActivityProfile.setOnClickListener { finish() }
        setAPIActivityUser()
    }

    private fun setAPIActivityUser() {
        var user_id = mPreferrences.getID()

        mPresenterActivity.ActivityUserPresenterRX(user_id,this::ActivityUserNext,this::ActivityUserError)
    }

    private fun ActivityUserNext(responseActivity: List<ResponseActivity>) {
        for (i in responseActivity.indices){
            mResponseActivity.add(responseActivity[i])
        }
        mAdapterActivity = AdapterActivityUser(this,mResponseActivity)
        recyclerview_ActivityProfile.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterActivity
            mAdapterActivity.notifyDataSetChanged()
        }

    }

    private fun ActivityUserError(message: String) {

    }
}