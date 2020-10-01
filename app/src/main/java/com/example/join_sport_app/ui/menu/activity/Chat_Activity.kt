package com.example.join_sport_app.ui.menu.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterChat
import com.example.join_sport_app.model.ResponseChat
import com.example.join_sport_app.model.ResponseGetChat
import com.example.join_sport_app.ui.dashboard.Map_Detail_Activity
import com.example.myapplicationproject.adapterall.AdapterActivity
import com.example.myapplicationproject.model.ResponseActivity
import com.example.myapplicationproject.model.ResponseJoin
import com.example.myapplicationproject.model.ResponseUpdateAC
import com.example.myapplicationproject.presenter.PresenterActivity
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_chat_.*
import java.util.*
import kotlin.collections.ArrayList

class Chat_Activity : AppCompatActivity() {
    var mPreferrences = Preferrences(this)
    var mPresenter = PresenterActivity()
    var mPresenterActivity = PresenterActivity()
    var mResponse = ArrayList<ResponseGetChat>()
    lateinit var mAdapterActivity : AdapterChat
    var ID =""
    var UserID = ""
    var name = ""
    var Number : Int? = null
    var Time = ""
    var Type = ""
    var lat =""
    var long = ""
    var NumberJoin :Int? = null
    var Username = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_)
        initview()
    }

    private fun initview() {
        ID = intent.getStringExtra("AC_id")
        setAPIShowData(ID)
        setAPIgetMessageChat(ID.toInt())
        btn_back_toActivityUser_Chat.setOnClickListener {
            finish()
        }
        btn_chat.setOnClickListener {
            var message = chat_Post.text.toString()
            setAPIPostChat(ID,message)
        }
        Chat_Map.setOnClickListener {
            val i = Intent(applicationContext, Map_Detail_Activity::class.java)
            i.putExtra("lat",lat)
            i.putExtra("long",long)
            startActivity(i)
        }
        logout_activity.setOnClickListener {
            setAPILogoutAC(ID.toInt(),UserID,name,Type,Time,Number!!,NumberJoin!!-1,lat,long,Username)
        }
    }

    private fun setAPILogoutAC(ID: Int,
    userID: String,
    nameAC: String,
    typeAC: String,
    timeAC: String,
    numberAC: Int,
    nbj: Int,
    latAC: String,
    longAC: String,
    userName: String
    ) {
        mPresenterActivity.UpdateActivityPresenterRX(ID,userID,nameAC,typeAC,timeAC,numberAC,nbj,latAC,longAC,userName,this::UpdateNext,this::UpdateError)
    }

    private fun UpdateNext(data: ResponseUpdateAC) {
        setAPIDelete(mPreferrences.getID(),ID)
    }

    private fun UpdateError(message: String) {
    }

    private fun setAPIgetMessageChat(ID: Int) {
        mPresenter.getMessageChatPresenterRX(ID,this::MessageNext,this::MessageError)
    }

    private fun MessageNext(DataMessage :List<ResponseGetChat>) {
        for (i in DataMessage.indices){
            mResponse.add(DataMessage[i])
        }
        mAdapterActivity = AdapterChat(this@Chat_Activity,mResponse)
        recyclerView_Chat.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,true)
            adapter = mAdapterActivity
            mAdapterActivity.notifyDataSetChanged()
        }
    }

    private fun MessageError(message: String) {
    }

    private fun setAPIPostChat(ID: String, message: String) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        val chat_time = "${day}/${month}/${year} - ${hour}:${min}"
        mPresenter.ChatPresenterRX(ID,mPreferrences.getID(),message,chat_time,this::PostNext,this::PostError)
    }

    private fun PostNext(data : ResponseChat) {
        chat_Post.text.clear()
        mResponse.clear()
        mAdapterActivity.notifyDataSetChanged()
        setAPIgetMessageChat(ID.toInt())
    }

    private fun PostError(message: String) {
    }

    private fun setAPIShowData(id: String) {
        mPresenter.getDataActivityPresenterRX(id.toInt(),this::showNext,this::showError)
    }

    private fun showNext(data :List<ResponseActivity>) {
        when(data[0].ac_type.toInt()){
            0 -> Picasso.get().load(R.drawable.basketball).into(image_chat)
            1 -> Picasso.get().load(R.drawable.sport).into(image_chat)
            2 -> Picasso.get().load(R.drawable.volleyball).into(image_chat)
            3 -> Picasso.get().load(R.drawable.ball).into(image_chat)
            4 -> Picasso.get().load(R.drawable.takraw).into(image_chat)
            5 -> Picasso.get().load(R.drawable.run).into(image_chat)
            6 -> Picasso.get().load(R.drawable.tennis).into(image_chat)
            7 -> Picasso.get().load(R.drawable.badminton).into(image_chat)
            8 -> Picasso.get().load(R.drawable.breakdance).into(image_chat)
        }
        lat = data.get(0).ac_lat
        long = data.get(0).ac_long
        Ac_name.setText(data[0].ac_name)
        Ac_username.setText("กิจกรรม : ${data[0].ac_name}")
        Chat_number.setText("${data[0].ac_numberjoin.toString()} / ${data[0].ac_number.toString()} คน")
        Chat_time.setText(data[0].ac_time)
        name = data[0].ac_name
        Time = data[0].ac_time
        UserID = data[0].user_id
        Number = data[0].ac_number
        NumberJoin = data[0].ac_numberjoin
        Type = data[0].ac_type
        Username = data[0].user_name
    }

    private fun showError(message:String) {
    }
    private fun setAPIDelete(User_Id:String,ac_Id:String) {
        mPresenterActivity.DeleteJoinPresenterRX(User_Id,ac_Id,this::DeleteJoinNext,this::DeleteJoinError)
    }
    private fun DeleteJoinNext(response: ResponseJoin) {
        startActivity(
            Intent(applicationContext,ActivityUser_Activity::class.java)
        )
        finish()
    }

    private fun DeleteJoinError(message:String) {
        Toast.makeText(applicationContext,"ลบไม่สำเร็จ",Toast.LENGTH_LONG).show()
    }

}