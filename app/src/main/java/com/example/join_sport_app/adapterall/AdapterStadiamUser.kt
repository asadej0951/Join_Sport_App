package com.example.join_sport_app.adapterall

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
import com.example.join_sport_app.model.ResponseShowStadium
import com.example.join_sport_app.model.ResponseStadiam
import com.example.join_sport_app.rest.Utils
import com.example.join_sport_app.ui.notifications.Detail_Stadiam_Activity
import com.squareup.picasso.Picasso
import java.io.File

class AdapterStadiamUser(val ct:Context,private var mDataStadiam :ArrayList<ResponseShowStadium>):RecyclerView.Adapter<ViewHolderStadiamUser>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderStadiamUser {
        return ViewHolderStadiamUser(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_stadiam,parent,false
            )
        )
    }

    override fun getItemCount(): Int = mDataStadiam.size

    override fun onBindViewHolder(holder: ViewHolderStadiamUser, position: Int) {

        Picasso.get().load(Utils.host+"/imageregister/"+mDataStadiam.get(position).img).into(holder.ImgOPT)

        Picasso.get().load(Utils.host+"/imageStadium/"+mDataStadiam.get(position).Simg).into(holder.ImgStadiam)
        holder.nameStadiam.setText(mDataStadiam[position].s_name)
        holder.itemView.setOnClickListener {
            var i = Intent(ct,Detail_Stadiam_Activity::class.java)
            i.putExtra("s_id",mDataStadiam[position].s_id)
            i.putExtra("o_id",mDataStadiam[position].o_id)
            i.putExtra("s_name",mDataStadiam[position].s_name)
            i.putExtra("s_lat",mDataStadiam[position].s_lat)
            i.putExtra("s_long",mDataStadiam[position].s_long)
            i.putExtra("s_address",mDataStadiam[position].s_address)
            i.putExtra("s_type",mDataStadiam[position].s_type)
            i.putExtra("s_price",mDataStadiam[position].s_price)
            i.putExtra("s_timeopen",mDataStadiam[position].s_timeopen)
            i.putExtra("s_timeclose",mDataStadiam[position].s_timeclose)
            ct.startActivity(i)
        }
    }

}

class ViewHolderStadiamUser(item: View):RecyclerView.ViewHolder(item){
    val ImgOPT = item.findViewById<ImageView>(R.id.img_item_userstadiam)
    val nameStadiam = item.findViewById<TextView>(R.id.tv_namestadiam)
    val ImgStadiam = item.findViewById<ImageView>(R.id.img_stadiam)
}