package com.example.myapplicationproject.adapterall

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.join_sport_app.R
import com.example.join_sport_app.rest.Utils
import com.example.join_sport_app.ui.dashboard.UpdateNumberActivity
import com.example.myapplicationproject.model.ResponseActivity
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import java.util.*

class AdapterActivity(
    val ct :Context, private var mDataActivity : ArrayList<ResponseActivity>,
    private var mInvork:(Int,String,String,String,String,Int,Int,String,String,String) ->(Unit))
    :RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_activity_item,parent,false
            )
        )
    }

    override fun getItemCount() = mDataActivity.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        Picasso.get().load(Utils.host+"/imageregister/"+mDataActivity[position].u_img).into(holder.img)
            holder.usernameAC.text = mDataActivity.get(position).user_name
            holder.nameAC.text = mDataActivity.get(position).ac_name
            holder.numberAC.text = mDataActivity.get(position).ac_number.toString()
            holder.TimeAC.text = mDataActivity.get(position).ac_time
        if (mDataActivity.get(position).ac_type.toInt() == 0){holder.typeAC.text = "บาสเกตบอล" }
        else if (mDataActivity.get(position).ac_type.toInt() == 1){holder.typeAC.text = "ฟุตบอล"}
        else if (mDataActivity.get(position).ac_type.toInt() == 2){holder.typeAC.text = "ตะกร้อ"}
        else if (mDataActivity.get(position).ac_type.toInt() == 3){holder.typeAC.text = "วิ่ง"}
        else if (mDataActivity.get(position).ac_type.toInt() == 4){holder.typeAC.text = "เทนนิส"}
        else {holder.typeAC.text = "แบดมินตัน"}
            holder.numberjoinAC.text = mDataActivity.get(position).ac_numberjoin.toString()

        holder.itemView.setOnClickListener {
            mInvork.invoke(mDataActivity[position].ac_id,mDataActivity[position].user_id,
                mDataActivity[position].ac_name,mDataActivity[position].ac_type,mDataActivity[position].ac_time
            ,mDataActivity[position].ac_number,mDataActivity[position].ac_numberjoin,mDataActivity[position].ac_lat,
                mDataActivity[position].ac_long,mDataActivity[position].user_name)
            }
        }

    }
class ViewHolder(item:View):RecyclerView.ViewHolder(item){

    val usernameAC :TextView =item.findViewById(R.id.tv_username_AC)
    val nameAC :TextView = item.findViewById(R.id.tv_nameAC_item)
    val typeAC :TextView = item.findViewById(R.id.tv_type_item)
    val numberAC :TextView = item.findViewById(R.id.tv_numberAC_item)
    val numberjoinAC : TextView = item.findViewById(R.id.tv_numberjoinAC_item)
    val TimeAC : TextView = item.findViewById(R.id.tv_timeAC_item)
    val img = item.findViewById<ImageView>(R.id.img_item_userActivity)
}