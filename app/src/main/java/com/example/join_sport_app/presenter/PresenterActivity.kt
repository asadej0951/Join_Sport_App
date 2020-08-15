package com.example.myapplicationproject.presenter

import android.util.Log
import com.example.join_sport_app.body.activity.BodyActivityUser
import com.example.join_sport_app.body.activity.BodyDeleteJoinActivity
import com.example.myapplicationproject.body.BodyCheck
import com.example.myapplicationproject.body.BodyInsertActivity
import com.example.myapplicationproject.body.BodyJoin
import com.example.myapplicationproject.model.ResponseActivity
import com.example.myapplicationproject.model.ResponseCheck
import com.example.myapplicationproject.model.ResponseJoin
import com.example.myapplicationproject.model.ResponseUpdateAC
import com.example.myapplicationproject.rest.DataModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class PresenterActivity {
    var mDisposable: Disposable? = null


    fun CreatePresenterRX(
        user_id :String,
        ac_name :String,
        ac_type :String,
        ac_time :String,
        ac_number :Int,
        ac_numberjoin :Int,
        ac_lat :String,
        ac_long :String,
        user_name:String,
        u_img:String,
        dataResponse:(ResponseActivity)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doCreate(BodyInsertActivity(user_id, ac_name, ac_type, ac_time, ac_number, ac_numberjoin, ac_lat, ac_long,user_name,u_img))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseActivity>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseActivity) {
                    Log.d("Create",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("Create",e.message.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun ShowActivityPresenterRX(
        dataResponse:(List<ResponseActivity>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doshowactivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseActivity>>(){
                override fun onComplete() {}
                override fun onNext(response: List<ResponseActivity>) {
                    Log.d("messsage",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("messsage",e.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }

    fun UpdateActivityPresenterRX(
        id:Int,user_id :String,
        ac_name :String,
        ac_type :String,
        ac_time :String,
        ac_number :Int,
        ac_numberjoin :Int,
        ac_lat :String,
        ac_long :String,
        user_name: String,
        u_img: String,
        dataResponse:(ResponseUpdateAC)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doUpdatenumberjoin(id,BodyInsertActivity(user_id, ac_name, ac_type, ac_time, ac_number, ac_numberjoin, ac_lat, ac_long,user_name,u_img))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseUpdateAC>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseUpdateAC) {
                    Log.d("messsageup",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                }

            })
    }

    fun CheckPresenterRX(
        user:String,
        acid:String,
        dataResponse:(List<ResponseCheck>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doCheck(BodyCheck(user,acid))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseCheck>>(){
                override fun onComplete() {}
                override fun onNext(response: List<ResponseCheck>) {
                    Log.d("messsagecheck",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                    Log.d("messsagecheck",e.message.toString())
                }

            })
    }

    fun JoinPresenterRX(
        user_id: String,ac_id:String,JS:String,
        dataResponse:(ResponseJoin)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.dojoin(BodyJoin(user_id,ac_id,JS))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseJoin>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseJoin) {
                    Log.d("messsage",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                }

            })
    }

    fun DeletePresenterRX(
        ac_id:Int,
        dataResponse:(ResponseActivity)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.dodeleteactivity(ac_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseActivity>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseActivity) {
                    Log.d("DELETEAC",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun DeleteJoinPresenterRX(
        user_id: String,
        ac_id:String,
        dataResponse:(ResponseJoin)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.dodeleteJoin(BodyDeleteJoinActivity(user_id, ac_id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseJoin>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseJoin) {
                    Log.d("Delete",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("Delete",e.message.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }

    fun ActivityUserPresenterRX(
        user_id: String,
        dataResponse:(List<ResponseActivity>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doActivityUser(
            BodyActivityUser(
                user_id
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseActivity>>(){
                override fun onComplete() {}
                override fun onNext(response: List<ResponseActivity>) {
                    Log.d("messsage",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun UpdateDataActivityPresenterRX(
        id:Int,
        user_id :String,
        ac_name :String,
        ac_type :String,
        ac_time :String,
        ac_number :Int,
        ac_numberjoin :Int,
        ac_lat :String,
        ac_long :String,
        user_name: String,
        u_img: String,
        dataResponse:(ResponseUpdateAC)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doUpdateDataActivity(id,BodyInsertActivity(user_id, ac_name, ac_type, ac_time, ac_number, ac_numberjoin, ac_lat, ac_long,user_name,u_img))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseUpdateAC>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseUpdateAC) {
                    Log.d("messsageup",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                }

            })
    }
}