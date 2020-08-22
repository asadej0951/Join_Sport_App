package com.example.join_sport_app.ui.dashboard

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.join_sport_app.R
import com.example.myapplicationproject.model.ResponseCheck
import com.example.myapplicationproject.model.ResponseJoin
import com.example.myapplicationproject.model.ResponseUpdateAC
import com.example.myapplicationproject.presenter.PresenterActivity
import com.example.myapplicationproject.rest.local.Preferrences
import kotlinx.android.synthetic.main.activity_update_number.*

class UpdateNumberActivity : AppCompatActivity() {

    var mActivityPresenter = PresenterActivity()
    var mPreferrences = Preferrences(this)
    lateinit var radioButton: RadioButton
    private var JoinID :String =""
    var ID : Int? = null
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
        setContentView(R.layout.activity_update_number)
        btn_back_DetailActivity.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
        var acID = intent.getIntExtra("ac_id", 0)
        var userID = intent.getStringExtra("user_id")
        var nameAC = intent.getStringExtra("ac_name")
        var numberAC = intent.getIntExtra("ac_number",0)
        var timeAC = intent.getStringExtra("ac_time")
        var typeAC = intent.getStringExtra("ac_type")
        var latAC = intent.getStringExtra("ac_lat")
        var longAC = intent.getStringExtra("ac_long")
        var numberjoinUpdate = intent.getIntExtra("numberjoin", 0)
        var user_name = intent.getStringExtra("user_name")
        ID = acID
        name = nameAC
        UserID = userID
        Number = numberAC
        Time = timeAC
        Type = typeAC
        lat = latAC
        long = longAC
        NumberJoin = numberjoinUpdate
        Username = user_name

        btn_MapDetailActivity.setOnClickListener {
            val i =Intent(applicationContext,Map_Detail_Activity::class.java)
            i.putExtra("lat",latAC)
            i.putExtra("long",longAC)
            startActivity(i)
        }


        setAPICheck(mPreferrences.getID(),acID.toString(),user_name)

        tv_username_ac_type.setText(mPreferrences.getusername())
        tv_nameAC.setText(nameAC)
        if (typeAC.toInt() == 0){tv_typeAC.text = "บาสเกตบอล" }
        else if (typeAC.toInt() == 1){tv_typeAC.text = "ฟุตบอล"}
        else if (typeAC.toInt() == 2){tv_typeAC.text = "วอลเลย์บอล"}
        else if (typeAC.toInt() == 3){tv_typeAC.text = "ฟุตซอล"}
        else if (typeAC.toInt() == 4){tv_typeAC.text = "ตะกร้อ"}
        else if (typeAC.toInt() == 5){tv_typeAC.text = "วิ่ง"}
        else if (typeAC.toInt() == 6){tv_typeAC.text = "เทนนิส"}
        else if (typeAC.toInt() == 7){tv_typeAC.text = "แบดมินตัน"}
        else {tv_typeAC.text = "เต้น"}
        tv_numberAC.setText("เข้าร่วมแล้ว : "+numberjoinUpdate.toString()+" / "+numberAC.toString()+" คน")
        Day_DetailActivity.setText(timeAC.toString().substring(0,10))
        Time_DetailActivity.setText(timeAC.toString().substring(11)+" น.")


    }

    private fun setAPIJoin(userID: String, acID: String, js: String) {
        mActivityPresenter.JoinPresenterRX(userID,acID,js,this::JoinNext,this::JoinError)
    }

    private fun JoinError(message: String) {
        Toast.makeText(applicationContext,"เข้าร่วมไม่สำเร็จ",Toast.LENGTH_SHORT).show()
    }

    private fun JoinNext(response: ResponseJoin) {

    }

    private fun setAPIUpdateActivity(
        acID: Int,
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
        mActivityPresenter.UpdateActivityPresenterRX(acID,userID,nameAC,typeAC,timeAC,numberAC,nbj,latAC,longAC,userName,mPreferrences.getImage(),this::UpAcivityNext,this::UpAcivityError)
    }

    private fun UpAcivityError(message:String) {
        Toast.makeText(applicationContext,"เข้าร่วมไม่สำเร็จ",Toast.LENGTH_SHORT).show()
    }
    private fun UpAcivityNext(responseUpdateAC: ResponseUpdateAC) {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun setAPICheck(userID: String, acID: String,userName:String) {

        mActivityPresenter.CheckPresenterRX(userID,acID,this::checkNext,this::checkError)
    }

    private fun checkError(message:String) {
        rb_2.isChecked=true
        btn_saveJoin.setOnClickListener {
            val intSelectButton : Int = Rg.checkedRadioButtonId
            radioButton = findViewById(intSelectButton)
            if (radioButton == rb_1){
                var NBJ :Int = NumberJoin!!+1
                setAPIUpdateActivity(ID!!,UserID,name,Type,Time,Number!!,NBJ,lat,long,Username)
                setAPIJoin(mPreferrences.getID(),ID!!.toString(),"เข้าร่วมกิจกรรมนี้แล้ว")
            }
            else{
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun checkNext(response:List<ResponseCheck>) {
        rb_1.isChecked = true
        btn_saveJoin.setOnClickListener {
            val intSelectButton : Int = Rg.checkedRadioButtonId
            radioButton = findViewById(intSelectButton)
            if (radioButton == rb_2){
                var NBJ :Int = NumberJoin!!-1
                setAPIUpdateActivity(ID!!,UserID,name,Type,Time,Number!!,NBJ,lat,long,Username)
//                Toast.makeText(applicationContext, mPreferrences.getID()+"/"+ID.toString(), Toast.LENGTH_SHORT).show()
                setAPIDelete(mPreferrences.getID(),ID!!.toString())
            }
            else{
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun setAPIDelete(User_Id:String,ac_Id:String) {
        mActivityPresenter.DeleteJoinPresenterRX(User_Id,ac_Id,this::DeleteJoinNext,this::DeleteJoinError)
    }
    private fun DeleteJoinNext(response: ResponseJoin) {
    }

    private fun DeleteJoinError(message:String) {
        Toast.makeText(applicationContext,"ลบไม่สำเร็จ",Toast.LENGTH_LONG).show()
    }
}