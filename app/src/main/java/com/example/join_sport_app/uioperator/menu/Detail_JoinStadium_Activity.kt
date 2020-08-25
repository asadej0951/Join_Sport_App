package com.example.join_sport_app.uioperator.menu

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.join_sport_app.R
import com.example.join_sport_app.model.ResponseJoinStadium
import com.example.join_sport_app.model.ResponseListNotification
import com.example.join_sport_app.model.ResponseNotification
import com.example.join_sport_app.presenter.PresenterNotification
import com.example.join_sport_app.presenter.PresenterOperator
import com.example.join_sport_app.rest.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail__join_stadium_.*

class Detail_JoinStadium_Activity : AppCompatActivity() {
    private var HH_in :Int? = null
    private var HH_out :Int? = null
    private var MM_in :Int? = null
    private var MM_out :Int? = null
    lateinit var radioButton: RadioButton
    var mPresenterOperator = PresenterOperator()
    var mPresenterNotification =PresenterNotification()
    var U_ID = ""
    var Status = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail__join_stadium_)
        initviewSetView()
        initviewButton()
    }

    private fun initviewButton() {
        val r_id = intent.getStringExtra("r_id")
        val u_id = intent.getStringExtra("u_id")
        val o_id = intent.getStringExtra("o_id")
        val s_id = intent.getStringExtra("s_id")
        val s_name = intent.getStringExtra("s_name")
        val u_name = intent.getStringExtra("u_name")
        val u_phone = intent.getStringExtra("u_phone")
        val r_Date = intent.getStringExtra("r_Date")
        val r_timein = intent.getStringExtra("r_timein")
        val r_timeout = intent.getStringExtra("r_timeout")
        val u_price = intent.getStringExtra("u_price")
        val r_type = intent.getStringExtra("r_type")

        btn_back_tocheck_DetailJoinStadiam.setOnClickListener {
            startActivity(
                Intent(applicationContext,Check_JoinStadiam_Activity::class.java)
            )
            finish()
        }
        val RG = findViewById<RadioGroup>(R.id.RG_status_JoinStadium)
        RG.setOnCheckedChangeListener { group, checkedId ->
            if (RG.checkedRadioButtonId == R.id.rb_okay){
                Staus_CheckDetailStadium.setText("อนุมัติ")
                Staus_CheckDetailStadium.setTextColor(Color.parseColor("#00FF00"))
            }
            else{
                Staus_CheckDetailStadium.setText("ไม่อนุมัติ")
                Staus_CheckDetailStadium.setTextColor(Color.parseColor("#FF0000"))
            }
        }
        save_DetailStadium.setOnClickListener {

            val r_status = Staus_CheckDetailStadium.text.toString()
            if (r_status=="อนุมัติ"){
                Status = "3"+r_status
            }
            else{
                Status = "2"+r_status
            }
            U_ID =u_id

            setAPIUPDate(r_id.toInt(),u_id,o_id,s_id,s_name,u_name,u_phone,r_Date,r_timein,r_timeout,Status,u_price.toInt(),r_type)
        }
    }

    private fun setAPIUPDate(
        r_id: Int,
        uId: String,
        oId: String,
        sId: String,
        sName: String,
        uName: String,
        uPhone: String,
        rDate: String,
        rTimein: String,
        rTimeout: String,
        rStatus: String,
        u_price: Int,
        rType: String
    ) {
        mPresenterOperator.updateStatusJoinStadiumPresenterRX(r_id,uId,sId,oId,uName,sName,rTimein,rTimeout,rDate,uPhone,u_price,rType,rStatus
            ,this::updateStatusJoinStadiumNext,this::updateStatusJoinStadiumError)
    }

    private fun updateStatusJoinStadiumNext(response : ResponseJoinStadium) {
        mPresenterNotification.PostNotificationPresenterRX(U_ID,"สนามกีฬา",Status,this::PostNotificationNext,this::PostNotificationError)
    }

    private fun PostNotificationNext(response: ResponseListNotification) {
        startActivity(
            Intent(applicationContext,Check_JoinStadiam_Activity::class.java)
        )
        finish()
    }

    private fun PostNotificationError(message: String) {
    }

    private fun updateStatusJoinStadiumError(message:String) {
    }

    private fun initviewSetView() {
        val s_name = intent.getStringExtra("s_name")
        val u_name = intent.getStringExtra("u_name")
        val u_phone = intent.getStringExtra("u_phone")
        val r_Date = intent.getStringExtra("r_Date")
        val r_timein = intent.getStringExtra("r_timein")
        val r_timeout = intent.getStringExtra("r_timeout")
        val r_status = intent.getStringExtra("r_status")
        val u_price = intent.getStringExtra("u_price")
        val r_type = intent.getStringExtra("r_type")
        val img = intent.getStringExtra("img")
        Picasso.get().load(Utils.host+"/imageregister/"+img).into(ImgUser_CheckDetailStadium)

        HH_in = r_timein.substring(0,2).toInt()
        HH_out = r_timeout.substring(0,2).toInt()

        MM_in = r_timein.substring(3,5).toInt()
        MM_out = r_timeout.substring(3,5).toInt()

        var Total_timeCalulate = Calculate_Time(HH_in!!,HH_out!!,MM_in!!, MM_out!!)

        name_CheckDetailStadium.setText(s_name.toString())
        name_userCheckStadium.setText(u_name.toString())
        phone_userCheckStadium.setText(u_phone.toString())
        Day_CheckDetailStadium.setText(r_Date.toString())
        price_DetailStadium.setText("ราคาค่าบริการ : "+u_price+" บาท")
        time_CheckDetailStadium.setText(r_timein.toString().substring(0,5)+"น. - "+r_timeout.substring(0,5)+" น.")
        Total_time.setText("รวม "+Total_timeCalulate)
        if (r_type.toString()!=""){
            Type_DetailJoinStadium.setText("รายละเอียดเพิ่มเติม : "+r_type)
        }
        else{
            Type_DetailJoinStadium.setText("รายละเอียดเพิ่มเติม : ไม่มี")
        }
        Staus_CheckDetailStadium.setText(r_status.toString().substring(1))
        Staus_CheckDetailStadium.setTextColor(Color.parseColor("#00CCFF"))

        if (r_status.toString()=="3อนุมัติ"){
            rb_okay.isChecked = true
            Staus_CheckDetailStadium.setTextColor(Color.parseColor("#00FF00"))
        }
        else if (r_status.toString() =="2ไม่อนุมัติ"){
            rb_NoOkay.isChecked = true
            Staus_CheckDetailStadium.setTextColor(Color.parseColor("#FF0000"))

        }
    }
    private fun Calculate_Time (hh_in :Int,hh_out :Int,mm_in :Int, mm_out:Int):String{
        val HH :Int
        val MM :Int
        var strMM :String
        var strHH :String
        var TT:String

        if (hh_in>=hh_out){
            HH = hh_in - hh_out
        }
        else{
            HH = hh_out - hh_in
        }
        if (mm_in>=mm_out){
            MM = mm_in - mm_out
        }
        else{
            MM = mm_out - mm_in
        }
        if (MM!=0){
            strMM = MM.toString()+" นาที"
            strHH = HH.toString()+" ชั่วโมง"
            TT = strHH+" "+strMM
        }
        else{
            strHH = HH.toString()+" ชั่วโมง"
            TT = strHH
        }

        return TT
    }
}