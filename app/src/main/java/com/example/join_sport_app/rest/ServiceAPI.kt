package com.example.myapplicationproject.rest

import com.example.join_sport_app.body.BodyNotification
import com.example.join_sport_app.body.activity.BodyActivityUser
import com.example.join_sport_app.body.activity.BodyDeleteJoinActivity
import com.example.join_sport_app.body.activity.BodyGetCheckTimeStadium
import com.example.join_sport_app.body.operator.*
import com.example.join_sport_app.body.post.*
import com.example.join_sport_app.body.user.*
import com.example.join_sport_app.model.*
import com.example.join_sport_app.model.getname.ResponseNameOPT
import com.example.join_sport_app.model.operator.ResponseImageOPT
import com.example.join_sport_app.model.operator.ResponseOPTProfile
import com.example.join_sport_app.model.operator.ResponseOperator
import com.example.join_sport_app.model.operator.ResponseUpdateOPT
import com.example.myapplicationproject.body.*
import com.example.myapplicationproject.model.*
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ServiceAPI {

    //user
    @POST("/registeruser")
    fun doRegister(@Body body : BodylnsertUser?) : Observable<ResponseUser>

    @POST("/uploadimageUser")
    fun douploadimageUser(@Body body : BodyUploadImage) : Observable<ResponseUploadImage>

    @POST("/login")
    fun dologin(@Body body : BodyLogin?) : Observable<List<ResponseLogin>>

    @GET("/imageUser/{id}")
    fun doSelectImageUser(@Path("id") id: Int) : Observable<List<ResponseImageUser>>

    @GET("/user/{id}")
    fun dogetuser(@Path("id")id: Int):Observable<List<ResponseUser>>
    @POST("/getcheckStatusJoinStadium")
    fun dogetcheckStatus(@Body body : BodyStatusJoinStadium?):Observable<List<ResponseJoinStadium>>

    @PUT("/updateuser/{id}")
    fun doupdateuser(@Path("id")id: Int,@Body body :BodylnsertUser?) : Observable<ResponseUser>

    @PUT("/UpdateimageUser/{id}")
    fun doUpdateimageUser(@Path("id") id :Int, @Body body :BodyUploadImage?) :Observable<ResponseUploadImage>

    //activity
    @POST("/activity")
    fun doCreate(@Body body : BodyInsertActivity?) : Observable<ResponseActivity>

    @POST("/Chat")
    fun Chat(@Body body : BodyChat?) : Observable<ResponseChat>

    @GET("/getMessageChat/{id}")
    fun getMessageChat(@Path("id")id: Int?):Observable<List<ResponseGetChat>>

    @GET("/activity")
    fun doshowactivity():Observable<List<ResponseActivity>>

    @GET("/selectActivity/{id}")
    fun selectActivity(@Path("id")id: Int?):Observable<List<ResponseActivity>>

    @GET("/getDataActivity/{id}")
    fun getDataActivity(@Path("id")id: Int?):Observable<List<ResponseActivity>>

    @PUT("/updateactivity/{id}")
    fun doUpdatenumberjoin(@Path("id")id:Int,@Body body : BodyInsertActivity?) :Observable<ResponseUpdateAC>

    @PUT("/updatelogoutactivity/{id}")
    fun doUpdatelogoutactivity(@Path("id")id:Int,@Body body :BodyUpDate?) :Observable<ResponseUpdateAC>

    @PUT("/updateDataActivity/{id}")
    fun doUpdateDataActivity(@Path("id")id:Int,@Body body : BodyInsertActivity?) :Observable<ResponseUpdateAC>

    @DELETE("deleteactivity/{id}")
    fun  dodeleteactivity(@Path("id")id: Int) :Observable<ResponseActivity>

    @POST("/activityuser")
    fun doActivityUser(@Body body : BodyActivityUser?) :Observable<List<ResponseActivity>>

    //join
    @POST("/jn")
    fun dojoin(@Body body : BodyJoin?):Observable<ResponseJoin>

    @POST("/Check")
    fun doCheck(@Body body :BodyCheck?) :Observable<List<ResponseCheck>>

    @POST("/deleteJoin")
    fun  dodeleteJoin(@Body body : BodyDeleteJoinActivity?) :Observable<ResponseJoin>

    @GET("/showJoinActivity/{id}")
    fun showJoinActivity(@Path("id")id: Int?):Observable<List<ResponseJoinActivityItem>>



    //Post
    @POST("/Post")
    fun doPost(@Body body : BodyPost?):Observable<ResponsePost>

    @GET("/Post")
    fun doShowPost():Observable<List<ResponsePost>>

    @POST("/getPostProfile")
    fun dogetPostProfile(@Body body :BodygetPostProfile?):Observable<List<ResponsePost>>

    @POST("/Comment")
    fun doComment(@Body body : BodyComment?):Observable<ResponseComment>

    @PUT("/updateDataCommentImage/{id}")
    fun updateDataCommentImage(@Path("id")id: Int,@Body body:BodyCommentImage?):Observable<ResponseComment>

    @DELETE("deletePost/{id}")
    fun  deletePost(@Path("id")id: Int) :Observable<ResponsePost>

    @PUT("/updateDataPost/{id}")
    fun updateDataPost(@Path("id")id: Int, @Body body :BodyPost?) :Observable<ResponsePost>

    @PUT("/updateDataPostImage/{id}")
    fun updateDataPostImage(@Path("id")id: Int, @Body body : BodyImagePost?) :Observable<ResponsePost>

    @POST("/getComment")
    fun dogetComment(@Body body :BodyGetComment?):Observable<List<ResponseComment>>

    //Operator
    @POST("/loginOPT")
    fun dologinOPT(@Body body :BodyLoinOPT?):Observable<List<ResponseLoginOPT>>

    @POST("/operator")
    fun doRegisterOPT(@Body body :BodyRegisterOperator?) :Observable<ResponseOperator>

    @PUT("/updateOPT/{id}")
    fun doUpdateOPT(@Path("id")id: Int, @Body body :BodyRegisterOperator?) :Observable<ResponseUpdateOPT>

    @POST("/uploadimageOPT")
    fun doUploadimageOPT(@Body body :BodyUploadImageOPT?) :Observable<ResponseUploadImage>

    @POST("/UpdateimageOPT/{id}")
    fun doUpdateimageOPT(@Path("id") id :Int,@Body body :BodyUploadImageOPT?) :Observable<ResponseUploadImage>

    @GET("/imageOPT/{id}")
    fun doSelectImageOPT(@Path("id") id: Int) : Observable<List<ResponseImageOPT>>

    @GET("/operator/{id}")
    fun doOperator(@Path("id") id: Int) : Observable<List<ResponseOPTProfile>>

    //Stadiam
    @GET("/ShowStadium")
    fun doShowStadium():Observable<List<ResponseShowStadium>>

    @GET("/showimageStadium/{id}")
    fun doShowimageStadium(@Path("id")id: Int):Observable<List<ResponterImageStadium>>

    @GET("/ManageStadium/{id}")
    fun doManageStadium(@Path("id")id:Int):Observable<List<ResponseShowStadium>>

    @POST("/Stadium")
    fun doPostStadium(@Body body:BodyStadiam?):Observable<ResponseStadiam>

    @PUT("/UpdateDataStadium/{id}")
    fun UpdateDataStadium (@Path("id")id:Int,@Body body:BodyStadiam?):Observable<ResponseUpdateStadium>

    @POST("/uploadimageStadium")
    fun doUploadimageStadium(@Body body :BodyImageStadium?) :Observable<ResponseUploadImage>

    @POST("/JoinStadium")
    fun doJoinStadium(@Body body:BodyJoinStadiam?):Observable<ResponseJoinStadium>

    @POST("/getcheckStadiumOPT")
    fun dogetcheckStadiumOPT(@Body body :BodyGetCheckStadiumOPT?):Observable<List<ResponseJoinStadium>>

    @PUT("/updateStatusJoinStadium/{id}")
    fun doupdateStatusJoinStadium(@Path("id")id: Int,@Body body :BodyJoinStadiam?) : Observable<ResponseJoinStadium>

    @POST("/getcheckTimeStadium")
    fun dogetcheckTimeStadium(@Body body : BodyGetCheckTimeStadium?):Observable<List<ResponseJoinStadium>>

    @DELETE("/deleteJoinStadium/{id}")
    fun daDeleteJoinStadium(@Path("id")id: Int):Observable<ResponseJoinStadium>

    @DELETE("/deleteStadium/{id}")
    fun doDeleteStadium(@Path("id")id: Int):Observable<ResponseStadiam>

    @DELETE("/deleteJoinStadium_sID/{id}")
    fun doDeleteJoinStadium_sID(@Path("id")id: Int):Observable<ResponseJoinStadium>

    //notification
    @POST("/notification")
    fun Postnotification(@Body body: BodyNotification?):Observable<ResponseListNotification>

    @GET("/Checknotification/{id}")
    fun Checknotification(@Path("id")id: Int):Observable<List<ResponseNotification>>

    @DELETE("/deletenotification/{id}")
    fun deletenotification(@Path("id")id: Int):Observable<ResponseNotification>


    //ดึงชื่อของผู้ใช้ออกมาแสดง
    @GET("/operator/{id}")
    fun dogetnameOPT(@Path("id")id: Int):Observable<ResponseNameOPT>


}