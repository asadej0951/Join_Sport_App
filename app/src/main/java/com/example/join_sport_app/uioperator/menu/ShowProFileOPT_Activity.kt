package com.example.join_sport_app.uioperator.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.join_sport_app.R
import com.example.join_sport_app.model.operator.ResponseOPTProfile
import com.example.join_sport_app.presenter.PresenterOperator
import com.example.join_sport_app.rest.Utils
import com.example.join_sport_app.ui.menu.ProfileActivity
import com.example.myapplicationproject.presenter.PresenterUser
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_o_p_t_.*
import kotlinx.android.synthetic.main.activity_show_pro_file_o_p_t_.*

class ShowProFileOPT_Activity : AppCompatActivity() {

    var mPresenterOperator = PresenterOperator()
    var mPreferrences = Preferrences(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_pro_file_o_p_t_)
        setAPIGET()

        btn_back_toMenu_showProfileOPT.setOnClickListener {
            finish()
        }
        btn_toGo_UpdateOPT.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext, ProfileOPT_Activity::class.java
                )
            )
            finish()
        }
    }

    private fun setAPIGET() {
        mPresenterOperator.ProfileOPTPresenterRX(mPreferrences.getID().toInt(),this::showProfileNext,this::showProfileError)
    }
    private fun showProfileNext(response : List<ResponseOPTProfile>) {
        username_ShowProfileOPT.text = response[0].o_user
        Password_ShowProfileOPT.text =response[0].o_pass
        name_ShowProfileOPT.text = response[0].o_name
        lname_ShowProfileOPT.text =response[0].o_lname
        email_ShowProfileOPT.text = response[0].o_email
        tel_ShowProfileOPT.text = response[0].o_tel
        Sname_ShowProfileOPT.text = response[0].o_Sname
        address_ShowProfileOPT.text = response[0].o_address
        Picasso.get().load(Utils.host+"/imageregister/"+response[0].img).into(imageUser_showProfileOPT)
    }
    private fun showProfileError(message:String) {}

}