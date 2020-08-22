package com.example.join_sport_app.uioperator.home

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterPost
import com.example.join_sport_app.model.ResponsePost
import com.example.join_sport_app.presenter.PresenterPost
import com.example.join_sport_app.rest.Utils
import com.example.myapplicationproject.rest.local.Preferrences
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_post.view.*
import kotlinx.android.synthetic.main.fragment_home__operator.*
import kotlinx.android.synthetic.main.fragment_home__operator.view.*
import java.util.*
import kotlin.collections.ArrayList

class Home_OperatorFragment : Fragment() {

    lateinit var mPreferrences: Preferrences
    var mPresenterPost = PresenterPost()
    var mResponsePost = ArrayList<ResponsePost>()
    lateinit var mAdapterPost : AdapterPost
    private val REQ_CODE = 999

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_home__operator, container, false)
        initView(root)
        setAPIShowPost()
        return root.rootView
    }

    internal fun setAPIShowPost() {
        mPresenterPost.ShowPostPresenterRX(this::ShowPostNext,this::ShowPostError)
    }

    private fun ShowPostError(message: String) {
    }

    private fun ShowPostNext(responsePost: List<ResponsePost>) {
        for (i in responsePost.indices){
            mResponsePost.add(responsePost[i])
        }
        mAdapterPost = AdapterPost(context!!,mResponsePost)
        recyclerView_Post_OPT.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterPost
            mAdapterPost.notifyDataSetChanged()
        }
    }

    private fun initView(root: View) {
        mPreferrences = Preferrences(context!!)
        root.ID_OPT_Home.setText("ID : "+mPreferrences.getusername())
        root.tv_user_HomeOPT.setText("คุณ : "+mPreferrences.getName_lname())
        Picasso.get().load(Utils.host+"/imageregister/"+mPreferrences.getImage()).into(root.img_user_HomeOPT)
        root.findViewById<FloatingActionButton>(R.id.btn_Post_OTP).apply {
            setOnClickListener {
//                val DialogPost = PostDialogFragment()
//                DialogPost.show(parentFragmentManager,"Dialog")
                showDialog()
            }
        }
    }

    private fun showDialog() {
        val mDialogView = layoutInflater.inflate(R.layout.dialog_post,null)
        val message = mDialogView.findViewById<EditText>(R.id.ET_messagePost)
        mDialogView.tv_dialog_post.setText(mPreferrences.getusername())
        Picasso.get().load(Utils.host+"/imageregister/"+mPreferrences.getImage()).into(mDialogView.Img_Dialog)
        val Dialog= AlertDialog.Builder(context)
            Dialog.setTitle("โพสต์ข้อความ")
            Dialog.setView(mDialogView)
        Dialog.setIcon(R.drawable.iconpost)
            Dialog.setPositiveButton("โพสต์"){dialog, which ->
                setAPIPost(message.text.toString())
                mResponsePost.clear()
            }
            Dialog.setNegativeButton("ยกเลิก"){
                dialog, which ->
            }
        Dialog.show()
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
        val u_img = mPreferrences.getImage()
        mPresenterPost.PostPresenterRX(mPreferrences.getID(),mPreferrences.getName_lname(),u_img,message,p_time,"ผู้ประกอบการ",this::PostNext,this::PostError)
    }
    private fun PostError(message: String) {
    }

    private fun PostNext(responseOperator : ResponsePost) {
        setAPIShowPost()
    }

}