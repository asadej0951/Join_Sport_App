package com.example.join_sport_app.presenter

import android.util.Log
import com.example.join_sport_app.body.admin.BodyLoginAM
import com.example.join_sport_app.body.operator.BodyRegisterOperator
import com.example.join_sport_app.model.admin.ResponseAdmin
import com.example.join_sport_app.model.operator.ResponseOperator
import com.example.myapplicationproject.body.BodyLogin
import com.example.myapplicationproject.rest.DataModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class PresenterAdmin {
    var mDisposable: Disposable? = null

    fun LoginAMPresenterRX(
        A_username:String,A_Password:String,
        dataResponse:(List<ResponseAdmin>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.dologinAM(BodyLoginAM(A_username,A_Password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseAdmin>>(){
                override fun onComplete() {}
                override fun onNext(responseLogin: List<ResponseAdmin>) {
                    Log.d("Login",responseLogin.toString())
                    dataResponse.invoke(responseLogin)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                    Log.d("Login",e.message.toString())
                }

            })
    }
}