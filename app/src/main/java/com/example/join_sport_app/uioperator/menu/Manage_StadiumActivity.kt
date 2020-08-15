package com.example.join_sport_app.uioperator.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterStadiam
import com.example.join_sport_app.model.ResponseShowStadium
import com.example.join_sport_app.model.ResponseStadiam
import com.example.join_sport_app.presenter.PresenterOperator
import com.example.myapplicationproject.rest.local.Preferrences
import kotlinx.android.synthetic.main.activity_manage__stadium.*
import kotlinx.android.synthetic.main.fragment_dashboard__operator.*

class Manage_StadiumActivity : AppCompatActivity() {

    lateinit var mPreferrences: Preferrences
    var mPresenterStadiam = PresenterOperator()
    var mResponseStadiam = ArrayList<ResponseShowStadium>()
    lateinit var mAdapterStadiam : AdapterStadiam

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage__stadium)
        btn_back_toMenu_ManageStadium.setOnClickListener {
            finish()
        }
        setAPIManageStadium()
    }

    private fun setAPIManageStadium() {
        mPreferrences = Preferrences(this)
        mPresenterStadiam.ManageStadiumPresenterRX(mPreferrences.getID().toInt(),this::ManageNext,this::ManageError)
    }

    private fun ManageNext(responseStadiam :List<ResponseShowStadium>) {
        for (i in responseStadiam.indices){
            mResponseStadiam.add(responseStadiam[i])

        }
        mAdapterStadiam = AdapterStadiam(this,mResponseStadiam)
        recyclerview_manageStadium.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterStadiam
            mAdapterStadiam.notifyDataSetChanged()
        }
    }

    private fun ManageError(message :String) {
    }
}