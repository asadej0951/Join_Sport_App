package com.example.join_sport_app.presenter

import android.util.Log
import com.example.join_sport_app.body.operator.BodyRegisterOperator
import com.example.join_sport_app.model.getname.ResponseNameOPT
import com.example.join_sport_app.model.operator.ResponseOperator
import com.example.myapplicationproject.rest.DataModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class PresenterGetName {
    var mDisposable: Disposable? = null

    fun GetNameOPTPresenterRX(
        o_id:Int,
        dataResponse:(ResponseNameOPT)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.dogetnameOPT(o_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseNameOPT>(){
                override fun onComplete() {}
                override fun onNext(responseLogin: ResponseNameOPT) {
                    Log.d("Register",responseLogin.toString())
                    dataResponse.invoke(responseLogin)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                    Log.d("Register",e.message.toString())
                }

            })
    }
}