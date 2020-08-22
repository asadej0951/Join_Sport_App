package com.example.join_sport_app.uioperator.menu

import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.join_sport_app.R
import com.example.join_sport_app.uioperator.dashboard.MapsOperatorActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_map_update_stadium.*
import kotlinx.android.synthetic.main.activity_maps_operator.*
import kotlinx.android.synthetic.main.activity_maps_operator.btn_back_toStadiam

class MapUpdateStadiumActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMarkerClickListener  {

    private lateinit var map: GoogleMap
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat :Double? = null
    private var long :Double? = null

    companion object{
        var YOUR_API_KEY = "AIzaSyC3uyMbAhsKkosqOm-P7IIyySYGt23kqV4"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_update_stadium)
        lat = intent.getDoubleExtra("lat",0.0)
        long = intent.getDoubleExtra("long",0.0)
        btn_back_UpdateStadiam.setOnClickListener {
            intent.putExtra("lat",lat!!)
            intent.putExtra("long",long!!)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_OPT_Update) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        //เพิ่มเครื่องหมาย zoom
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        val currentLatLng = LatLng(lat!!,long!!)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,15f))
        map.addMarker(MarkerOptions().position(currentLatLng).title("สนามกีฬา"))

        setUpMap()
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        //เลือกตำแหน่งปัจุบัน
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this){location ->
            if (location != null){
                lastLocation = location
            }
        }


        map.setOnMapClickListener(object :GoogleMap.OnMapClickListener{
            override fun onMapClick(p0: LatLng) {
                //กดเลือกปักหมุด
                map.clear()
                map.addMarker(
                    MarkerOptions().position(p0).title(
                    p0.latitude.toString()+","+p0.longitude.toString()
                ))
                lat = p0.latitude
                long = p0.longitude
                object :GoogleMap.OnMarkerClickListener{
                    override fun onMarkerClick(p0: Marker?): Boolean {
                        p0!!.remove()
                        return true
                    }

                }
            }

        })
        map.setOnMarkerClickListener(object :GoogleMap.OnMarkerClickListener{
            override fun onMarkerClick(p0: Marker?): Boolean {
                intent.putExtra("lat",lat!!)
                intent.putExtra("long",long!!)
                setResult(Activity.RESULT_OK,intent)
                finish()
                return true
            }
        })
    }

    override fun onMarkerClick(p0: Marker?): Boolean = false
}