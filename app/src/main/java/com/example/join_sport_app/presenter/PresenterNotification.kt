package com.example.join_sport_app.presenter

import android.util.Log
import com.example.join_sport_app.body.BodyNotification
import com.example.join_sport_app.model.ResponseListNotification
import com.example.join_sport_app.model.ResponseNotification
import com.example.myapplicationproject.body.BodyInsertActivity
import com.example.myapplicationproject.model.ResponseActivity
import com.example.myapplicationproject.rest.DataModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class PresenterNotification {
    var mDisposable: Disposable? = null


    fun PostNotificationPresenterRX(
        u_id :String,
        subject:String,
        message:String,
        dataResponse:(ResponseListNotification)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.Postnotification(BodyNotification(u_id, subject,message))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseListNotification>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseListNotification) {
                    Log.d("Notification",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("Notification",e.message.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun ChecknotificationPresenterRX(
        nft_id :Int,
        dataResponse:(List<ResponseNotification>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.Checknotification(nft_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseNotification>>(){
                override fun onComplete() {}
                override fun onNext(response: List<ResponseNotification>) {
                    Log.d("Notification",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("Notification",e.message.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun deletenotificationPresenterRX(
        nft_id :Int,
        dataResponse:(ResponseNotification)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.deletenotification(nft_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseNotification>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseNotification) {
                    Log.d("Notification",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("Notification",e.message.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }
}