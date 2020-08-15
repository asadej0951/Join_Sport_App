package com.example.join_sport_app.ui.menu

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.GnssNavigationMessage
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import com.example.join_sport_app.R
import com.example.join_sport_app.ui.dashboard.Create_Activity
import com.example.myapplicationproject.model.ResponseUpdateAC
import com.example.myapplicationproject.presenter.PresenterActivity
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
import kotlinx.android.synthetic.main.activity_create_.*
import kotlinx.android.synthetic.main.activity_up_date_user_.*
import java.util.*

class UpDateActivityUser_Activity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    var ID :Int? = null
    var Name = ""
    var Type :Int? =null
    var Number : Int? = null
    var DAY =""
    var TIME = ""
    var lat :Double? = null
    var long :Double? = null
    var location :LatLng? = null
    var NumberJoin : Int? = null
    var UserName = ""
    var UserID =""

    var mActivityPresenter = PresenterActivity()
    var mPreferrences = Preferrences(this)
    private lateinit var map: GoogleMap
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var toDoDate:Triple<Int?,Int?,Int?>
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODES = 1
        const val REQUEST_CODE = 999
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_up_date_user_)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_UpdateActivity) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        initivewShow()
        initviewButton()
    }

    private fun initivewShow() {
        val type = resources.getStringArray(R.array.type)

        var ac_id = intent.getIntExtra("ac_id",0)
        var ac_number = intent.getIntExtra("ac_number",0)
        var ac_name = intent.getStringExtra("ac_name")
        var ac_type = intent.getStringExtra("ac_type")
        var ac_time = intent.getStringExtra("ac_time")
        var ac_long = intent.getStringExtra("ac_long")
        var ac_lat = intent.getStringExtra("ac_lat")
        var user_name = intent.getStringExtra("user_name")
        var user_id = intent.getStringExtra("user_id")
        var ac_numberjoin = intent.getIntExtra("ac_numberjoin",0)
        UserName = user_name
        UserID = user_id
        NumberJoin = ac_numberjoin
        ID = ac_id
        Name = ac_name
        Type = ac_type.toInt()
        Number = ac_number
        DAY = ac_time.substring(0,10)
        TIME = ac_time.substring(11)
        lat = ac_lat.toDouble()
        long = ac_long.toDouble()

        name_AC_update.setText(Name)
        if (spinner_type_update != null) {
            val adaptertype = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, type)
            spinner_type_update.adapter = adaptertype
            spinner_type_update.setSelection(Type!!)
        }
        number_Ac_update.setText(Number.toString())
        Day_update_Activity.setText(DAY)
        Time_Activity_update.setText(TIME)
    }

    private fun initviewButton() {
        btn_back_toActivityUser.setOnClickListener {
            startActivity(
                Intent(applicationContext,ActivityUser_Activity::class.java)
            )
            finish()
        }
        btn_Day_update.setOnClickListener { showDate() }
        btn_TimeUpdateActivity.setOnClickListener { showDialogTime() }

        btn_MapUpdateActivity.setOnClickListener {
            val i = Intent(applicationContext,MapUpdate_Activity::class.java)
            i.putExtra("lat",lat)
            i.putExtra("long",long)
            startActivityForResult(
                i,REQUEST_CODE
            )
        }
        btn_UpdateActivityUser.setOnClickListener {
            setAPIUpdateDataActivity()
        }
    }

    private fun setAPIUpdateDataActivity() {
        Name = name_AC_update.text.toString()
        Number = number_Ac_update.text.toString().toInt()
        mActivityPresenter.UpdateDataActivityPresenterRX(ID!!,UserID,Name,spinner_type_update.selectedItemId.toString(),
            Day_update_Activity.text.toString()+"-"+Time_Activity_update.text.toString(),Number!!,NumberJoin!!,lat.toString(),
            long.toString(),mPreferrences.getImage(),UserName,this::UpActivityNext,this::UpActivityError
        )
    }

    private fun UpActivityNext(response : ResponseUpdateAC) {
        startActivity(
            Intent(applicationContext,ActivityUser_Activity::class.java)
        )
        finish()
    }

    private fun UpActivityError(message: String) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==REQUEST_CODE&&resultCode==Activity.RESULT_OK){
            map.clear()
            val latdata = data!!.getStringExtra("lat")
            val longdata = data!!.getStringExtra("long")
            lat = latdata.toDouble()
            long = longdata.toDouble()
            location = LatLng(lat!!,long!!)
            map.addMarker(MarkerOptions().position(location!!))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15f))
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
        setUpMap()
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODES
            )
            return
        }
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this){location ->
            if (location != null){
                lastLocation = location
                val currentLatLng = LatLng(location.latitude,location.longitude)
            }
        }
        location = LatLng(lat!!,long!!)
        map.addMarker(MarkerOptions().position(location!!))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location,12f))
    }

    override fun onMarkerClick(p0: Marker?): Boolean = false

    private fun showDialogTime() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        val listener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            var HOUR =""
            var MIN =""
            if (hourOfDay.toString().length==1){HOUR = "0${hourOfDay}"}
            else{HOUR = "${hourOfDay}"}
            if (minute.toString().length ==1){MIN = "0${minute}"}
            else{MIN = "${minute}" }
            val dateStr = "$HOUR:$MIN"

            Time_Activity_update.setText(dateStr)
        }
        val dialog = TimePickerDialog(this@UpDateActivityUser_Activity,listener,hour,min,true)
        dialog.show()
    }
    private fun showDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        toDoDate = Triple(null,null,null)
        val listener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            var DAY =""
            var MONTH =""
            toDoDate = Triple(dayOfMonth,month+1,year)

            if (toDoDate.first.toString().length==1) { DAY = "0${toDoDate.first}" }
            else{ DAY = "${toDoDate.first}" }
            if (toDoDate.second.toString().length==1){MONTH = "0${toDoDate.second}"}
            else{MONTH = "${toDoDate.second}"}

            Day_update_Activity.setText("${DAY}/${MONTH}/${toDoDate.third}")
        }
        val dialog = DatePickerDialog(this,listener,year,month,day)
        dialog.show()
    }

}