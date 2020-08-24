package com.example.join_sport_app.ui.menu.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterJoinActivity
import com.example.join_sport_app.model.ResponseJoinActivityItem
import com.example.myapplicationproject.presenter.PresenterActivity
import com.example.myapplicationproject.rest.local.Preferrences
import kotlinx.android.synthetic.main.fragment_join__activity_.*

class Join_Activity_Fragment : Fragment() {
    lateinit var mPreferrences: Preferrences
    lateinit var mAdapter: AdapterJoinActivity
    var mResponseJoinActivityItem = ArrayList<ResponseJoinActivityItem>()
    var mPresenterActivity = PresenterActivity()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_join__activity_, container, false)
        setAPIShow()
        return root.rootView
    }

    private fun setAPIShow() {
        mPreferrences = Preferrences(context!!)
        mPresenterActivity.showJoinActivityPresenterRX(mPreferrences.getID().toInt(),this::showJoinNext,this::showJoinError)
    }

    private fun showJoinNext(response : List<ResponseJoinActivityItem>) {
        for (i in response.indices){
            mResponseJoinActivityItem.add(response[i])
        }
        mAdapter = AdapterJoinActivity(context!!,mResponseJoinActivityItem)
        recycler_JoinActivity.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            mAdapter.notifyDataSetChanged()
        }

    }

    private fun showJoinError(message:String) {
    }

}