package com.example.myapplicationproject.presenter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.util.Base64
import android.util.Log
import com.example.join_sport_app.body.activity.BodyGetCheckTimeStadium
import com.example.join_sport_app.body.user.BodyGetCheckStadiumOPT
import com.example.join_sport_app.body.user.BodyJoinStadiam
import com.example.join_sport_app.body.user.BodyStatusJoinStadium
import com.example.join_sport_app.body.user.BodyUploadImage
import com.example.join_sport_app.model.ResponseImageUser
import com.example.join_sport_app.model.ResponseJoinStadium
import com.example.join_sport_app.model.ResponseUploadImage
import com.example.join_sport_app.model.operator.ResponseImageOPT
import com.example.myapplicationproject.body.BodyInsertActivity
import com.example.myapplicationproject.body.BodyLogin
import com.example.myapplicationproject.body.BodylnsertUser
import com.example.myapplicationproject.model.ResponseLogin
import com.example.myapplicationproject.model.ResponseUser
import com.example.myapplicationproject.rest.DataModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.TimeUnit

class PresenterUser{


    var mDisposable: Disposable? = null


    fun UploadImageUserPresenterRX(
        u_id:String ,
        u_img: File,
        res:(Boolean)-> Unit
    ){
        val encodedImagePic1: String
        var UploadImage = ArrayList<BodyUploadImage.Data>()

        if (u_img.absolutePath != "") {
            val myBitmap = BitmapFactory.decodeFile(u_img.absolutePath)

            if (myBitmap != null) {
                Log.d("register", myBitmap.toString())
                val byteArrayOutputStream =
                    ByteArrayOutputStream()
                myBitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    70,
                    byteArrayOutputStream
                )
                val byteArrayImage =
                    byteArrayOutputStream.toByteArray()
                encodedImagePic1 = Base64.encodeToString(
                    byteArrayImage,
                    Base64.DEFAULT
                )
                val uploadData = BodyUploadImage.Data(
                    u_id,u_img.name,"data:image/jpeg;base64,$encodedImagePic1"
                )
                UploadImage.add(uploadData)

            }
        }
        mDisposable = DataModule.myAppService.douploadimageUser(BodyUploadImage(UploadImage))
            .subscribeOn(Schedulers.io())
            .timeout(20, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseUploadImage>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseUploadImage) {
                    if (response.isSuccessful){
                        res.invoke(true)
                        Log.d("register",response.message)
                    }else{
                        res.invoke(false)
                    }
                }
                override fun onError(e: Throwable) {
                    Log.d("register",e.message)
                    res.invoke(false)
                }

            })
    }
    fun RegisterPresenterRX(
        u_user: String,
        u_pass: String,
        u_name: String,
        u_lname: String,
        u_email: String,
        u_tel: String,
        u_status: String,
        dataResponse:(ResponseUser)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doRegister(BodylnsertUser(u_user, u_pass, u_name, u_lname, u_email, u_tel, u_status))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseUser>(){
                override fun onComplete() {}
                override fun onNext(responseLogin: ResponseUser) {
                    Log.d("messsage",responseLogin.toString())
                    dataResponse.invoke(responseLogin)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                    Log.d("messsage",e.message.toString())
                }

            })
    }
    fun LoginPresenterRX(
        u_user:String,
        u_pass:String,
        u_status:String,
        dataResponse:(List<ResponseLogin>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.dologin(BodyLogin(u_user, u_pass, u_status))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseLogin>>(){
                override fun onComplete() {}
                override fun onNext(responseLogin: List<ResponseLogin>) {
                    Log.d("LOGIN",responseLogin.toString())
                    dataResponse.invoke(responseLogin)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                    Log.d("LOGIN",e.message.toString())
                }

            })
    }
    fun SelectImageUserPresenterRX(
        u_id:Int,
        dataResponse:(List<ResponseImageUser>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doSelectImageUser(u_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseImageUser>>(){
                override fun onComplete() {}
                override fun onNext(responseLogin: List<ResponseImageUser>) {
                    Log.d("LOGIN",responseLogin.toString())
                    dataResponse.invoke(responseLogin)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                    Log.d("LOGIN",e.message.toString())
                }

            })
    }
    fun SelectImageOPTPresenterRX(
        o_id:Int,
        dataResponse:(List<ResponseImageOPT>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doSelectImageOPT(o_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseImageOPT>>(){
                override fun onComplete() {}
                override fun onNext(responseLogin: List<ResponseImageOPT>) {
                    Log.d("LOGIN",responseLogin.toString())
                    dataResponse.invoke(responseLogin)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                    Log.d("LOGIN",e.message.toString())
                }

            })
    }
    fun GetUserPresenterRX(
        user:Int,
        dataResponse:(List<ResponseUser>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.dogetuser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseUser>>(){
                override fun onComplete() {}
                override fun onNext(responseLogin: List<ResponseUser>) {
                    Log.d("GetUser",responseLogin.toString())
                    dataResponse.invoke(responseLogin)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                    Log.d("GetUser",e.message.toString())
                }

            })
    }

    fun updateUserPresenterRX(
        user_id:Int,
        u_user: String,
        u_pass: String,
        u_name: String,
        u_lname: String,
        u_email: String,
        u_tel: String,
        u_status: String,
        dataResponse:(List<ResponseUser>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doupdateuser(user_id,BodylnsertUser(u_user, u_pass, u_name, u_lname, u_email, u_tel, u_status))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseUser>>(){
                override fun onComplete() {}
                override fun onNext(responseLogin: List<ResponseUser>) {
                    Log.d("messsage",responseLogin.toString())
                    dataResponse.invoke(responseLogin)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                    Log.d("messsage",e.message.toString())
                }

            })
    }
    fun JoinStadiamPresenterRX(
        u_id:String,
        s_id:String,
        o_id:String,
        u_name:String,
        s_name:String,
        r_timein:String,
        r_timeout:String,
        r_Date:String,
        u_phone:String,
        u_price:Int,
        r_type:String,
        r_status:String,
        dataResponse:(ResponseJoinStadium)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doJoinStadium(BodyJoinStadiam(u_id, s_id,o_id, u_name, s_name, r_timein, r_timeout,r_Date, u_phone, u_price, r_type,r_status))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseJoinStadium>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseJoinStadium) {
                    Log.d("JoinStadiam",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                    Log.d("JoinStadiam",e.toString())
                }

            })
    }
    fun dogetcheckTimeStadiumPresenterRX(
        r_id: String,
        r_Date: String,
        r_status: String,
        dataResponse:(List<ResponseJoinStadium>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.dogetcheckTimeStadium(BodyGetCheckTimeStadium(r_id,r_Date, r_status))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseJoinStadium>>(){
                override fun onComplete() {}
                override fun onNext(response: List<ResponseJoinStadium>) {
                    Log.d("getcheckStadium",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("getcheckStadium",e.message.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun CheckStatusPresenterRX(
        u_id: String,
        dataResponse:(List<ResponseJoinStadium>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.dogetcheckStatus(BodyStatusJoinStadium(u_id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseJoinStadium>>(){
                override fun onComplete() {}
                override fun onNext(response: List<ResponseJoinStadium>) {
                    Log.d("getcheckStadium",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("getcheckStadium",e.message.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }
}