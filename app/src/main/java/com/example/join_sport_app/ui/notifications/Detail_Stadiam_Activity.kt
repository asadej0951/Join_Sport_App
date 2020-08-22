package com.example.join_sport_app.ui.notifications

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterShowTime
import com.example.join_sport_app.adapterall.AdapterStadiamUser
import com.example.join_sport_app.adapterall.ImageViewPagerAdapter
import com.example.join_sport_app.adapterall.ImageViewPagerStadiumAdapter
import com.example.join_sport_app.model.ResponseJoinStadium
import com.example.join_sport_app.model.ResponseStadiam
import com.example.join_sport_app.model.ResponterImageStadium
import com.example.join_sport_app.presenter.PresenterOperator
import com.example.myapplicationproject.presenter.PresenterUser
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.viewpagerindicator.CirclePageIndicator
import kotlinx.android.synthetic.main.activity_detail__stadiam_.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import java.util.*

class Detail_Stadiam_Activity : AppCompatActivity() {
    private val CODE_RE = 999
    var DateListJoin = ""
    var mPresenterUser = PresenterUser()
    var mPresenterOPT = PresenterOperator()
    var mResponseJoinStadium = ArrayList<ResponseJoinStadium>()
    private var imageViewPagerAdapter: ImageViewPagerStadiumAdapter? = null
    lateinit var mAdapterShowTime : AdapterShowTime
    private var ID = ""
    //ประกาศตัวแปรเอาไว้เก็บข้อมูล
    private var lat :Double? = null
    private var long:Double? = null
    private var name =""
    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
    private lateinit var toDoDate:Triple<Int?,Int?,Int?>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail__stadiam_)
        var s_name = intent.getStringExtra("s_name")
        var s_lat = intent.getStringExtra("s_lat")
        var s_long = intent.getStringExtra("s_long")
        lat = s_lat.toDouble()
        long = s_long.toDouble()
        name = s_name
        initview_Btn()
        initview_Show()
    }

    private fun initview_Show() {

        var s_name = intent.getStringExtra("s_name")
        var s_img = intent.getStringExtra("s_img")
        var s_address = intent.getStringExtra("s_address")
        var s_type = intent.getStringExtra("s_type")
        var s_price = intent.getIntExtra("s_price",0)
        var s_timeopen = intent.getStringExtra("s_timeopen")
        var s_timeclose = intent.getStringExtra("s_timeclose")
        var s_id = intent.getIntExtra("s_id",0)
        ID = s_id.toString()

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        var Day :String
        var MON = month+1
        var MonTh :String
        var YeaR:String
        if (day.toString().length==1){ Day = "0${day}" } else{Day = "${day}"}
        if (MON.toString().length==1){ MonTh = "0${MON}" } else{MonTh = "${MON}"}
        if (year.toString().length==1){ YeaR = "0${year}" } else{YeaR = "${year}"}
        val Date = "${Day}/${MonTh}/${YeaR}"
        DateListJoin = Date
        if (DateListJoin ==Date){
            time_ListJoinStadiam.setText("เวลาที่ถูกจองแล้ว : วันนี้")
            setAPIShowTime(ID)
        }

        setAPIShowImage()
        nameStadiam_Detail.setText("สนามกีฬา : "+s_name)
        address_Detail.setText("ที่อยู่ : "+s_address)
        Time_DetailStadiam.setText("เปิด "+s_timeopen.substring(0,5)+" น."+" ถึง "+s_timeclose.substring(0,5)+" น.")
        price_StadiamDetail.setText("$"+s_price.toString()+" บาท/ชั่วโมง")
        Type_DetailStadiam.setText("รายละเอียดสนามกีฬา : \n"+s_type)
    }

    private fun setAPIShowImage() {
        Log.d("Show",ID)
        mPresenterOPT.ShowimageStadiumPresenterRX(ID.toInt(),this::showNext,this::ShowError)

    }

    private fun showNext(response:List<ResponterImageStadium>) {
        val phone = ArrayList<String>()
        for (i in response.indices){
            phone.add(response[i].s_img)
        }
        Log.d("Show",phone.toString())
        val ViewPager = findViewById<ViewPager>(R.id.Img_DetailStadiam)
        imageViewPagerAdapter = ImageViewPagerStadiumAdapter(this@Detail_Stadiam_Activity,phone)
        ViewPager.adapter = imageViewPagerAdapter

        val indicator = findViewById<CirclePageIndicator>(R.id.indicator).also {
            it.setViewPager(ViewPager)
        }
        val density = resources.displayMetrics.density
        indicator.radius = 5*density

    }

    private fun ShowError(message: String) {
    }

    private fun setAPIShowTime(sId: String) {
        mPresenterUser.dogetcheckTimeStadiumPresenterRX(sId,DateListJoin,"3อนุมัติ",this::ShowTimeNext,this::ShowTimeError)
    }

    private fun ShowTimeNext(response : List<ResponseJoinStadium>) {
        for (i in response.indices){
            mResponseJoinStadium.add(response[i])
        }
        mAdapterShowTime= AdapterShowTime(this,mResponseJoinStadium)
        Recycler_JoinStadiam.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter = mAdapterShowTime
        }
        mAdapterShowTime.notifyDataSetChanged()
    }

    private fun ShowTimeError( message :String) {

    }
    private fun initview_Btn() {
        btn_back_Detail.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
        btn_JoinStadiam.setOnClickListener {
            var s_id = intent.getIntExtra("s_id",0)
            var o_id = intent.getIntExtra("o_id",0)
            var s_name = intent.getStringExtra("s_name")
            var s_lat = intent.getStringExtra("s_lat")
            var s_long = intent.getStringExtra("s_long")
            var s_price = intent.getIntExtra("s_price",0)
            var i =Intent(applicationContext,JoinStadiam_Activity::class.java)
            i.putExtra("s_id",s_id)
            i.putExtra("o_id",o_id)
            i.putExtra("s_name",s_name)
            i.putExtra("s_lat",s_lat)
            i.putExtra("s_long",s_long)
            i.putExtra("s_price",s_price)
            startActivityForResult(i,CODE_RE)
        }
        btn_DateListJoinStadiam.setOnClickListener {
            showDialogDate()
        }
        btn_map_DetailJoin.setOnClickListener {
            var s_name = intent.getStringExtra("s_name")
            var s_lat = intent.getStringExtra("s_lat")
            var s_long = intent.getStringExtra("s_long")
            val i = Intent(applicationContext,Map_DetailActivity::class.java)
            i.putExtra("s_name",s_name)
            i.putExtra("s_lat",s_lat)
            i.putExtra("s_long",s_long)
            startActivity(i)
        }
    }

    private fun showDialogDate() {
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

            DateListJoin = "${Day}/${MonTh}/${YeaR}"

            mAdapterShowTime= AdapterShowTime(this,mResponseJoinStadium)
                mResponseJoinStadium.clear()
                mAdapterShowTime.notifyDataSetChanged()

            time_ListJoinStadiam.setText("เวลาที่ถูกจองแล้ว : "+DateListJoin)
            setAPIShowTime(ID)
        }
        val dialog = DatePickerDialog(this,AlertDialog.THEME_HOLO_DARK,listener,year,month,day)
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==CODE_RE&&resultCode==Activity.RESULT_OK){
            val message = data!!.getStringExtra("message")
            findViewById<TextView>(R.id.Status_Text).apply {
                setText("สถานะการจอง : ")
            }
            findViewById<TextView>(R.id.Staus_Show).apply {
                setText(message)
                setTextColor(Color.parseColor("#00CCFF"))
            }
            findViewById<Button>(R.id.btn_JoinStadiam).apply {
                setBackgroundColor(Color.parseColor("#DCDCDC"))
                isClickable = false
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}