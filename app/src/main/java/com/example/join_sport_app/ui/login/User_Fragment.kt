package com.example.join_sport_app.ui.login

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.join_sport_app.MainActivity
import com.example.join_sport_app.R
import com.example.myapplicationproject.model.ResponseLogin
import com.example.myapplicationproject.model.ResponseUser
import com.example.myapplicationproject.presenter.PresenterUser
import com.example.myapplicationproject.rest.local.Preferrences
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_.*
import kotlinx.android.synthetic.main.fragment_user_.view.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception

class User_Fragment : Fragment() {

    var mPresenterRegister = PresenterUser()
    lateinit var mPreferrences: Preferrences
    var imageName :File? = null
    private lateinit var addImage : ImageView
    private lateinit var addImagetoDataBase:String
    private var PICK_IMAGE = 999
    var Image :String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_user_, container, false)

        initview(root)
        return root.rootView
    }

    private fun initview(root: View) {
        addImage = root.findViewById(R.id.imageUser)
        val Btn_img = root.findViewById<FloatingActionButton>(R.id.btn_img_User)
        Btn_img.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i,PICK_IMAGE)

        }
        root.btn_UserSave.setOnClickListener {
            if (imageName != null) {
                val username = root.username_User.text.toString()
                val password = root.password_User.text.toString()
                val passwordCheck = root.password_User2.text.toString()
                val name = root.name_User.text.toString()
                val lastname = root.lastname_User.text.toString()
                val email = root.email_User.text.toString()
                val tel = root.tel_User.text.toString()
                if (username != "")
                { if (password !=""){
                    if (passwordCheck != "") {
                        if (password == passwordCheck) {
                            if (name != "") {
                                if (lastname !="") {
                                    if (email != "") {
                                        if (tel != "") {
                                            setapi(username, password, name, lastname, email, tel)
                                        }
                                        else{
                                            Toast.makeText(context, "กรุณากรอกเบอร์โทรศัทพ์", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    else{
                                        Toast.makeText(context, "กรุณากรอกอีเมล", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                else{
                                    Toast.makeText(context, "กรุณากรอกนามสกุล", Toast.LENGTH_SHORT).show()
                                }
                            }
                            else{
                                Toast.makeText(context, "กรุณากรอกชื่อ", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            root.password_User.text?.clear()
                            root.password_User2.text?.clear()
                            Toast.makeText(context, "กรุณารหัสผ่านไม่ตรงกัน", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(context, "กรุณายืนยันรหัสผ่าน", Toast.LENGTH_SHORT).show()
                    }
                }
                    else{Toast.makeText(context, "กรุณากรอกรหัสผ่าน", Toast.LENGTH_SHORT).show()}
                    }
                else{
                    Toast.makeText(context, "กรุณากรอกชื่อผู้ใช้", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(context, "กรุณาเลือกรูปภาพ", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setapi(username: String, password: String, name: String, lastname: String, email: String, tel: String) {
        val u_status:String = "ผู้ใช้งานทั่วไป"
        mPresenterRegister.RegisterPresenterRX(
                    username, password,
                    name, lastname,
                    email, tel,
                    u_status,this::onSuccessRegister,this::onErrorRegister)
    }

    private fun onErrorRegister(message:String) {
        Toast.makeText(context, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show()
    }

    private fun onSuccessRegister(response : ResponseUser) {
        var u_id= response.u_id
        mPresenterRegister.UploadImageUserPresenterRX(
            u_id,imageName!!
        ){if (it){
            mPreferrences = Preferrences(context!!)
            mPreferrences.saveId(u_id)
            mPreferrences.saveName_lname(name_User.text.toString()+" "+lastname_User.text.toString())
            mPreferrences.saveUsername(username_User.text.toString())
            mPreferrences.saveImage(imageName!!.name)
            mPreferrences.saveStatus("ผู้ใช้งานทั่วไป")
            startActivity(
                Intent(context,MainActivity::class.java)
            )
            (context as Activity).finish()
        }
            else{
            Toast.makeText(context, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show()}
        }
    }

       override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==PICK_IMAGE && resultCode==Activity.RESULT_OK){
            try {
                val pickedImage : Uri = data?.data!!
                val filePath = arrayOf(MediaStore.Images.Media.DATA)
                val cursor: Cursor =activity!!.contentResolver.query(pickedImage,filePath,null,null,null)!!
//                    activity!!.contentResolver.query(pickedImage, filePath, null, null, null)!!
                cursor.moveToFirst()
                val imagePath: String = cursor.getString(cursor.getColumnIndex(filePath[0]))
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val bitmap = BitmapFactory.decodeFile(imagePath, options)

                Log.d("As5da1sda", File(imagePath).absolutePath )
                imageName = File(imagePath)
                Picasso.get().load(imageName!!).into(addImage)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}