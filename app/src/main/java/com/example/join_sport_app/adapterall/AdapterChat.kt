package com.example.join_sport_app.adapterall

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.join_sport_app.R
import com.example.join_sport_app.model.ResponseGetChat
import com.example.join_sport_app.rest.Utils
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import java.util.ArrayList

class AdapterChat(var ct:Context, private var mDataChat : ArrayList<ResponseGetChat>):RecyclerView.Adapter<ViewHolderChat>() {
    var mPreferrences = Preferrences(ct)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderChat {
        return ViewHolderChat(LayoutInflater.from(parent.context).inflate(R.layout.item_chat_user,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolderChat, position: Int) {
        Picasso.get().load(Utils.host+"/imageregister/"+mDataChat[position].img).into(holder.imageChat)
        if (mPreferrences.getID()==mDataChat.get(position).u_id){
            holder.nameChat.text = mDataChat.get(position).nameUser+" "+mDataChat.get(position).lnameUser
        }
        else{
            holder.nameChat.setTextColor(Color.parseColor("#000000"))
            holder.nameChat.text = mDataChat.get(position).nameUser+" "+mDataChat.get(position).lnameUser
        }
        holder.messageChar.text = mDataChat.get(position).message
        holder.timeChat.text = mDataChat.get(position).time
    }

    override fun getItemCount()=mDataChat.size

}
class ViewHolderChat(item:View):RecyclerView.ViewHolder(item){
    val imageChat = item.findViewById<ImageView>(R.id.img_chat)
    val nameChat = item.findViewById<TextView>(R.id.name_chat)
    val messageChar = item.findViewById<TextView>(R.id.tv_message_chat)
    val timeChat = item.findViewById<TextView>(R.id.time_chat)
}