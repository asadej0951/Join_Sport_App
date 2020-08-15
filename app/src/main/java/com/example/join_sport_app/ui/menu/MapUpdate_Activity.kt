package com.example.join_sport_app.ui.menu

import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.join_sport_app.R
import com.example.join_sport_app.ui.dashboard.Map_Detail_Activity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map__detail_.*
import kotlinx.android.synthetic.main.activity_map_update_.*

class MapUpdate_Activity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat:Double?=null
    private var long:Double?=null
    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_update_)
        var latMap = intent.getDoubleExtra("lat",0.0)
        var longMap = intent.getDoubleExtra("long",0.0)
        lat = latMap
        long = longMap
        btn_back_toUpdateActivity.setOnClickListener {
            lat
            long
            intent.putExtra("lat", lat!!.toString())
            intent.putExtra("long",long!!.toString())
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_UpdateActivityUser) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
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
                LOCATION_PERMISSION_REQUEST_CODE
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
        var location = LatLng(lat!!,long!!)
        map.addMarker(MarkerOptions().position(location).title(""))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15f))
        map.setOnMapClickListener(object :GoogleMap.OnMapClickListener{
            override fun onMapClick(p0: LatLng) {
                map.clear()
                map.addMarker(
                    MarkerOptions().position(p0).title(
                        p0.latitude.toString()+","+p0.longitude.toString()
                    ))
                object :GoogleMap.OnMarkerClickListener{
                    override fun onMarkerClick(place: Marker?): Boolean {
                        place!!.remove()
                        return true
                    }

                }
                lat = p0.latitude
                long = p0.longitude
            }
        })
        map.setOnMarkerClickListener(object :GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(p0: Marker?): Boolean {
                intent.putExtra("lat", lat!!.toString())
                intent.putExtra("long",long!!.toString())
                setResult(Activity.RESULT_OK,intent)
                finish()
                return true
            }

        })
    }

    override fun onMarkerClick(p0: Marker?): Boolean = false
}