package com.example.join_sport_app.uioperator.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterPost
import com.example.join_sport_app.adapterall.adaptermenudata.AdapterGetCheckStadiumOPT
import com.example.join_sport_app.model.ResponseJoinStadium
import com.example.join_sport_app.model.ResponsePost
import com.example.join_sport_app.presenter.PresenterOperator
import com.example.myapplicationproject.rest.local.Preferrences
import kotlinx.android.synthetic.main.activity_check__join_stadiam_.*
import kotlinx.android.synthetic.main.fragment_home__operator.*

class Check_JoinStadiam_Activity : AppCompatActivity() {
    var mPreferrences = Preferrences(this)
    var mPresenterOperator = PresenterOperator()
    var mResponseJoinStadium = ArrayList<ResponseJoinStadium>()
    lateinit var mAdapterGetCheckStadiumOPT : AdapterGetCheckStadiumOPT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check__join_stadiam_)
        btn_back_toMenu_JoinStadiam.setOnClickListener { finish() }
        setAPIGetCheck()
    }

    private fun setAPIGetCheck() {
        mPresenterOperator.dogetcheckStadiumOPTPresenterRX(mPreferrences.getID(),this::getcheckNext,this::getcheckError)
    }

    private fun getcheckNext(response :List<ResponseJoinStadium>) {
        for (i in response.indices){
            mResponseJoinStadium.add(response[i])
        }
        mAdapterGetCheckStadiumOPT = AdapterGetCheckStadiumOPT(this,mResponseJoinStadium)
        recycler_CheckJoinStadium.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterGetCheckStadiumOPT
            mAdapterGetCheckStadiumOPT.notifyDataSetChanged()
        }
    }
    private fun getcheckError(message :String) {
    }
}