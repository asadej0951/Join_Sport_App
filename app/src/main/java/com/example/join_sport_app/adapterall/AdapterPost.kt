package com.example.join_sport_app.adapterall

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.join_sport_app.R
import com.example.join_sport_app.model.ResponseComment
import com.example.join_sport_app.model.ResponsePost
import com.example.join_sport_app.presenter.PresenterPost
import com.example.join_sport_app.rest.Utils
import com.example.join_sport_app.ui.home.CommentActivity
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.view.*

class AdapterPost (val ct :Context,private var mDataPost: ArrayList<ResponsePost>):RecyclerView.Adapter<ViewHolderPost>(){

    lateinit var mPreferrences: Preferrences

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPost {
       return ViewHolderPost(
           LayoutInflater.from(parent.context).inflate(
               R.layout.item_post,parent,false
           )
       )
    }
    override fun getItemCount() = mDataPost.size

    override fun onBindViewHolder(holder: ViewHolderPost, position: Int) {
        if (mDataPost[position].p_message.length <50){
            holder.messageitem.text=mDataPost.get(position).p_message
        }
        else{
            holder.messageitem.text=mDataPost.get(position).p_message.substring(0,50)+"..."
        }
        Picasso.get().load(Utils.host+"/imageregister/"+mDataPost[position].u_img).into(holder.u_img)
        holder.usernamePost.text = mDataPost.get(position).username
        holder.timePost.text = mDataPost.get(position).p_time
        holder.itemView.setOnClickListener {
            val i = Intent(ct,CommentActivity::class.java)
            i.putExtra("ID", mDataPost[position].p_id)
            i.putExtra("u_id", mDataPost[position].user_id)
            i.putExtra("u_name", mDataPost[position].username)
            i.putExtra("u_img", mDataPost[position].u_img)
            i.putExtra("p_message", mDataPost[position].p_message)
            i.putExtra("p_time", mDataPost[position].p_time)
            ct.startActivity(i)

        }
    }



}


class ViewHolderPost(item:View):RecyclerView.ViewHolder(item){
    val u_img :ImageView = item.findViewById(R.id.img_userItem_post)
    val usernamePost :TextView = item.findViewById(R.id.tv_username_PostItem)
    val messageitem :TextView = item.findViewById(R.id.tv_messageItem)
    val timePost :TextView = item.findViewById(R.id.timeItem_Post)
}