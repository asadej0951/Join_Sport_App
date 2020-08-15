package com.example.join_sport_app.ui.notifications

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.join_sport_app.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map__detail.*

class Map_DetailActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private lateinit var map: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    //ประกาศตัวแปรเอาไว้เก็บข้อมูล
    private var lat :Double? = null
    private var long:Double? = null
    private var name =""
    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map__detail)
        btn_back_MapStadium.setOnClickListener {
            finish()
        }
        //รับตัวแปรที่ส่งมา
        var s_name = intent.getStringExtra("s_name")
        var s_lat = intent.getStringExtra("s_lat")
        var s_long = intent.getStringExtra("s_long")
        //เอาตัวแปรที่รับมาไปเก็บไว้ที่ตัวแปรที่ตั้งไว้
        lat = s_lat.toDouble()
        long = s_long.toDouble()
        name = s_name
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_MapDetail) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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
            }
        }

        var location = LatLng(lat!!,long!!)
        map.addMarker(MarkerOptions().position(location).title("สนามกีฬา "+name))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location,12f))

    }

    override fun onMarkerClick(p0: Marker?): Boolean = false
}