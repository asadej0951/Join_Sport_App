package com.example.join_sport_app.adapterall

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.join_sport_app.R
import com.example.join_sport_app.model.ResponseJoinStadium
import com.example.join_sport_app.model.ResponseShowStadium
import com.example.join_sport_app.model.ResponseStadiam
import com.example.join_sport_app.model.getname.ResponseNameOPT
import com.example.join_sport_app.presenter.PresenterGetName
import com.example.join_sport_app.presenter.PresenterOperator
import com.example.join_sport_app.rest.Utils
import com.example.join_sport_app.uioperator.menu.Manage_StadiumActivity
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_stadiam.view.*
import java.io.File

class AdapterStadiam(val ct : Context,private var mDataStadiam :ArrayList<ResponseShowStadium>):RecyclerView.Adapter<ViewHolderStadiam>(){
    lateinit var mPreferrences: Preferrences
    var mPresenterOperator = PresenterOperator()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderStadiam {
       return ViewHolderStadiam(
           LayoutInflater.from(parent.context).inflate(
               R.layout.item_stadiam,parent,false
           )
       )
    }

    override fun getItemCount(): Int = mDataStadiam.size

    override fun onBindViewHolder(holder: ViewHolderStadiam, position: Int) {
        mPreferrences = Preferrences(ct)

        holder.nameStadiam.text = mDataStadiam[position].s_name
        Picasso.get().load(Utils.host+"/imageregister/"+mDataStadiam.get(position).o_img).into(holder.imguser)

        Picasso.get().load(Utils.host+"/imageStadium/"+mDataStadiam.get(position).img).into(holder.imgStadiam)

        if (mDataStadiam.get(position).o_id.toString() ==mPreferrences.getID()){
            holder.itemView.setOnLongClickListener {
                val builderSingle = AlertDialog.Builder(ct)
                val animals = arrayOf("แก้ไขข้อมูลสนามกีฬา","ลบสนามกีฬา")
                builderSingle.setItems(animals){
                        _,which ->
                    when(which){
                        0->{
                        }
                        1->{
                            setAPIDeleteStadium(mDataStadiam.get(position).s_id.toString())
                            setAPIDeleteJoinStadium(mDataStadiam.get(position).s_id.toString())
                        }
                    }
                }
                builderSingle.show()
                true
            }
        }
    }

    private fun setAPIDeleteJoinStadium(sId: String) {
        mPresenterOperator.DeleteJoinStadium_sIDPresenterRX(sId.toInt(),this::DeleteJoinStadiumNext,this::DeleteJoinStadiumError)
    }

    private fun DeleteJoinStadiumNext(response : ResponseJoinStadium) {
        ct.startActivity(
            Intent(ct,Manage_StadiumActivity::class.java)
        )
        (ct as Activity).finish()
    }

    private fun DeleteJoinStadiumError(message: String) {}

    private fun setAPIDeleteStadium(sId: String) {
        mPresenterOperator.DeleteStadiumPresenterRX(sId.toInt(),this::DeleteStadiumNext,this::DeleteStadiumError)
    }
    private fun DeleteStadiumNext(responseStadiam: ResponseStadiam) {}
    private fun DeleteStadiumError(message : String) {}
}

class ViewHolderStadiam(item:View):RecyclerView.ViewHolder(item){
    var imguser = item.findViewById<ImageView>(R.id.img_item_userstadiam)
    var nameStadiam = item.findViewById<TextView>(R.id.tv_namestadiam)
    var imgStadiam = item.findViewById<ImageView>(R.id.img_stadiam)
}