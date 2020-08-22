package com.example.join_sport_app.ui.menu.activity

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterActivityUser
import com.example.join_sport_app.adapterall.PagerAdapterActivity
import com.example.join_sport_app.rest.Utils
import com.example.myapplicationproject.adapterall.AdapterActivity
import com.example.myapplicationproject.model.ResponseActivity
import com.example.myapplicationproject.presenter.PresenterActivity
import com.example.myapplicationproject.rest.local.Preferrences
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_menu.view.*

class ActivityUser_Activity : AppCompatActivity() {

    var mPreferrences = Preferrences(this)
    var mPresenterActivity = PresenterActivity()
    var mResponseActivity = ArrayList<ResponseActivity>()
    lateinit var mAdapterActivity : AdapterActivityUser
    private var Image =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_)
        btn_back_toMenu_ActivityProfile.setOnClickListener { finish() }
        initview()
//        setAPIActivityUser()
    }

    private fun initview() {
        var ViewPagerActivity = findViewById<ViewPager2>(R.id.ViewPager_Activity)
        ViewPagerActivity.adapter = PagerAdapterActivity(supportFragmentManager,lifecycle)
        var tabLayoutActivity = findViewById<TabLayout>(R.id.tabLayout_Activity)
        var data = arrayListOf<String>("กิจกรรมที่สร้าง","กิจกรรมที่เข้าร่วม")
        TabLayoutMediator(tabLayoutActivity,ViewPagerActivity){
            tab, position ->  tab.text = data[position]
        }.attach()
    }


}