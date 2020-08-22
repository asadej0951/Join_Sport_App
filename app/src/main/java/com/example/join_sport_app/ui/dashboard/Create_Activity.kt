package com.example.join_sport_app.ui.dashboard

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import androidx.core.app.ActivityCompat
import com.example.join_sport_app.R
import com.example.join_sport_app.uioperator.dashboard.Stadiam_Activity
import com.example.myapplicationproject.model.ResponseActivity
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
import java.util.*

class Create_Activity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

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
    private var lat :Double?=null
    private var long:Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_Activity) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val type = resources.getStringArray(R.array.type)

        btn_back_Activity.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        btn_Day.setOnClickListener {
            showDate()
        }
        btn_TimeActivity.setOnClickListener {
            showDialogTime()
        }

        if (spinner_type != null) {
            val adaptertype = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, type)
            spinner_type.adapter = adaptertype
            //spinner_type.setSelection(1)
        }

        btn_UpdateActivity.setOnClickListener {
            setAPI()

        }
        btn_MapActivity.setOnClickListener {
            val i =Intent(applicationContext,MapsActivity::class.java)
            startActivityForResult(i, REQUEST_CODE)
        }
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

            Day_Activity.setText("${DAY}/${MONTH}/${toDoDate.third}")
        }
        val dialog = DatePickerDialog(this,listener,year,month,day)
        dialog.show()
    }

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

            Time_Activity.setText(dateStr)
        }
        val dialog = TimePickerDialog(this@Create_Activity,listener,hour,min,true)
        dialog.show()
    }


    private fun setAPI() {

        mActivityPresenter.CreatePresenterRX(
            mPreferrences.getID(),
            ed_nameAC.text.toString(),
            spinner_type.selectedItemId.toString(),
            Day_Activity.text.toString()+"-"+Time_Activity.text.toString(),
            ed_number.text.toString().toInt(),
            0,
            lat.toString(),
           long.toString(),
            mPreferrences.getName_lname(),
            this::onSuccessActivity,
            this::onErrorActivity
        )
    }

    private fun onErrorActivity(message: String) {

    }

    private fun onSuccessActivity(response: ResponseActivity) {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode== REQUEST_CODE&&resultCode==Activity.RESULT_OK){
            val latdata = data!!.getStringExtra("lat")
            val longdata = data!!.getStringExtra("long")
            lat = latdata.toDouble()
            long = longdata.toDouble()
            var location = LatLng(lat!!,long!!)
            map.addMarker(MarkerOptions().position(location).title("ที่ทำกิจกรรม"+ed_nameAC.text.toString()))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location,12f))
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
    }

    override fun onMarkerClick(p0: Marker?): Boolean = false

}