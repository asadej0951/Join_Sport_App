package com.example.join_sport_app.adapterall

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
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
        if (mDataChat.get(position).u_id!=mPreferrences.getID()){
            Picasso.get().load(Utils.host+"/imageregister/"+mDataChat[position].img).into(holder.ImageUser)
            holder.carUser.apply {
                setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            holder.Message.text = mDataChat.get(position).message
            holder.nameUser.text = mDataChat.get(position).nameUser+" "+mDataChat.get(position).lnameUser
        }
        else{
            holder.car.apply {
                setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            holder.nameUser.apply {
                setTextColor(Color.parseColor("#FFFFFF"))
            }
            holder.MessageUser.apply {
                setTextColor(Color.parseColor("#FFFFFF"))
                text = mDataChat.get(position).message
            }
            holder.Message.apply {
                setTextColor(Color.parseColor("#FFFFFF"))
            }
        }
    }

    override fun getItemCount()=mDataChat.size

}
class ViewHolderChat(item:View):RecyclerView.ViewHolder(item){
    var ImageUser = item.findViewById<ImageView>(R.id.image_chat_User)
    var MessageUser = item.findViewById<TextView>(R.id.User_Chat)
    var Message = item.findViewById<TextView>(R.id.Message_Chat)
    var nameUser = item.findViewById<TextView>(R.id.name_Chat)
    var car = item.findViewById<LinearLayout>(R.id.crad)
    var carUser = item.findViewById<LinearLayout>(R.id.card_user)
}