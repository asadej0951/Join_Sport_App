package com.example.join_sport_app.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.join_sport_app.MainActivity
import com.example.join_sport_app.R
import com.example.join_sport_app.uioperator.dashboard.Stadiam_Activity
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.location.places.ui.PlacePicker

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_create_.*
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat:Double?=null
    private var long:Double?=null
    companion object{
        var YOUR_API_KEY = "AIzaSyC3uyMbAhsKkosqOm-P7IIyySYGt23kqV4"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fab_map.setOnClickListener {
            initView()
        }
    }

    private fun initView() {
       Places.initialize(applicationContext, YOUR_API_KEY)
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS))
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {

                val sydney =  LatLng(place.latLng!!.latitude, place.latLng!!.longitude)
                map.addMarker(MarkerOptions().position(sydney).title(place.address))
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17.0f))
                lat = place.latLng!!.latitude
                long = place.latLng!!.longitude
                intent.putExtra("lat",lat.toString())
                intent.putExtra("long",long.toString())
                setResult(Activity.RESULT_OK,intent)
                Log.i("MyActivity", "Place: ${place.name}, ${place.id}, ${place.address}, ${place.latLng}")
            }

            override fun onError(status: Status) {
                Log.i("MyActivity", "An error occurred: $status")
            }
        })
        val fields = listOf(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(this)
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)

                        val sydney =  LatLng(place.latLng!!.latitude, place.latLng!!.longitude)
                        map.addMarker(MarkerOptions().position(sydney).title(place.address))
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17.0f))
                        lat = place.latLng!!.latitude
                        long = place.latLng!!.longitude

                        intent.putExtra("lat",lat.toString())
                        intent.putExtra("long",long.toString())
                        setResult(Activity.RESULT_OK,intent)
                        var location = LatLng(lat!!,long!!)
                        map.addMarker(MarkerOptions().position(location).title(place.address))
                        map.setOnMapClickListener(object :GoogleMap.OnMapClickListener{
                            override fun onMapClick(p0: LatLng) {
                                map.addMarker(
                                    MarkerOptions().position(p0).title(
                                        p0.latitude.toString()+","+p0.longitude.toString()
                                    ))
                                object :GoogleMap.OnMarkerClickListener{
                                    override fun onMarkerClick(place: Marker): Boolean {
                                        place.remove()
                                        return true
                                    }

                                }
                                lat = p0.latitude
                                long = p0.longitude
                            }
                        })
                        map.setOnMarkerClickListener(object :GoogleMap.OnMarkerClickListener{
                            override fun onMarkerClick(p0: Marker?): Boolean {
                                intent.putExtra("lat",lat.toString())
                                intent.putExtra("long",long.toString())
                                setResult(Activity.RESULT_OK,intent)
                                p0!!.remove()
                                finish()
                                return true
                            }

                        })
                        Log.i("MyActivity", "Place: ${place.name}, ${place.id}, ${place.address}, ${place.latLng}")
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.i("MyActivity", status.statusMessage)
                    }
                }
                Activity.RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
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
               LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this){location ->
            if (location != null){
                lastLocation = location
                val currentLatLng = LatLng(location.latitude,location.longitude)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,15f))
            }
        }
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
                intent.putExtra("lat",lat.toString())
                intent.putExtra("long",long.toString())
                setResult(Activity.RESULT_OK,intent)
                p0!!.remove()
                finish()
                return true
            }

        })
    }

    override fun onMarkerClick(p0: Marker?): Boolean = false
}