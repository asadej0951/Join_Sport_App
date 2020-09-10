package com.example.join_sport_app.ui.menu.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterChat
import com.example.join_sport_app.model.ResponseChat
import com.example.join_sport_app.model.ResponseGetChat
import com.example.myapplicationproject.adapterall.AdapterActivity
import com.example.myapplicationproject.model.ResponseActivity
import com.example.myapplicationproject.presenter.PresenterActivity
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_chat_.*

class Chat_Activity : AppCompatActivity() {
    var mPreferrences = Preferrences(this)
    var mPresenter = PresenterActivity()
    var mResponse = ArrayList<ResponseGetChat>()
    lateinit var mAdapterActivity : AdapterChat
    var ID = ""
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
        mPresenter.ChatPresenterRX(ID,mPreferrences.getID(),message,this::PostNext,this::PostError)
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
        Ac_name.setText(data[0].ac_name)
        Ac_username.setText("กิจกรรม : ${data[0].ac_name}")
        Chat_number.setText("${data[0].ac_numberjoin.toString()} / ${data[0].ac_number.toString()} คน")
        Chat_time.setText(data[0].ac_time)

    }

    private fun showError(message:String) {
    }
}