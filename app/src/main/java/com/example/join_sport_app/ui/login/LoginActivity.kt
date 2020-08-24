package com.example.join_sport_app.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.join_sport_app.MainActivity
import com.example.join_sport_app.MainActivityOperator
import com.example.join_sport_app.R
import com.example.join_sport_app.model.ResponseImageUser
import com.example.join_sport_app.model.ResponseLoginOPT
import com.example.join_sport_app.model.operator.ResponseImageOPT
import com.example.join_sport_app.presenter.PresenterOperator
import com.example.myapplicationproject.model.ResponseLogin
import com.example.myapplicationproject.presenter.PresenterUser
import com.example.myapplicationproject.rest.local.Preferrences
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.dialog_message_show.view.*

class LoginActivity : AppCompatActivity() {
    var mLoginPresenter = PresenterUser()
    var mPresenterOPT = PresenterOperator()
    var mPreferrences = Preferrences(this)
    lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initview()

    }
    private fun initview() {
        if (checkIsUsername(mPreferrences.getusername())&&checkIsStatus(mPreferrences.getstatus())){
            if (mPreferrences.getstatus()=="ผู้ใช้งานทั่วไป"){
                finish()
                startActivity(
                    Intent(this, MainActivity::class.java)
                )
            }
            else if (mPreferrences.getstatus()=="ผู้ประกอบการ"){
                finish()
                startActivity(
                    Intent(this,MainActivityOperator::class.java)
                )
            }

        }
        val btn_log = findViewById<Button>(R.id.login_btn)

        btn_log.setOnClickListener {
            val inselectButton = RG_status.checkedRadioButtonId
            radioButton = findViewById(inselectButton)
            val Status = radioButton.text.toString()

            val ET_Username = findViewById<EditText>(R.id.ET_username)
            val ET_Password = findViewById<EditText>(R.id.Password_ET)
            var UN = ET_Username.text.toString()
            var PW = ET_Password.text.toString()
            if(UN!=""&&PW!=""){
                if (Status =="ผู้ใช้งานทั่วไป"){
                    setapi(UN,PW,Status)
                }
                else if (Status =="ผู้ประกอบการ"){
                    setapiOPT(UN,PW,Status)
                }

            }
            else if (UN!=""&&PW==""){
                ShowDialogMessage("กรุณากรอกรหัสผ่าน")
                //Toast.makeText(applicationContext,"กรุณากรอกรหัสผ่าน", Toast.LENGTH_SHORT).show()
            }
            else if (UN==""&&PW!=""){
                ShowDialogMessage("กรุณากรอก ID ")
//                Toast.makeText(applicationContext,"กรุณากรอก ID ", Toast.LENGTH_SHORT).show()
            }
            else{
                ShowDialogMessage("กรุณากรอก ID และ รหัสผ่าน ")
//                Toast.makeText(applicationContext,"กรุณากรอก ID และ รหัสผ่าน ", Toast.LENGTH_SHORT).show()
            }

        }
        val Tv_Register = findViewById<TextView>(R.id.Register_tv)
        Tv_Register.setOnClickListener {
            startActivity(
                Intent(applicationContext,RegisterActivity::class.java)
            )
        }
    }
    private fun ShowDialogMessage(message:String){
        val View = layoutInflater.inflate(R.layout.dialog_message_show,null)
        View.Message_dialog_Show.setText(message)
        View.btn_OK_show.setText("ปิด")
        val dialog = AlertDialog.Builder(this@LoginActivity).apply {
            setTitle("คำเตือน!!")
            setIcon(R.drawable.alert)
            setView(View)
        }
        val dialogButton = dialog.show()
        View.btn_OK_show.setOnClickListener {
            dialogButton.dismiss()
        }
    }

    private fun setapiOPT(un: String, pw: String, status: String) {
        //เช็ค login ของผู้ประกอบการ
        mPresenterOPT.LoginOPTPresenterRX(un,pw,status,this::LoginOPTNext,this::LoginError)
    }

    private fun LoginError(message: String) {
        ShowDialogMessage("รหัสผ่านไม่ถูกต้อง")
        //Toast.makeText(applicationContext, "รหัสผ่านไม่ถูกต้อง", Toast.LENGTH_SHORT).show()
    }


    private fun LoginOPTNext(responselogin: List<ResponseLoginOPT>) {
        mPreferrences.saveId(responselogin[0].o_id)
        mPreferrences.saveUsername(responselogin[0].o_user)
        mPreferrences.saveName_lname(responselogin[0].o_name+"  "+responselogin[0].o_lname)
        mPreferrences.saveStatus(responselogin[0].o_status)
       mLoginPresenter.SelectImageOPTPresenterRX(responselogin[0].o_id.toInt(),this::SelectImageOPTNext,this::SelectImageOPTError)
    }

    private fun SelectImageOPTError(message: String) {
    }

    private fun SelectImageOPTNext(response :List<ResponseImageOPT>) {
        mPreferrences.saveIDImage(response[0].imgO_id.toString())
        mPreferrences.saveImage(response[0].o_img)
        startActivity(
            Intent(applicationContext,MainActivityOperator::class.java)
        )
        finish()
    }

    private fun setapi(un: String, pw: String, status: String) {
        //เช็ค login ของผู้ใช้ทั่วไป
        mLoginPresenter.LoginPresenterRX(un,pw,status,
            this::onSuccessSubscrib,
            this::onErrorSubscrib)
    }
    private fun onErrorSubscrib(message :String) {
        ShowDialogMessage("รหัสผ่านไม่ถูกต้อง")
    }

    private fun onSuccessSubscrib(responseLogin: List<ResponseLogin>) {
        //ถ้าผ่านให้เข้าสู่ระบบ
        mPreferrences.saveId(responseLogin[0].u_id)
        mPreferrences.saveUsername(responseLogin[0].u_user)
        mPreferrences.saveName_lname(responseLogin[0].u_name+"  "+responseLogin[0].u_lname)
        mPreferrences.saveStatus(responseLogin[0].u_status)
        mLoginPresenter.SelectImageUserPresenterRX(responseLogin[0].u_id.toInt(),this::SelectImageUserNext,this::SelectImageUserError)

    }

    private fun SelectImageUserNext(response:List<ResponseImageUser>) {
        mPreferrences.saveIDImage(response[0].imgU_id)
        mPreferrences.saveImage(response[0].u_img)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun SelectImageUserError(message: String) {
    }

    fun checkIsUsername(username:String):Boolean{
        return  username.isNotEmpty()
    }
    fun checkIsStatus(status:String):Boolean{
        return status.isNotEmpty()
    }
}