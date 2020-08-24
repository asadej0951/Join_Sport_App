package com.example.join_sport_app.adapterall

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.join_sport_app.R
import com.example.join_sport_app.model.ResponseJoinActivityItem
import com.example.join_sport_app.rest.Utils
import com.squareup.picasso.Picasso

class AdapterJoinActivity(var ct :Context,private var mDataJoin : ArrayList<ResponseJoinActivityItem>):RecyclerView.Adapter<ViewItemJoinAcyivity>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewItemJoinAcyivity {
        return ViewItemJoinAcyivity(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_joinactivity,parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewItemJoinAcyivity, position: Int) {
        when(mDataJoin.get(position).type_activity.toInt()){
            0 -> Picasso.get().load(R.drawable.basketball).into(holder.img_User)
            1 -> Picasso.get().load(R.drawable.sport).into(holder.img_User)
            2 -> Picasso.get().load(R.drawable.volleyball).into(holder.img_User)
            3 -> Picasso.get().load(R.drawable.ball).into(holder.img_User)
            4 -> Picasso.get().load(R.drawable.takraw).into(holder.img_User)
            5 -> Picasso.get().load(R.drawable.run).into(holder.img_User)
            6 -> Picasso.get().load(R.drawable.tennis).into(holder.img_User)
            7 -> Picasso.get().load(R.drawable.badminton).into(holder.img_User)
            8 -> Picasso.get().load(R.drawable.breakdance).into(holder.img_User)
        }
        holder.name_Activity.text = "กิจกรรม : "+mDataJoin.get(position).name_activity
        holder.name_user.text = "จัดตั้งโดย : "+mDataJoin.get(position).name_user
    }

    override fun getItemCount() = mDataJoin.size
}

class ViewItemJoinAcyivity(item: View):RecyclerView.ViewHolder(item){
    val img_User = item.findViewById<ImageView>(R.id.img_Item_Join)
    val name_Activity = item.findViewById<TextView>(R.id.Name_Activity)
    val name_user = item.findViewById<TextView>(R.id.Name_User)
}