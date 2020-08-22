package com.example.join_sport_app.ui.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.join_sport_app.R
import com.example.join_sport_app.rest.Utils
import com.example.myapplicationproject.model.ResponseUser
import com.example.myapplicationproject.presenter.PresenterUser
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_profile.*

class ShowProfileActivity : AppCompatActivity() {
    var mUserPresenter = PresenterUser()
    var mPreferrences = Preferrences(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_profile)
        setAPIGetUser()
        btn_back_toMenu_showProfile.setOnClickListener {
            finish()
        }
        btn_toGo_Update.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,ProfileActivity::class.java
                )
            )
            finish()
        }
    }

    private fun setAPIGetUser() {
        mUserPresenter.GetUserPresenterRX(mPreferrences.getID().toInt(),this::GetUserNext,this::GetUserError)
    }

    private fun GetUserError(message:String) {

    }

    private fun GetUserNext(responseUser: List<ResponseUser>) {
        var username = responseUser[0].u_user
        var name = responseUser[0].u_name
        var lname = responseUser[0].u_lname
        var password = responseUser[0].u_pass
        var email = responseUser[0].u_email
        var tel =responseUser[0].u_tel
        name_ShowProfile.setText("ชื่อ : "+name)
        username_ShowProfile.setText("ID : "+username)
        Password_ShowProfile.setText("รหัสผ่าน : "+password)
        lname_ShowProfile.setText("นามสกุล : "+lname)
        email_ShowProfile.setText("อีเมล์ : "+email)
        tel_ShowProfile.setText("เบอร์โทรศัพท์ : "+tel)
        Picasso.get().load(Utils.host+"/imageregister/"+responseUser[0].img).into(imageUser_showProfile)
    }
}