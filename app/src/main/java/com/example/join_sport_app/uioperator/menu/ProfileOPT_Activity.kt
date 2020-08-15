package com.example.join_sport_app.uioperator.menu

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.join_sport_app.R
import com.example.join_sport_app.model.operator.ResponseOPTProfile
import com.example.join_sport_app.model.operator.ResponseUpdateOPT
import com.example.join_sport_app.presenter.PresenterOperator
import com.example.join_sport_app.rest.Utils
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile_o_p_t_.*
import java.io.File
import java.lang.Exception

class ProfileOPT_Activity : AppCompatActivity() {
    var mPreferrences = Preferrences(this)
    var mPresenterOperator = PresenterOperator()
    private var o_Sname = ""
    private var o_address = ""
    private var o_email = ""
    private var o_lname = ""
    private var o_name = ""
    private var o_pass = ""
    private var o_status = ""
    private var o_tel = ""
    private var o_user = ""
    private var img = ""
    private var o_id : Int? = null
    private var PICK_IMAGE = 999
    private lateinit var imageName : File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_o_p_t_)
        setAPIShowProfile()
        initViewButton()
    }

    private fun initViewButton() {
        btn_back_toMenuOPT_Profile.setOnClickListener {finish()}
        btn_cancel_ProfileOPT.setOnClickListener { finish() }
        btn_img_ProfileOPT.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i,PICK_IMAGE)
        }
        btm_save_ProfileOPT.setOnClickListener {
            setAPIUpdateData()
            setAPIUpdateImage()
        }
    }

    private fun setAPIUpdateImage() {
        mPresenterOperator.UpdateimageOPTPresenterRX(o_id.toString(),imageName){
            if (it){
                Toast.makeText(applicationContext, "UpDateImage Next!!", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(applicationContext, "UpDateImage Error!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setAPIUpdateData() {
        o_user = username_ProfileOPT.text.toString()
        o_pass = password_ProfileOPT.text.toString()
        o_name = name_ProfileOPT.text.toString()
        o_lname = lname_ProfileOPT.text.toString()
        o_email = email_ProfileOPT.text.toString()
        o_tel = tel_ProfileOPT.text.toString()
        o_Sname = nameStadium_ProfileOPT.text.toString()
        o_address = address_ProfileOPT.text.toString()
        mPresenterOperator.UpdateOPTPresenterRX(o_id!!,o_user,o_pass,o_name,o_lname,o_email,o_tel,o_Sname,o_address,o_status,
        this::UpDateNext,this::UpDateError)
    }

    private fun UpDateNext(response : ResponseUpdateOPT) {
        Toast.makeText(applicationContext, "UpDateData Next!!", Toast.LENGTH_SHORT).show()
    }

    private fun UpDateError(message: String) {
        Toast.makeText(applicationContext, "UpDateData Error!!", Toast.LENGTH_SHORT).show()
    }

    private fun initViewShow() {
        username_ProfileOPT.setText(o_user)
        password_ProfileOPT.setText(o_pass)
        name_ProfileOPT.setText(o_name)
        lname_ProfileOPT.setText(o_lname)
        email_ProfileOPT.setText(o_email)
        tel_ProfileOPT.setText(o_tel)
        nameStadium_ProfileOPT.setText(o_Sname)
        address_ProfileOPT.setText(o_address)
        Picasso.get().load(Utils.host+"/imageregister/"+img).into(imageOPT_Profile)
    }
    private fun setAPIShowProfile() {
        mPresenterOperator.ProfileOPTPresenterRX(mPreferrences.getID().toInt(),this::showProfileNext,this::showProfileError)
    }
    private fun showProfileNext(response : List<ResponseOPTProfile>) {
        o_Sname = response[0].o_Sname
        o_address =response[0].o_address
        o_email = response[0].o_email
        o_lname =response[0].o_lname
        o_name = response[0].o_name
        o_pass = response[0].o_pass
        o_status = response[0].o_status
        o_tel = response[0].o_tel
        o_user = response[0].o_user
        o_id = response[0].o_id
        img = response[0].img
        initViewShow()
    }
    private fun showProfileError(message:String) {}
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==PICK_IMAGE && resultCode== Activity.RESULT_OK){
            try {
                val pickedImage : Uri = data?.data!!
                val filePath = arrayOf(MediaStore.Images.Media.DATA)
                val cursor: Cursor =
                    this.contentResolver.query(pickedImage, filePath, null, null, null)!!
                cursor.moveToFirst()
                val imagePath: String = cursor.getString(cursor.getColumnIndex(filePath[0]))
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                BitmapFactory.decodeFile(imagePath, options)
                imageName = File(imagePath)
                Picasso.get().load(imageName).into(imageOPT_Profile)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}