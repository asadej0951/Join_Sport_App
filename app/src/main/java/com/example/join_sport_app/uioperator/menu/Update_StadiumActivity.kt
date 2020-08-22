package com.example.join_sport_app.uioperator.menu

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.ImageViewPagerAdapter
import com.example.join_sport_app.adapterall.ImageViewPagerStadiumAdapter
import com.example.join_sport_app.body.operator.BodyImageStadium
import com.example.join_sport_app.model.ResponseUpdateStadium
import com.example.join_sport_app.model.ResponterImageStadium
import com.example.join_sport_app.presenter.PresenterOperator
import com.example.join_sport_app.uioperator.dashboard.Stadiam_Activity
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
import com.tangxiaolv.telegramgallery.GalleryActivity
import com.tangxiaolv.telegramgallery.GalleryConfig
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.activity_stadiam_.*
import kotlinx.android.synthetic.main.activity_update__stadium.*
import java.util.*
import kotlin.collections.ArrayList

class Update_StadiumActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap

    private val REQ_CODE = 999
    private var timeOpen =""
    private var timeClose =""
    var IDS :Int? =null
    var mPreferrences = Preferrences(this)

    var IDImage =ArrayList<String>()

    private lateinit var lastLocation: Location
    var mPresenterOPT = PresenterOperator()
    private var imageViewPagerAdapter: ImageViewPagerAdapter? = null
    private var imageViewPagerStadiumAdapter: ImageViewPagerStadiumAdapter? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODES = 1
    }

    private var lat :Double? = null
    private var long :Double?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update__stadium)
        initButton()
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_OPTUpdate) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        initShow()
    }

    private fun initButton() {
        btn_backto_MenageStadiam.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,Manage_StadiumActivity::class.java
                )
            )
            finish()
        }
        btn_Time_UpdateStadiam.setOnClickListener {
            showDialogTimeStadiamOpen()
        }
        btn_Map_StadiumUpdate.setOnClickListener {
            var i =Intent(applicationContext,MapUpdateStadiumActivity::class.java)
            i.putExtra("lat",lat!!)
            i.putExtra("long",long!!)
                startActivityForResult(i
                ,REQ_CODE
            )
        }
        btn_Save_StadiamUpdate.setOnClickListener {
            setAPIUpdateData()
        }
    }

    private fun setAPIUpdateData() {
        mPresenterOPT.UpdateDataStadiumPresenterRX(
            IDS!!,
            Stadiam_nameUpdate.text.toString(),
            lat!!.toString(),long!!.toString(),
            address_StadiamUpdate.text.toString(),
            Type_StadiamUpdate.text.toString(),
            price_StadiamUpdate.text.toString().toInt(),
            TimeOpen_StadiamUpdate.text.toString(),
            TimeClose_StadiamUpdate.text.toString(),
            mPreferrences.getID(),mPreferrences.getName_lname(),
            this::UpdateDataNext,this::UpdateDateError
        )
    }

    private fun UpdateDataNext(response:ResponseUpdateStadium) {
        Toast.makeText(applicationContext, "แก้ไขข้อมูลเสร็จสิ้น", Toast.LENGTH_SHORT).show()
        startActivity(
            Intent(applicationContext,Manage_StadiumActivity::class.java)
        )
        finish()
    }

    private fun UpdateDateError(message: String) {
        Toast.makeText(applicationContext, "ผิดพลาด", Toast.LENGTH_SHORT).show()
    }

    private fun initShow() {
        var s_name = intent.getStringExtra("s_name")
        var s_lat = intent.getStringExtra("s_lat")
        var s_long = intent.getStringExtra("s_long")
        var s_address = intent.getStringExtra("s_address")
        var s_type = intent.getStringExtra("s_type")
        var s_price = intent.getIntExtra("s_price",0)
        var s_timeopen = intent.getStringExtra("s_timeopen")
        var s_timeclose = intent.getStringExtra("s_timeclose")
        var s_id = intent.getIntExtra("s_id",0)
        IDS = s_id
        lat = s_lat.toDouble()
        long = s_long.toDouble()
        Stadiam_nameUpdate.setText(s_name)
        address_StadiamUpdate.setText(s_address)
        Type_StadiamUpdate.setText(s_type)
        price_StadiamUpdate.setText(s_price.toString())
        TimeOpen_StadiamUpdate.setText(s_timeopen.substring(0,5))
        TimeClose_StadiamUpdate.setText(s_timeclose.substring(0,5))

        setAPIShowImage(s_id)
    }

    private fun setAPIShowImage(sId: Int) {
        mPresenterOPT.ShowimageStadiumPresenterRX(sId,this::showNext,this::ShowError)
    }
    private fun showNext(response:List<ResponterImageStadium>) {
        val phone = ArrayList<String>()
        for (i in response.indices){
            phone.add(response[i].s_img)
            IDImage.add(response[i].imgS_id.toString())
        }
        Log.d("Show",phone.toString())
        val ViewPager = findViewById<ViewPager>(R.id.Vp_ImageStadiumUpdate)
        imageViewPagerStadiumAdapter = ImageViewPagerStadiumAdapter(this@Update_StadiumActivity,phone)
        ViewPager.adapter = imageViewPagerStadiumAdapter

    }

    private fun ShowError(message: String) {
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
            }
        }
        val currentLatLng = LatLng(lat!!,long!!)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,15f))
        map.addMarker(MarkerOptions().position(currentLatLng).title("สนามกีฬา"))
    }

    override fun onMarkerClick(p0: Marker?): Boolean = false

    private fun showDialogTimeStadiamOpen() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        val listener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            var H :String
            var M :String
            if (hourOfDay.toString().length ==1){
                H = "0${hourOfDay}"
            }else{H = "${hourOfDay}"}
            if (minute.toString().length==1){M = "0${minute}"}
            else{M = "${minute}"}
            val dateStr = "$H:$M"
            TimeOpen_StadiamUpdate.setText(dateStr+" น.")
            timeOpen ="$hourOfDay:$minute"
            showDialogTimeStadiamClose()

        }
        val dialog = TimePickerDialog(this@Update_StadiumActivity,
            AlertDialog.THEME_HOLO_DARK,listener,hour,min,false)
        dialog.show()
    }
    private fun showDialogTimeStadiamClose() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        val listener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            var H :String
            var M :String
            if (hourOfDay.toString().length ==1){
                H = "0${hourOfDay}"
            }else{H = "${hourOfDay}"}
            if (minute.toString().length==1){M = "0${minute}"}
            else{M = "${minute}"}
            val dateStr = "$H:$M"
            TimeClose_StadiamUpdate.setText(dateStr+" น.")
            timeClose = dateStr
        }
        val dialog = TimePickerDialog(this@Update_StadiumActivity,
            AlertDialog.THEME_HOLO_DARK,listener,hour,min,false)
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (REQ_CODE==requestCode&&resultCode== RESULT_OK){

            map.clear()
            lat = data!!.getDoubleExtra("lat",0.0)
            long = data!!.getDoubleExtra("long",0.0)
            val currentLatLng = LatLng(lat!!,long!!)
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,15f))
            map.addMarker(MarkerOptions().position(currentLatLng).title("สนามกีฬา"))
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}