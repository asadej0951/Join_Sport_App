package com.example.join_sport_app.uioperator.dashboard

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Telephony.Mms.Part.FILENAME
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterStadiam
import com.example.join_sport_app.model.ResponseShowStadium
import com.example.join_sport_app.model.ResponseStadiam
import com.example.join_sport_app.presenter.PresenterOperator
import com.example.join_sport_app.rest.Utils
import com.example.myapplicationproject.adapterall.AdapterActivity
import com.example.myapplicationproject.model.ResponseActivity
import com.example.myapplicationproject.presenter.PresenterActivity
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard__operator.*
import kotlinx.android.synthetic.main.fragment_dashboard__operator.view.*
import kotlinx.android.synthetic.main.fragment_home__operator.view.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*

class Dashboard_OperatorFragment : Fragment() {
    lateinit var mPreferrences: Preferrences
    var mPresenterStadiam = PresenterOperator()
    var mResponseStadiam = ArrayList<ResponseShowStadium>()
    lateinit var mAdapterStadiam : AdapterStadiam
    private val REQ_CODE = 999

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val root = inflater.inflate(R.layout.fragment_dashboard__operator, container, false)
        //แสดงสนามกีฬาทั้งหมด
        setAPIShowStadiam()
        initview(root)
        return root.rootView
    }

    private fun setAPIShowStadiam() {
        mPresenterStadiam.ShowStadiamPresenterRX(this::ShowStadiamNext,this::ShowStadiamError)
    }

    private fun ShowStadiamError(message:String) {

    }

    private fun ShowStadiamNext(responseStadiam: List<ResponseShowStadium>) {
        for (i in responseStadiam.indices){
            mResponseStadiam.add(responseStadiam[i])
        }
        mAdapterStadiam = AdapterStadiam(context!!,mResponseStadiam)
        recycler_stadiamOPT.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterStadiam
            mAdapterStadiam.notifyDataSetChanged()
        }
    }

    private fun initview(root: View) {
       //แสดงชื่อผู้ใช้
      mPreferrences = Preferrences(context!!)
        root.ID_OPT_Stadiam.setText("ID : "+mPreferrences.getusername())
        root.tv_username_stadiamOPT.setText("คุณ : "+mPreferrences.getName_lname())
        Picasso.get().load(Utils.host+"/imageregister/"+mPreferrences.getImage()).into(root.img_stadiam_OPT)
        //ปุ่มสร้างสนามกีฬา
        root.btn_stadiamOPT.setOnClickListener {
            startActivityForResult(Intent(context,Stadiam_Activity::class.java),REQ_CODE)
            mResponseStadiam.clear()
            mAdapterStadiam.notifyDataSetChanged()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_CODE && resultCode ==Activity.RESULT_OK) {
            setAPIShowStadiam()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
