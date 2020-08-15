package com.example.join_sport_app.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.viewpager2.widget.ViewPager2
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.PagerAdapter
import com.example.myapplicationproject.presenter.PresenterUser
import com.example.myapplicationproject.rest.local.Preferrences
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RegisterActivity : AppCompatActivity() {

    var mPresenterRegister = PresenterUser()
    var mPreferrences = Preferrences(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initview()
    }
    private fun initview() {
        findViewById<ImageButton>(R.id.imgbtn_registerBack).apply {
            setOnClickListener {
                finish()
            }
        }

        var viewPager = findViewById<ViewPager2>(R.id.ViewPager2)
        viewPager.adapter = PagerAdapter(supportFragmentManager,lifecycle)
        var tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        var data = arrayListOf<String>("ผู้ใช้ทั่วไป","ผู้ประกอบการ")
        TabLayoutMediator(tabLayout,viewPager){
            tab, position -> tab.text = data[position]
        }.attach()
    }


}