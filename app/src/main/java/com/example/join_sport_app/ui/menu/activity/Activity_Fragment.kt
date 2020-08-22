package com.example.join_sport_app.ui.menu.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterActivityUser
import com.example.myapplicationproject.model.ResponseActivity
import com.example.myapplicationproject.presenter.PresenterActivity
import com.example.myapplicationproject.rest.local.Preferrences
import kotlinx.android.synthetic.main.fragment_activity_.*

class Activity_Fragment : Fragment() {
    lateinit var mPreferrences :Preferrences
    var mPresenterActivity = PresenterActivity()
    var mResponseActivity = ArrayList<ResponseActivity>()
    lateinit var mAdapterActivity : AdapterActivityUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_activity_, container, false)
        setAPIActivityUser()
        return root.rootView
    }

    private fun setAPIActivityUser() {
        mPreferrences = Preferrences(context!!)
        var user_id = mPreferrences.getID()

        mPresenterActivity.ActivityUserPresenterRX(user_id,this::ActivityUserNext,this::ActivityUserError)
    }

    private fun ActivityUserNext(responseActivity: List<ResponseActivity>) {
        for (i in responseActivity.indices){
            mResponseActivity.add(responseActivity[i])
        }
        mAdapterActivity = AdapterActivityUser(context!!,mResponseActivity)
        recyclerview_ActivityProfile.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterActivity
            mAdapterActivity.notifyDataSetChanged()
        }

    }

    private fun ActivityUserError(message: String) {

    }


}