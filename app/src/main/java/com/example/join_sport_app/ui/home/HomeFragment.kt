package com.example.join_sport_app.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterPost
import com.example.join_sport_app.model.ResponsePost
import com.example.join_sport_app.presenter.PresenterPost
import com.example.join_sport_app.rest.Utils
import com.example.myapplicationproject.adapterall.AdapterActivity
import com.example.myapplicationproject.model.ResponseActivity
import com.example.myapplicationproject.rest.local.Preferrences
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.dialog_post.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.contracts.contract

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    lateinit var mPreferrences: Preferrences
    var mPresenterPost = PresenterPost()
    var mResponsePost = ArrayList<ResponsePost>()
    lateinit var mAdapterPost : AdapterPost
    private var Image = ""

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        setAPIShowPost()
        initview(root)
        return root.rootView
    }

    private fun setAPIShowPost() {
        mPresenterPost.ShowPostPresenterRX(this::ShowPostNext,this::ShowPostError)
    }

    private fun ShowPostError(message: String) {
    }

    private fun ShowPostNext(responsePost: List<ResponsePost>) {
        for (i in responsePost.indices){
            mResponsePost.add(responsePost[i])
        }
        mAdapterPost = AdapterPost(context!!,mResponsePost)
        recyclerView_Post.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterPost
            mAdapterPost.notifyDataSetChanged()
        }
    }
    private fun initview(root: View) {
        mPreferrences = Preferrences(context!!)
        mPreferrences.getstatus()
        mPreferrences.getID()
        Image = mPreferrences.getImage()
        Picasso.get().load(Utils.host+"/imageregister/"+Image).into(root.img_user_Post)
        root.ID_user_Home.setText("ID : "+mPreferrences.getusername())
        root.tv_username_Post.setText("คุณ : "+mPreferrences.getName_lname())
        root.findViewById<FloatingActionButton>(R.id.fab_Post).apply {
            setOnClickListener {
                showDialog()
            }
        }
    }
    private fun showDialog() {
        val mDialogView = layoutInflater.inflate(R.layout.dialog_post,null)
        val message = mDialogView.findViewById<EditText>(R.id.ET_messagePost)
        val Dialog= AlertDialog.Builder(context)
        mDialogView.tv_dialog_post.setText(mPreferrences.getName_lname())
        Picasso.get().load(Utils.host+"/imageregister/"+Image).into(mDialogView.Img_Dialog)
        Dialog.setTitle("โพสต์ข้อความ")
        Dialog.setView(mDialogView)
        Dialog.setIcon(R.drawable.iconpost)
        val DialogButton = Dialog.show()
        mDialogView.btn_Post.setOnClickListener {
            setAPIPost(message.text.toString())
            mResponsePost.clear()
            DialogButton.dismiss()
        }
        mDialogView.btn_cancel_Post.setOnClickListener {
            DialogButton.dismiss()
        }
    }
    private fun setAPIPost(message: String) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val min = calendar.get(Calendar.MINUTE)
        var DD =""
        var Mon = ""
        var HH = ""
        var MM = ""
        if (day.toString().length ==1){DD = "0${day}"}else{DD = "${day}"}
        if (month.toString().length ==1){Mon = "0${month}"}else{Mon = "${month}"}
        if (hour.toString().length==1){HH ="0${hour}"}else{HH ="${hour}"}
        if (min.toString().length==1){MM ="0${min}"}else{MM ="${min}"}
        val p_time = "${DD}/${Mon}/${year} - ${HH}:${MM}"
        mPresenterPost.PostPresenterRX(mPreferrences.getID(),mPreferrences.getName_lname(),Image,message,p_time,mPreferrences.getstatus(),this::PostNext,this::PostError)
    }
    private fun PostError(message: String) {
    }

    private fun PostNext(responseOperator : ResponsePost) {
        setAPIShowPost()
    }
}