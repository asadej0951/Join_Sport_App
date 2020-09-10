package com.example.join_sport_app.ui.home

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.join_sport_app.R
import com.example.join_sport_app.model.ResponseNotification
import com.example.join_sport_app.presenter.PresenterNotification
import com.example.join_sport_app.ui.menu.Check_Status_JoinStadiumActivity
import com.example.myapplicationproject.rest.local.Preferrences

class notification_Activity : AppCompatActivity() {

    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var Builer: Notification.Builder
    private val ChannelID = "com.example.join_sport_app.ui.home"
    private val description = "notification Join Sport"

    lateinit var mPreferrences: Preferrences
    var mPresenterNotification = PresenterNotification()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        setAPINotification()
    }
    private fun setAPINotification() {
        mPreferrences = Preferrences(this)
        mPresenterNotification.ChecknotificationPresenterRX(mPreferrences.getID().toInt(),this::CheckNext,this::CheckError)
    }

    private fun CheckNext(response:List<ResponseNotification>) {
        for (i in response.indices){
            if (response[i].subject =="สนามกีฬา"){
                Log.d("Notification",response[i].subject)
                showNotificationStadium(response[i].message,response[i].nft_id)
            }
            else{
            showNotificationMessage(response[i].message,response[i].nft_id)
            }
        }
    }

    private fun CheckError(message: String) {
        finish()
    }
    private fun showNotificationStadium(message: String, nftId: Int){
        val intent = Intent(this, Check_Status_JoinStadiumActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(ChannelID,description,NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            Builer = Notification.Builder(this,ChannelID)
                .setContentTitle("Join Sport")
                .setContentText("การจองสนามกีฬาของคุณ "+message.substring(1))
                .setSmallIcon(R.drawable.join)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.stadium))
                .setContentIntent(pendingIntent)
        }
        else{
            Builer = Notification.Builder(this)
                .setContentTitle("Join Sport")
                .setContentText("การจองสนามกีฬาของคุณ "+message.substring(1))
                .setSmallIcon(R.drawable.join)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.stadium))
                .setContentIntent(pendingIntent)

        }
        notificationManager.notify(1000,Builer.build())
        setAPINotificationDelete(nftId)
    }
    private fun showNotificationMessage(message: String, nftId: Int){
        val intent = Intent(this, Check_Status_JoinStadiumActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(ChannelID,description,NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            Builer = Notification.Builder(this,ChannelID)
                .setContentTitle("Join Sport")
                .setContentText("ข้อความกิจกรรม : "+message)
                .setSmallIcon(R.drawable.join)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.stadium))
                .setContentIntent(pendingIntent)

        }
        else{
            Builer = Notification.Builder(this)
                .setContentTitle("Join Sport")
                .setContentText("ข้อความกิจกรรม : "+message)
                .setSmallIcon(R.drawable.join)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.stadium))
                .setContentIntent(pendingIntent)

        }
        notificationManager.notify(1000,Builer.build())
        setAPINotificationDelete(nftId)

    }

    private fun setAPINotificationDelete(nftId: Int) {
        mPresenterNotification.deletenotificationPresenterRX(nftId,this::deleteNext,this::deleteError)
    }

    private fun deleteNext(response:ResponseNotification) {
        finish()
    }

    private fun deleteError(message: String) {
    }
}