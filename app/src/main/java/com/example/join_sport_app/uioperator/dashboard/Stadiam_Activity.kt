package com.example.join_sport_app.uioperator.dashboard

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.ImageViewPagerAdapter
import com.example.join_sport_app.body.operator.BodyImageStadium
import com.example.join_sport_app.model.ResponseStadiam
import com.example.join_sport_app.presenter.PresenterOperator
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
import kotlinx.android.synthetic.main.activity_stadiam_.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class Stadiam_Activity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var map: GoogleMap
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    var PERMISSION_CODE = 1001
    var PICK_IMAGE_MULTIPLE = 1001
    private var imageViewPagerAdapter: ImageViewPagerAdapter? = null

    var uploadImage = ArrayList<BodyImageStadium.Data>()
    var photos: List<String> = ArrayList()
    val REQUEST_CODE = 200

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODES = 1
    }
    private var timeOpen =""
    private var timeClose =""
    private val REQ_CODE = 999
    private var lat :Double? = null
    private var long :Double?= null
    var mPresenterOperator = PresenterOperator()
    lateinit var mPreferrences: Preferrences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stadiam_)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_OPT) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        initview()
    }

    private fun initview() {
        btn_img_Stadium.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)
                } else {
                    //permission already granted
                    pickImageFromGallery()
                }
            } else {
            //system OS is < Marshmallow
            pickImageFromGallery()
            }
        }
        btn_Save_Stadiam.setOnClickListener {
            setAPIPostStadiam()
        }
        btn_Time_Stadiam.setOnClickListener {
            showDialogTimeStadiamOpen()
        }
        btn_back_Stadiam.setOnClickListener {
            finish()
        }
        btn_Map_Stadium.setOnClickListener {
            startActivityForResult(
                Intent(applicationContext,MapsOperatorActivity::class.java),REQ_CODE
            )
        }
    }

    private fun pickImageFromGallery() {
        val config = GalleryConfig.Build()
            .limitPickPhoto(12)
            .singlePhoto(false)
            .hintOfPick("this is pick hint")
            .filterMimeTypes(arrayOf("image/jpeg"))
            .build()
        GalleryActivity.openActivity(this@Stadiam_Activity, PICK_IMAGE_MULTIPLE, config)
    }

    private fun setAPIPostStadiam() {
        mPreferrences = Preferrences(this)
        var o_id = mPreferrences.getID()
        var o_user = mPreferrences.getusername()
        var s_name = Stadiam_name.text.toString()
        var s_lat = lat.toString()
        var s_long = long.toString()
        var s_address = address_Stadiam.text.toString()
        var s_type = Type_Stadiam.text.toString()
        var s_price = price_Stadiam.text.toString().toInt()
        mPresenterOperator.PostStadiamPresenterRX(s_name,s_lat,s_long,s_address,s_type,s_price,timeOpen,timeClose,o_id,o_user,mPreferrences.getImage(),
        this::PostStadiamNext,this::PostStadiamError)
    }

    private fun PostStadiamError(message:String) {
        Toast.makeText(applicationContext, "ไม่สำเร็จ", Toast.LENGTH_SHORT).show()
    }

    private fun PostStadiamNext(response:ResponseStadiam) {
        var s_id = response.s_id
        mPresenterOperator.UploadImageStadiumPresenterRX(s_id,photos) {
            if (it) {
                setResult(Activity.RESULT_OK)
                finish()
            }
            else{
                Toast.makeText(applicationContext, "เกิดข้อผิดพลาดในการ uploadImage", Toast.LENGTH_SHORT).show()
            }
        }
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
                lat = location.latitude
                long = location.longitude
                val currentLatLng = LatLng(location.latitude,location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,15f))
            }
        }
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
            TimeOpen_Stadiam.setText(dateStr+" น.")
            timeOpen ="$hourOfDay:$minute"
            showDialogTimeStadiamClose()

        }
        val dialog = TimePickerDialog(this@Stadiam_Activity,AlertDialog.THEME_HOLO_DARK,listener,hour,min,false)
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
            TimeClose_Stadiam.setText(dateStr+" น.")
            timeClose = dateStr
        }
        val dialog = TimePickerDialog(this@Stadiam_Activity,AlertDialog.THEME_HOLO_DARK,listener,hour,min,false)
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==REQ_CODE&&resultCode== Activity.RESULT_OK){
            map.clear()
            lat = data!!.getDoubleExtra("lat",0.0)
            long = data!!.getDoubleExtra("long",0.0)
            val location = LatLng(lat!!,long!!)
            map.addMarker(MarkerOptions().position(location).title(long.toString()+"/"+long.toString()))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15f))
        }
        try { // When an Image is picked
            var encodedImage: String

            photos = data!!.getSerializableExtra(GalleryActivity.PHOTOS) as List<String>

            // for(i in photos){
            imageViewPagerAdapter =
                ImageViewPagerAdapter(this@Stadiam_Activity, photos as ArrayList<String>)
            //  }
            Vp_ImageStadium.setAdapter(imageViewPagerAdapter)
        } catch (e: Exception) {
            //Log.d("ShowDataNoti2", e.message)
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                .show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}