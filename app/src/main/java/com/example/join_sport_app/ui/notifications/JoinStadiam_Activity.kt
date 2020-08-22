package com.example.join_sport_app.ui.notifications

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.join_sport_app.R
import com.example.join_sport_app.model.ResponseJoinStadium
import com.example.join_sport_app.uioperator.dashboard.MapsOperatorActivity
import com.example.myapplicationproject.presenter.PresenterUser
import com.example.myapplicationproject.rest.local.Preferrences
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_detail__stadiam_.*
import kotlinx.android.synthetic.main.activity_join_stadiam_.*
import kotlinx.android.synthetic.main.activity_stadiam_.*
import java.util.*

class JoinStadiam_Activity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var map: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat :Double? = null
    private var loan:Double? = null
    private var hh:Int? = null
    private var mm:Int? = null
    private var name =""
    private var timein = ""
    private var timeout = ""
    private var Date = ""
    private var price :Int? = null
    private var T_price :Int? = null
    lateinit var chackbox :CheckBox
    var mPresenterUser = PresenterUser()
    var mPreferrences = Preferrences(this)
    private lateinit var toDoDate:Triple<Int?,Int?,Int?>

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_stadiam_)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_JoinStadiam) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        initview()
    }

    private fun initview() {
        var s_price = intent.getIntExtra("s_price",0)
        price = s_price
        var s_id = intent.getIntExtra("s_id",0)
        var o_id = intent.getIntExtra("o_id",0)
        var s_name = intent.getStringExtra("s_name")
        var s_lat = intent.getStringExtra("s_lat")
        var s_long = intent.getStringExtra("s_long")
        lat = s_lat.toDouble()
        loan = s_long.toDouble()
        name =s_name
        btn_back_JoinStadiam.setOnClickListener {
            finish()
        }
        btn_Save_JoinStadiam.setOnClickListener {
            chackbox = findViewById(R.id.checkBox_JoinStadiam)
            if (chackbox.isChecked) {
                if (nameUser_JoinStadiam.text.toString() != "") {
                    if (Phone_JoinStadiam.text.toString() != "") {
                        if (Day_JoinStadiam.text.toString() !="") {
                            if (Time_JoinStadiam.text.toString() !="") {
                                setAPIJoinStadiam(s_id.toString(), s_name, o_id.toString())
                            }
                            else{
                                Toast.makeText(applicationContext, "กรุณาเลือกช่วงเวลาจอง", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(applicationContext, "กรุณาเลือกวันที่สำหรับการจอง", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(applicationContext, "กรุณาใส่เบอร์โทรศัพท์", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(applicationContext, "กรุณาใส่ชื่อและนามสกุล", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(applicationContext, "กรุณายอมรับข้อตกลง", Toast.LENGTH_SHORT).show()
            }
           }

        btn_TimeJoinStadiam.setOnClickListener {
            TimeIn()
        }
        btn_DayJoinStadiam.setOnClickListener {
            ShowDate()
        }
    }


    private fun setAPIJoinStadiam(s_Id: String, s_Name: String,o_Id: String) {
        mPresenterUser.JoinStadiamPresenterRX(mPreferrences.getID(),s_Id,o_Id,
            nameUser_JoinStadiam.text.toString(),s_Name,timein,timeout,Date,Phone_JoinStadiam.text.toString()
        ,T_price!!,Type_JoinStadiam.text.toString(),"1รออนุมัติ",this::JoinStadiamNext,this::JoinStadiamError)
    }

    private fun JoinStadiamError(message:String) {

    }

    private fun JoinStadiamNext(response:ResponseJoinStadium) {
        var message = "รออนุมัติ"
        intent.putExtra("message",message)
        setResult(Activity.RESULT_OK,intent)
        finish()

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        //เพิ่มเครื่องหมาย zoom
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
        setUpMap()
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        //เลือกตำแหน่งปัจุบัน
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this){location ->
            if (location != null){
                lastLocation = location
                val currentLatLng = LatLng(location.latitude,location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,12f))
            }
        }

        var location = LatLng(lat!!,loan!!)
        map.addMarker(MarkerOptions().position(location).title("สนามกีฬา "+name))
    }

    override fun onMarkerClick(p0: Marker?): Boolean = false
    private fun TimeIn(){
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        val listener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            var HH = ""
            var MM = ""
            if (hourOfDay.toString().length==1){HH ="0${hourOfDay}"}else{HH ="${hourOfDay}"}
            if (minute.toString().length==1){MM ="0${minute}"}else{MM ="${minute}"}
            val p_time = "${HH}:${MM}"
            hh = hourOfDay
            mm = minute
            timein = p_time
            TimeOut()
        }
        val dialog = TimePickerDialog(this@JoinStadiam_Activity, AlertDialog.THEME_HOLO_DARK,listener,hour,min,false)
        dialog.show()
    }

    private fun TimeOut(){
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        var min = calendar.get(Calendar.MINUTE)
        val listener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            var HH = ""
            var MM = ""
            if (hourOfDay.toString().length==1){HH ="0${hourOfDay}"}else{HH ="${hourOfDay}"}
            if (minute.toString().length==1){MM ="0${minute}"}else{MM ="${minute}"}
            val p_time = "${HH}:${MM}"
            timeout = p_time
            var total_time =""
            var PH = price
            var PM = price
            var Time_M :Int? = null
            var Time_H :Int? = null
            if (hourOfDay >= hh!!){Time_H = hourOfDay- hh!!}
            else{Time_H = hh!! - hourOfDay}
            var price_H = PH!!*Time_H
            var price_M = 0

            if(minute>= mm!!){
                Time_M = minute- mm!!
                if (Time_M!! >= 15&& Time_M < 45){
                    price_M = PM!!/2
                }
                else if (Time_M >=45){ price_M = PM!!}
            }
            else{
                Time_M = mm!! - minute
                if (Time_M!! >= 15&& Time_M < 45){
                    price_M = PM!!/2
                }
                else if (Time_M >=45){ price_M = PM!!}
            }

            if (Time_M!=0){
                total_time = Time_H.toString()+" ชั่วโมง "+Time_M.toString()+" นาที"
            }
            else{
                total_time = Time_H.toString()+" ชั่วโมง "
            }
            Totaltime_JoinStadiam.setText("เวลารวม : "+total_time)
            T_price = price_H+price_M
            price_JoinStadiam.setText("ราคารวม : "+T_price)
            Time_JoinStadiam.setText(timein+" - "+timeout)
        }
        val dialog = TimePickerDialog(this@JoinStadiam_Activity,AlertDialog.THEME_HOLO_DARK,listener,hour,min,false)
        dialog.show()
    }
    private fun ShowDate(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        toDoDate = Triple(null,null,null)
        val listener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            toDoDate = Triple(dayOfMonth,month+1,year)
            var Day :String
            var MonTh :String
            var YeaR:String
            if (toDoDate.first.toString().length==1){ Day = "0${toDoDate.first}" } else{Day = "${toDoDate.first}"}
            if (toDoDate.second.toString().length==1){ MonTh = "0${toDoDate.second}" } else{MonTh = "${toDoDate.second}"}
            if (toDoDate.third.toString().length==1){ YeaR = "0${toDoDate.third}" } else{YeaR = "${toDoDate.third}"}

            Date = "${Day}/${MonTh}/${YeaR}"
            Day_JoinStadiam.setText(Date)
        }
        val dialog = DatePickerDialog(this,AlertDialog.THEME_HOLO_DARK,listener,year,month,day)
        dialog.show()
    }
}