package com.example.join_sport_app.ui.login

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.example.join_sport_app.MainActivityOperator
import com.example.join_sport_app.R
import com.example.join_sport_app.model.operator.ResponseOperator
import com.example.join_sport_app.presenter.PresenterOperator
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_operator_.*
import kotlinx.android.synthetic.main.fragment_operator_.view.*
import retrofit2.http.Url
import java.io.File
import java.lang.Exception


class Operator_Fragment : Fragment() {

    var mPresenterOperator = PresenterOperator()
    private lateinit var imageName :File
    private lateinit var addImage :ImageView
    lateinit var mPreferrences: Preferrences
    private var PICK_IMAGE = 999

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_operator_, container, false)
        initView(root)
        return root.rootView
    }

    private fun initView(view: View) {
        addImage = view.findViewById(R.id.img_OTP)

        view.btn_imgOPT.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i,PICK_IMAGE)
        }

        view.btn_registerOPT.setOnClickListener {
            var username = ET_usernameOPT.text.toString()
            var password =ET_passwordOPT.text.toString()
            var name = ET_nameOPT.text.toString()
            var lname = ET_lnameOPT.text.toString()
            var email = ET_emailOTP.text.toString()
            var tel = ET_telOTP.text.toString()
            var Sname = ET_SnameOPT.text.toString()
            var address = ET_addressOPT.text.toString()
            var o_status = "ผู้ประกอบการ"
            setRegisterOPT(username,password,name,lname,email,tel,Sname,address,o_status)
        }
    }

    private fun setRegisterOPT(
        username: String,
        password: String,
        name: String,
        lname: String,
        email: String,
        tel: String,
        sname: String,
        address: String,
        o_status:String
    ) {
        mPresenterOperator.RegisterOPTPresenterRX(username,password,name,lname,email,tel,sname,address,o_status,this::RegisterOPTNext,this::RegisterOPTError)
    }

    private fun RegisterOPTError(message:String) {
    }

    private fun RegisterOPTNext(responseOperator: ResponseOperator) {
        val o_id = responseOperator.o_id
        mPresenterOperator.UploadImageOPTPresenterRX(o_id.toString(),imageName){
            if (it){
                mPreferrences = Preferrences(context!!)
                mPreferrences.saveStatus("ผู้ประกอบการ")
                mPreferrences.saveUsername(ET_usernameOPT.text.toString())
                mPreferrences.saveName_lname(ET_nameOPT.text.toString()+" "+ET_lnameOPT.text.toString())
                mPreferrences.saveImage(imageName.name)
                startActivity(
                    Intent(context,MainActivityOperator::class.java)
                )
                (context as Activity).finish()
            }
            else{
                Toast.makeText(context, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==PICK_IMAGE && resultCode==Activity.RESULT_OK){
            try {
                val pickedImage :Uri = data?.data!!
                val filePath = arrayOf(MediaStore.Images.Media.DATA)
                val cursor: Cursor =
                    activity!!.contentResolver.query(pickedImage, filePath, null, null, null)!!
                cursor.moveToFirst()
                val imagePath: String = cursor.getString(cursor.getColumnIndex(filePath[0]))
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val bitmap = BitmapFactory.decodeFile(imagePath, options)

                Log.d("As5da1sda",File(imagePath).absolutePath )
                imageName = File(imagePath)
                Picasso.get().load(imageName).into(addImage)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}