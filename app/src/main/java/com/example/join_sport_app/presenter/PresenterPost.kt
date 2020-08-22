package com.example.join_sport_app.presenter

import android.util.Log
import com.example.join_sport_app.body.post.*
import com.example.join_sport_app.body.user.BodygetPostProfile
import com.example.join_sport_app.model.ResponseComment
import com.example.join_sport_app.model.ResponsePost
import com.example.myapplicationproject.model.ResponseUser
import com.example.myapplicationproject.rest.DataModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class PresenterPost {
    var mDisposable: Disposable? = null


    fun PostPresenterRX(
        user_id :String,
        username:String,
        u_img:String,
        p_message :String,
        p_time:String,user_status:String,
        dataResponse:(ResponsePost)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doPost(
            BodyPost(
                user_id,
                username,
                u_img,
                p_message,p_time,user_status
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponsePost>(){
                override fun onComplete() {}
                override fun onNext(response: ResponsePost) {
                    Log.d("Post",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun ShowPostPresenterRX(
        dataResponse:(List<ResponsePost>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doShowPost()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponsePost>>(){
                override fun onComplete() {}
                override fun onNext(response: List<ResponsePost>) {
                    Log.d("Post",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun PostProfilePresenterRX(
        user_id: String,user_status: String,
        dataResponse:(List<ResponsePost>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.dogetPostProfile(BodygetPostProfile(user_id, user_status))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponsePost>>(){
                override fun onComplete() {}
                override fun onNext(response: List<ResponsePost>) {
                    Log.d("Post",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun CommentPresenterRX(
        user_id: String,
        p_id:String,
        com_message:String,
        username: String,
        img:String,
        com_time:String,
        dataResponse:(ResponseComment)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.doComment(BodyComment(user_id, p_id, com_message,username,img, com_time))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseComment>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseComment) {
                    Log.d("Comment",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("Comment",e.message.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun updateDataCommentImagePresenterRX(
        user_id: Int,
        img:String,
        dataResponse:(ResponseComment)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.updateDataCommentImage(user_id, BodyCommentImage(img))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponseComment>(){
                override fun onComplete() {}
                override fun onNext(response: ResponseComment) {
                    Log.d("CommentImage",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("CommentImage",e.message.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun GetCommentPresenterRX(
        p_id:String,
        dataResponse:(List<ResponseComment>)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.dogetComment(BodyGetComment(p_id))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<ResponseComment>>(){
                override fun onComplete() {}
                override fun onNext(response: List<ResponseComment>) {
                    Log.d("Comment",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun deletePostPresenterRX(
        p_id: Int,
        dataResponse:(ResponsePost)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.deletePost(p_id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponsePost>(){
                override fun onComplete() {}
                override fun onNext(response: ResponsePost) {
                    Log.d("deletePost",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("deletePost",e.message.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun updateDataPostPresenterRX(
        p_id: Int,
        user_id :String,
        username:String,
        u_img:String,
        p_message :String,
        p_time:String,user_status:String,
        dataResponse:(ResponsePost)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.updateDataPost(p_id,
            BodyPost(user_id, username, u_img, p_message, p_time, user_status)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponsePost>(){
                override fun onComplete() {}
                override fun onNext(response: ResponsePost) {
                    Log.d("updateDataPost",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("updateDataPost",e.message.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }
    fun updateDataPostImagePresenterRX(
        user_id :Int,
        u_img:String,
        dataResponse:(ResponsePost)->Unit,
        MessageError:(String)->Unit
    ){
        mDisposable = DataModule.myAppService.updateDataPostImage(user_id,BodyImagePost(u_img)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<ResponsePost>(){
                override fun onComplete() {}
                override fun onNext(response: ResponsePost) {
                    Log.d("updateDataPostImage",response.toString())
                    dataResponse.invoke(response)
                }
                override fun onError(e: Throwable) {
                    Log.d("updateDataPostImage",e.message.toString())
                    MessageError.invoke(e.message!!)
                }

            })
    }
}