package com.example.join_sport_app.adapterall.adaptermenudata

import android.app.AlertDialog
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.ViewHolderPost
import com.example.join_sport_app.model.ResponsePost
import com.example.join_sport_app.rest.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post__profile.*

class AdapterPostProfile (val ct : Context, private var mDataPost: ArrayList<ResponsePost>):RecyclerView.Adapter<ViewHolderPostProfile>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPostProfile {
        return ViewHolderPostProfile(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_post,parent,false
            )
        )
    }
    override fun getItemCount() = mDataPost.size

    override fun onBindViewHolder(holder: ViewHolderPostProfile, position: Int) {
        Picasso.get().load(Utils.host+"/imageregister/"+mDataPost[position].u_img).into(holder.u_img)
        if (mDataPost[position].p_message.length <50){
            holder.messageitem.text=mDataPost.get(position).p_message
        }
        else{
            holder.messageitem.text=mDataPost.get(position).p_message.substring(0,50)+"..."
        }
        holder.usernamePost.text = mDataPost.get(position).username
        holder.timePost.text = mDataPost.get(position).p_time
        holder.itemView.setOnLongClickListener {
            val builderSingle = AlertDialog.Builder(ct)
            val animals = arrayOf("แก้ไขกิจกรรม","ลบกิจกรรม")
            builderSingle.setItems(animals){
                    _,which ->
                when(which){
                    0->{
                    }
                    1->{
                    }
                }
            }
            builderSingle.show()
            true
        }
    }
}

class ViewHolderPostProfile(item:View):RecyclerView.ViewHolder(item){
    val u_img : ImageView = item.findViewById(R.id.img_userItem_post)
    val usernamePost : TextView = item.findViewById(R.id.tv_username_PostItem)
    val messageitem : TextView = item.findViewById(R.id.tv_messageItem)
    val timePost : TextView = item.findViewById(R.id.timeItem_Post)
}