package com.example.join_sport_app.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.join_sport_app.R
import com.example.join_sport_app.rest.Utils
import com.example.myapplicationproject.adapterall.AdapterActivity
import com.example.myapplicationproject.model.ResponseActivity
import com.example.myapplicationproject.presenter.PresenterActivity
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashboardFragment : Fragment() {
    companion object {
        const val REQUEST_CODE = 999
    }

    private lateinit var dashboardViewModel: DashboardViewModel

    lateinit var mPreferrences: Preferrences
    var mPresenterActivity = PresenterActivity()
    var mResponseActivity = ArrayList<ResponseActivity>()
    lateinit var mAdapterActivity : AdapterActivity
    private var Image =""

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        setAPIActivity()
        initView(root)
        return root.rootView
    }

    private fun setAPIActivity() {
        mPresenterActivity.ShowActivityPresenterRX(this::onShowActivitySuccess,this::onShowActivityError)
    }

    private fun onShowActivityError(message :String) {
    }

    private fun onShowActivitySuccess(responseActivity: List<ResponseActivity>) {
        for (i in responseActivity.indices){
            mResponseActivity.add(responseActivity[i])
        }
        mAdapterActivity = AdapterActivity(context!!,mResponseActivity){
            ac_id,user_id,ac_name,ac_type,ac_time,ac_number,ac_numberjoin,ac_lat,ac_long,user_name ->
            val i = Intent(context, UpdateNumberActivity::class.java)
            i.putExtra("user_id",user_id)
            i.putExtra("ac_id",ac_id)
            i.putExtra("ac_name", ac_name)
            i.putExtra("ac_number", ac_number)
            i.putExtra("numberjoin",ac_numberjoin)
            i.putExtra("ac_time",ac_time)
            i.putExtra("ac_type",ac_type)
            i.putExtra("ac_lat", ac_lat)
            i.putExtra("ac_long", ac_long)
            i.putExtra("user_name",user_name)
            startActivityForResult(i, REQUEST_CODE)
            mResponseActivity.clear()
            mAdapterActivity.notifyDataSetChanged()
        }


        recyclerview_Activity.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterActivity
            mAdapterActivity.notifyDataSetChanged()
        }
    }

    private fun initView(root: View) {
        mPreferrences = Preferrences(context!!)
        root.ID_user_Activity.setText("ID : "+mPreferrences.getusername())
        root.tv_username2.setText("คุณ : "+mPreferrences.getName_lname())
        Image = mPreferrences.getImage()
        Picasso.get().load(Utils.host+"/imageregister/"+Image).into(root.img_user)

        root.btn_activity.setOnClickListener {
            mResponseActivity.clear()
            mAdapterActivity.notifyDataSetChanged()
            startActivityForResult(
                Intent(context,Create_Activity::class.java),REQUEST_CODE
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode== REQUEST_CODE&&resultCode == Activity.RESULT_OK){
            setAPIActivity()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}