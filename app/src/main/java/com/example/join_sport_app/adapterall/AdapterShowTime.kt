package com.example.join_sport_app.adapterall

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.join_sport_app.R
import com.example.join_sport_app.model.ResponseJoinStadium

class AdapterShowTime(var ct:Context,private var mDataTime: ArrayList<ResponseJoinStadium>):RecyclerView.Adapter<ViewHolderShowTime>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderShowTime {
        return ViewHolderShowTime(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_joinstadiam,parent,false
            )
        )
    }

    override fun getItemCount() = mDataTime.size

    override fun onBindViewHolder(holder: ViewHolderShowTime, position: Int) {
            holder.Time.text = mDataTime[position].r_timein.substring(0,5)+" น. - "+mDataTime[position].r_timeout.substring(0,5)+" น."
    }
}
class ViewHolderShowTime(item:View):RecyclerView.ViewHolder(item){
    val Time = item.findViewById<TextView>(R.id.Time_JoinStadiam_List)
}