package com.example.join_sport_app.adapterall

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.join_sport_app.R
import com.example.join_sport_app.model.ResponseComment
import com.example.join_sport_app.rest.Utils
import com.squareup.picasso.Picasso

class AdapterComment(val ct :Context , private var mDataComment : ArrayList<ResponseComment>):RecyclerView.Adapter<ViewHolberComment>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolberComment {
        return ViewHolberComment(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_comment,parent,false
            )
        )
    }

    override fun getItemCount(): Int = mDataComment.size

    override fun onBindViewHolder(holder: ViewHolberComment, position: Int) {
//        Toast.makeText(ct, mDataComment.get(position).img, Toast.LENGTH_SHORT).show()

        Picasso.get().load(Utils.host+"/imageregister/"+mDataComment[position].img).into(holder.img)

        holder.nameUser.text = mDataComment[position].username
        holder.message.text = mDataComment[position].com_message
        holder.time.text = mDataComment[position].com_time
    }

}

class ViewHolberComment(item:View):RecyclerView.ViewHolder(item){
    val nameUser = item.findViewById<TextView>(R.id.name_com)
    val message = item.findViewById<TextView>(R.id.tv_message_com)
    val img = item.findViewById<ImageView>(R.id.img_com)
    val time = item.findViewById<TextView>(R.id.time_comment)
}