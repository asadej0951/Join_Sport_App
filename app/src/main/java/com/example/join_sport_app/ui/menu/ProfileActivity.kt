package com.example.join_sport_app.ui.menu

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
import android.widget.ImageView
import android.widget.Toast
import com.example.join_sport_app.R
import com.example.join_sport_app.rest.Utils
import com.example.myapplicationproject.model.ResponseUser
import com.example.myapplicationproject.presenter.PresenterUser
import com.example.myapplicationproject.rest.DataModule
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.File
import java.lang.Exception

class ProfileActivity : AppCompatActivity() {
    lateinit var img:File
    var mUserPresenter = PresenterUser()
    var mPreferrences = Preferrences(this)
    private var PICK_IMAGE = 999
    private lateinit var imageName :File
    private lateinit var addImage : ImageView
    var IMAGE = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setAPIGetUser()
        initview()
    }

    private fun initview() {
        btn_back_toMenu_Profile.setOnClickListener {
            finish()
        }
        btn_cancel_Profile.setOnClickListener {
            finish()
        }
        btm_save_Profile.setOnClickListener {
            setAPIUpdateuser()
        }
        btn_img_Profile.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i,PICK_IMAGE)
        }
    }

    private fun setAPIUpdateuser() {
        val username_Profile = username_Profile.text.toString()
        val password_Profile = password_Profile.text.toString()
        val name_Profile = name_Profile.text.toString()
        val lname_Profile = lname_Profile.text.toString()
        val email_Profile = email_Profile.text.toString()
        val tel_Profile = tel_Profile.text.toString()
//        val img_Profile = imageUser_Profile.

        mUserPresenter.updateUserPresenterRX(mPreferrences.getID().toInt(),username_Profile,
        password_Profile,name_Profile,lname_Profile,email_Profile,tel_Profile,mPreferrences.getstatus(),this::UpdateDataUserNext,this::UpdateDataUserError)
    }

    private fun UpdateDataUserNext(response : List<ResponseUser>) {
    }

    private fun UpdateDataUserError(message: String) {
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
        name_Profile.setText(name)
        username_Profile.setText(username)
        password_Profile.setText(password)
        lname_Profile.setText(lname)
        email_Profile.setText(email)
        tel_Profile.setText(tel)
        Picasso.get().load(Utils.host+"/imageregister/"+responseUser[0].img).into(imageUser_Profile)
    }
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

                Log.d("As5da1sda", File(imagePath).absolutePath )
                imageName = File(imagePath)
                Picasso.get().load(imageName).into(imageUser_Profile)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}