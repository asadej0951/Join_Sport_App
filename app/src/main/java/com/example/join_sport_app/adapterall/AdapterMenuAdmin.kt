package com.example.join_sport_app.adapterall

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.join_sport_app.R
import com.example.join_sport_app.model.ResponterMenu
import com.example.join_sport_app.model.ResponterMenuAM
import com.example.join_sport_app.ui.login.LoginActivity
import com.example.myapplicationproject.rest.local.Preferrences

class AdapterMenuAdmin(var ct :Context,private var mDataMenu :ArrayList<ResponterMenuAM>):RecyclerView.Adapter<ViewHolderAdmin>() {
    lateinit var mPreferrences: Preferrences

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAdmin {
        return ViewHolderAdmin(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_menu_admin,parent,false
            )
        )
    }

    override fun getItemCount(): Int =mDataMenu.size

    override fun onBindViewHolder(holder: ViewHolderAdmin, position: Int) {
        holder.menuAdmin.text = mDataMenu[position].data
        holder.itemView.setOnClickListener {
            if(mDataMenu[position].data == "โพสต์ข้อความ"){

            }
            else  if(mDataMenu[position].data == "รายชื่อผู้ใช้งาน"){

            }
            else  if(mDataMenu[position].data == "กิจกรรม"){

            }
            else  if(mDataMenu[position].data == "สนามกีฬา"){

            }
            else  if(mDataMenu[position].data == "ออกจากระบบ"){
                mPreferrences = Preferrences(ct)
                mPreferrences.clear()
                ct.startActivity(
                    Intent(ct,LoginActivity::class.java)
                )
                (ct as Activity).finish()
            }
        }
    }
}
class ViewHolderAdmin(item:View):RecyclerView.ViewHolder(item){
    val menuAdmin = item.findViewById<TextView>(R.id.item_Menu_Admin)
}