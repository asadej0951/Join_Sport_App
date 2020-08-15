package com.example.join_sport_app.ui.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterActivityUser
import com.example.join_sport_app.adapterall.adaptermenudata.AdapterCheckStatusJoinStadium
import com.example.join_sport_app.model.ResponseJoinStadium
import com.example.myapplicationproject.model.ResponseActivity
import com.example.myapplicationproject.presenter.PresenterActivity
import com.example.myapplicationproject.presenter.PresenterUser
import com.example.myapplicationproject.rest.local.Preferrences
import kotlinx.android.synthetic.main.activity_check__status__join_stadium.*

class Check_Status_JoinStadiumActivity : AppCompatActivity() {

    var mResponseJoinStadium = ArrayList<ResponseJoinStadium>()
    lateinit var mAdapterCheckStatusJoinStadium : AdapterCheckStatusJoinStadium
    var mPresenterUser = PresenterUser()
    var mPreferrences = Preferrences(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check__status__join_stadium)
        btn_back_toMenu_checkStatus.setOnClickListener {
            finish()
        }
        setAPICheckStatus()
    }

    private fun setAPICheckStatus() {
        mPresenterUser.CheckStatusPresenterRX(mPreferrences.getID(),this::CheckStatusNext,this::CheckStatusError)
    }

    private fun CheckStatusNext(response :List<ResponseJoinStadium>) {
        for (i in response.indices){
            mResponseJoinStadium.add(response[i])
        }
        mAdapterCheckStatusJoinStadium = AdapterCheckStatusJoinStadium(this,mResponseJoinStadium)
        recycler_StatusJoinStadium.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterCheckStatusJoinStadium
            mAdapterCheckStatusJoinStadium.notifyDataSetChanged()
        }
    }

    private fun CheckStatusError(message:String) {
    }
}