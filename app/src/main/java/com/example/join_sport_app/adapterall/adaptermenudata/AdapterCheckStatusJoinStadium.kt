package com.example.join_sport_app.adapterall.adaptermenudata

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.join_sport_app.R
import com.example.join_sport_app.model.ResponseJoinStadium

class AdapterCheckStatusJoinStadium (val ct: Context, private var mDataMenu :ArrayList<ResponseJoinStadium>):RecyclerView.Adapter<ViewHolderCheckStatus>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCheckStatus {
       return ViewHolderCheckStatus(
           LayoutInflater.from(parent.context).inflate(
               R.layout.item_status_joinstadium,parent,false
           )
       )
    }

    override fun getItemCount()=mDataMenu.size

    override fun onBindViewHolder(holder: ViewHolderCheckStatus, position: Int) {
       holder.nameStadium.text = mDataMenu.get(position).s_name
        holder.Date.text = mDataMenu.get(position).r_Date
        holder.Time.text = mDataMenu.get(position).r_timein.substring(0,5)+" น. - "+mDataMenu.get(position).r_timeout.substring(0,5)+" น."
        holder.Price.text = "ค่าบริการ : "+mDataMenu.get(position).u_price+" บาท"
        if (mDataMenu.get(position).r_status =="1รออนุมัติ"){
            holder.Status.text = mDataMenu.get(position).r_status.substring(1)
            holder.Status.setTextColor(Color.parseColor("#00CCFF"))

        }
        else if(mDataMenu.get(position).r_status =="3อนุมัติ"){
            holder.Status.text = mDataMenu.get(position).r_status.substring(1)+"แล้ว"
            holder.Status.setTextColor(Color.parseColor("#00FF00"))
        }
        else if (mDataMenu.get(position).r_status =="2ไม่อนุมัติ"){
            holder.Status.text = mDataMenu.get(position).r_status.substring(1)
            holder.Status.setTextColor(Color.parseColor("#FF0000"))
        }

    }
}

class ViewHolderCheckStatus(item:View):RecyclerView.ViewHolder(item){
    val nameStadium = item.findViewById<TextView>(R.id.name_CheckStatus)
    val Date = item.findViewById<TextView>(R.id.Day_CheckStatus)
    val Time = item.findViewById<TextView>(R.id.time_CheckStatus)
    val Price = item.findViewById<TextView>(R.id.Price_checkStatus)
    val Status = item.findViewById<TextView>(R.id.Staus_CheckStatus)
}