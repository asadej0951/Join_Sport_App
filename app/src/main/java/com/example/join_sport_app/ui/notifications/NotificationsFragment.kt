package com.example.join_sport_app.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterStadiam
import com.example.join_sport_app.adapterall.AdapterStadiamUser
import com.example.join_sport_app.model.ResponseShowStadium
import com.example.join_sport_app.model.ResponseStadiam
import com.example.join_sport_app.presenter.PresenterOperator
import com.example.join_sport_app.rest.Utils
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.android.synthetic.main.fragment_dashboard__operator.*
import kotlinx.android.synthetic.main.fragment_notifications.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    lateinit var mPreferrences: Preferrences
    var mPresenterStadiam = PresenterOperator()
    var mResponseStadiam = ArrayList<ResponseShowStadium>()
    lateinit var mAdapterStadiamUser : AdapterStadiamUser
    private var Image = ""

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        initview(root)
        setAPIShowStadiamUser()
        return root.rootView
    }

    private fun setAPIShowStadiamUser() {
        mPresenterStadiam.ShowStadiamPresenterRX(this::ShowStadiamNext,this::ShowStadiamError)
    }

    private fun ShowStadiamError(message: String) {

    }

    private fun ShowStadiamNext(responseStadiam: List<ResponseShowStadium>) {
        for (i in responseStadiam.indices){
            mResponseStadiam.add(responseStadiam[i])
        }
        mAdapterStadiamUser= AdapterStadiamUser(context!!,mResponseStadiam)
        recyclerView_StadiamUser.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterStadiamUser
            mAdapterStadiamUser.notifyDataSetChanged()
        }
    }

    private fun initview(root: View) {
        mPreferrences = Preferrences(context!!)
        root.ID_user_Stadiam.setText("ID : "+mPreferrences.getusername())
        root.name_userStadiam.setText("คุณ : "+mPreferrences.getName_lname())
        Image = mPreferrences.getImage()
        Picasso.get().load(Utils.host+"/imageregister/"+Image).into(root.img_user_Stadiam)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        setAPIShowStadiamUser()
        super.onActivityResult(requestCode, resultCode, data)
    }
}