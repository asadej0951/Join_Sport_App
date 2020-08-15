package com.example.join_sport_app.presenter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.util.Log
import com.example.join_sport_app.body.operator.*
import com.example.join_sport_app.body.user.BodyGetCheckStadiumOPT
import com.example.join_sport_app.body.user.BodyJoinStadiam
import com.example.join_sport_app.body.user.BodyUploadImage
import com.example.join_sport_app.body.user.BodygetPostProfile
import com.example.join_sport_app.model.*
import com.example.join_sport_app.model.operator.ResponseOPTProfile
import com.example.join_sport_app.model.operator.ResponseOperator
import com.example.join_sport_app.model.operator.ResponseUpdateOPT
import com.example.myapplicationproject.body.BodyLogin
import com.example.myapplicationproject.model.ResponseActivity
import com.example.myapplicationproject.model.ResponseLogin
import com.example.myapplicationproject.rest.DataModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.TimeUnit

class PresenterOperator {

    var mDisposable: Disposable? = null

    fun RegisterOPTPresenterRX(
        o_user:String, o_pass:String,o_name:String,o_lname:String,o_email:String,o_tel:String,o_Sname:String,o_address:String, o_status:String,
        dataResponse:(ResponseOperator)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doRegisterOPT(BodyRegisterOperator(o_user, o_pass, o_name, o_lname, o_email, o_tel, o_Sname, o_address, o_status))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseOperator>(){
                override fun onComplete() {}
                override fun onNext(responseLogin: ResponseOperator) {
                    Log.d("Register",responseLogin.toString())
                    dataResponse.invoke(responseLogin)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                    Log.d("Register",e.message.toString())
                }

            })
    }
    fun UploadImageOPTPresenterRX(
        o_id:String,
        o_img: File,
        res:(Boolean)-> Unit
    ){
        val encodedImagePic1: String
        var UploadImage = ArrayList<BodyUploadImageOPT.Data>()

        if (o_img.absolutePath != "") {
            val myBitmap = BitmapFactory.decodeFile(o_img.absolutePath)

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
                val uploadData = BodyUploadImageOPT.Data(
                    o_id,o_img.name,"data:image/jpeg;base64,$encodedImagePic1"
                )
                UploadImage.add(uploadData)

            }
        }
        mDisposable = DataModule.myAppService.doUploadimageOPT(BodyUploadImageOPT(UploadImage))
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
    fun LoginOPTPresenterRX(
        o_user:String, o_pass:String, o_status:String,
        dataResponse:(List<ResponseLoginOPT>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.dologinOPT(BodyLoinOPT(o_user, o_pass, o_status))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseLoginOPT>>(){
                override fun onComplete() {}
                override fun onNext(responseLogin: List<ResponseLoginOPT>) {
                    Log.d("LOGIN",responseLogin.toString())
                    dataResponse.invoke(responseLogin)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                    Log.d("LOGIN",e.message.toString())
                }

            })
    }
    fun PostStadiamPresenterRX(
        s_name:String,
        s_lat:String,
        s_long:String,
        s_address:String,
        s_type:String,
        s_price:Int,
        s_timeopen:String,
        s_timeclose: String,
        o_id:String,
        o_user: String,
        o_img: String,
        dataResponse:(ResponseStadiam)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doPostStadium(BodyStadiam(s_name, s_lat, s_long,s_address, s_type,s_price,s_timeopen,s_timeclose, o_id, o_user,o_img))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseStadiam>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseStadiam) {
                    Log.d("PostStadiam",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                    Log.d("PostStadiam",e.message.toString())
                }

            })
    }
    fun UploadImageStadiumPresenterRX(
        s_id:Int ,
        s_img: List<String>,
        res:(Boolean)-> Unit
    ){
        var encodedImagePic1: String
        var UploadImage = ArrayList<BodyImageStadium.Data>()
        for (i in s_img) {

                val file = File(i)

                if (file.absolutePath != "") {

                    //เอารูปมาแปลงเป็น bitmap
                    val myBitmap = BitmapFactory.decodeFile(file.absolutePath)
                    if (myBitmap != null) {
                        val byteArrayOutputStream =
                            ByteArrayOutputStream()
                        myBitmap.compress(
                            Bitmap.CompressFormat.JPEG,
                            70,
                            byteArrayOutputStream
                        )

                        //เแปลงรูปจาก bitmap เป็น base 64
                        val byteArrayImage =
                            byteArrayOutputStream.toByteArray()
                        encodedImagePic1 = encodeToString(
                            byteArrayImage,
                            DEFAULT
                        )

                        val bodyImage =
                            BodyImageStadium.Data(
                               s_id.toString(),
                                file.name, "data:image/jpeg;base64,$encodedImagePic1")

                        UploadImage.add(bodyImage)
                        Log.d("Upload",UploadImage.toString())
                    }
                }
            }
        mDisposable = DataModule.myAppService.doUploadimageStadium(BodyImageStadium(UploadImage))
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
    fun ShowStadiamPresenterRX(
        dataResponse:(List<ResponseShowStadium>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doShowStadium()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseShowStadium>>(){
                override fun onComplete() {}
                override fun onNext(response: List<ResponseShowStadium>) {
                    Log.d("messsage",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun ShowimageStadiumPresenterRX(
        s_id: Int,
        dataResponse:(List<ResponterImageStadium>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doShowimageStadium(s_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponterImageStadium>>(){
                override fun onComplete() {}
                override fun onNext(response: List<ResponterImageStadium>) {
                    Log.d("Show",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("Show",e.message.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun dogetcheckStadiumOPTPresenterRX(
        o_id: String,
        dataResponse:(List<ResponseJoinStadium>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.dogetcheckStadiumOPT(BodyGetCheckStadiumOPT(o_id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseJoinStadium>>(){
                override fun onComplete() {}
                override fun onNext(response: List<ResponseJoinStadium>) {
                    Log.d("getcheckStadium",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun updateStatusJoinStadiumPresenterRX(
        r_id:Int,
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
        mDisposable = DataModule.myAppService.doupdateStatusJoinStadium(r_id,
            BodyJoinStadiam(u_id, s_id, o_id, u_name, s_name, r_timein, r_timeout, r_Date, u_phone, u_price, r_type, r_status))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseJoinStadium>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseJoinStadium) {
                    Log.d("getcheckStadium",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun DeleteJoinStadiumOPTPresenterRX(
        r_id: Int,
        dataResponse:(ResponseJoinStadium)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.daDeleteJoinStadium(r_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseJoinStadium>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseJoinStadium) {
                    Log.d("getcheckStadium",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun ManageStadiumPresenterRX(
        o_id: Int,
        dataResponse:(List<ResponseShowStadium>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doManageStadium(o_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseShowStadium>>(){
                override fun onComplete() {}
                override fun onNext(response: List<ResponseShowStadium>) {
                    Log.d("ManageStadium",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("ManageStadium",e.message.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun DeleteJoinStadium_sIDPresenterRX(
        s_id: Int,
        dataResponse:(ResponseJoinStadium)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doDeleteJoinStadium_sID(s_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseJoinStadium>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseJoinStadium) {
                    Log.d("getcheckStadium",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun DeleteStadiumPresenterRX(
        s_id: Int,
        dataResponse:(ResponseStadiam)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doDeleteStadium(s_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseStadiam>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseStadiam) {
                    Log.d("getcheckStadium",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun ProfileOPTPresenterRX(
        o_id: Int,
        dataResponse:(List<ResponseOPTProfile>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doOperator(o_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseOPTProfile>>(){
                override fun onComplete() {}
                override fun onNext(response: List<ResponseOPTProfile>) {
                    Log.d("Profile",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("Profile",e.message.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun UpdateOPTPresenterRX(
        o_id: Int,o_user:String, o_pass:String, o_name:String, o_lname:String, o_email:String, o_tel:String, o_Sname:String, o_address:String, o_status:String,
        dataResponse:(ResponseUpdateOPT)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doUpdateOPT(o_id,BodyRegisterOperator(o_user, o_pass, o_name, o_lname, o_email, o_tel, o_Sname, o_address, o_status))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseUpdateOPT>(){
                override fun onComplete() {}
                override fun onNext(responseLogin: ResponseUpdateOPT) {
                    Log.d("Register",responseLogin.toString())
                    dataResponse.invoke(responseLogin)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                    Log.d("Register",e.message.toString())
                }

            })
    }
    fun UpdateimageOPTPresenterRX(
        o_id:String,
        o_img: File,
        res:(Boolean)-> Unit
    ){
        val encodedImagePic1: String
        var UploadImage = ArrayList<BodyUploadImageOPT.Data>()

        if (o_img.absolutePath != "") {
            val myBitmap = BitmapFactory.decodeFile(o_img.absolutePath)

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
                val uploadData = BodyUploadImageOPT.Data(
                    o_id,o_img.name,"data:image/jpeg;base64,$encodedImagePic1"
                )
                UploadImage.add(uploadData)

            }
        }
        mDisposable = DataModule.myAppService.doUpdateimageOPT(BodyUploadImageOPT(UploadImage))
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


}