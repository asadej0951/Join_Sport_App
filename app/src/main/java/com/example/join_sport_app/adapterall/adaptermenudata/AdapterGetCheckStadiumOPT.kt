package com.example.join_sport_app.adapterall.adaptermenudata

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.join_sport_app.R
import com.example.join_sport_app.model.ResponseJoinStadium
import com.example.join_sport_app.presenter.PresenterOperator
import com.example.join_sport_app.rest.Utils
import com.example.join_sport_app.uioperator.menu.Check_JoinStadiam_Activity
import com.example.join_sport_app.uioperator.menu.Detail_JoinStadium_Activity
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class AdapterGetCheckStadiumOPT (val ct: Context, private var mDataMenu :ArrayList<ResponseJoinStadium>):RecyclerView.Adapter<ViewHoldergetcheckStadium>(){

    var mPresenterOperator = PresenterOperator()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoldergetcheckStadium {
        return ViewHoldergetcheckStadium(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_check_stadium,parent,false
            )
        )
    }

    override fun getItemCount() =mDataMenu.size

    override fun onBindViewHolder(holder: ViewHoldergetcheckStadium, position: Int) {
        Picasso.get().load(Utils.host+"/imageregister/"+mDataMenu.get(position).img).into(holder.ImgUser)
        holder.DayStadium.text = mDataMenu.get(position).r_Date
       holder.nameStrdium.text = mDataMenu.get(position).s_name
        holder.nameUser.text = mDataMenu.get(position).u_name
        holder.TimeStadium.text = mDataMenu.get(position).r_timein.substring(0,5)+" น. - "+mDataMenu.get(position).r_timeout.substring(0,5)+" น."
        if (mDataMenu.get(position).r_status =="1รออนุมัติ"){
            holder.StausStadium.text = mDataMenu.get(position).r_status.substring(1)
            holder.StausStadium.setTextColor(Color.parseColor("#00CCFF"))
        }
        else if(mDataMenu.get(position).r_status =="3อนุมัติ"){
            holder.StausStadium.text = mDataMenu.get(position).r_status.substring(1)+"แล้ว"
            holder.StausStadium.setTextColor(Color.parseColor("#00FF00"))
            holder.itemView.setOnLongClickListener {
                val builderSingle = AlertDialog.Builder(ct)
                val animals = arrayOf("เข้ารับบริการแล้ว","ไม่เข้ารับบริการ")
                builderSingle.setItems(animals){
                        _,which ->
                    when(which){
                        0->{
                            setAPIDelete(mDataMenu[position].r_id)
                            Toast.makeText(ct, "เช็คเรียบร้อย", Toast.LENGTH_SHORT).show()
                        }
                        1->{
                        }
                    }
                }
                builderSingle.show()
                true
            }
        }
        else if (mDataMenu.get(position).r_status =="2ไม่อนุมัติ"){
            holder.StausStadium.text = mDataMenu.get(position).r_status.substring(1)
            holder.StausStadium.setTextColor(Color.parseColor("#FF0000"))
        }
        holder.itemView.setOnClickListener {
            val i = Intent(ct,Detail_JoinStadium_Activity::class.java)
            i.putExtra("r_id",mDataMenu[position].r_id)
            i.putExtra("u_id",mDataMenu[position].u_id)
            i.putExtra("o_id",mDataMenu[position].o_id)
            i.putExtra("s_id",mDataMenu[position].s_id)
            i.putExtra("u_name",mDataMenu[position].u_name)
            i.putExtra("s_name",mDataMenu[position].s_name)
            i.putExtra("u_phone",mDataMenu[position].u_phone)
            i.putExtra("r_Date",mDataMenu[position].r_Date)
            i.putExtra("r_timein",mDataMenu[position].r_timein)
            i.putExtra("r_timeout",mDataMenu[position].r_timeout)
            i.putExtra("u_price",mDataMenu[position].u_price)
            i.putExtra("r_status",mDataMenu[position].r_status)
            i.putExtra("r_type",mDataMenu[position].r_type)
            i.putExtra("img",mDataMenu[position].img)
            ct.startActivity(i)
            (ct as Activity).finish()
        }
    }

    private fun setAPIDelete(rId: String) {
        mPresenterOperator.DeleteJoinStadiumOPTPresenterRX(rId.toInt(),this::DeleteNext,this::DeleteError)
    }

    private fun DeleteNext(response : ResponseJoinStadium) {
        ct.startActivity(Intent(ct,Check_JoinStadiam_Activity::class.java))
        (ct as Activity).finish()
    }

    private fun DeleteError(message:String) {
    }
}
class ViewHoldergetcheckStadium(item: View):RecyclerView.ViewHolder(item){

    val ImgUser = item.findViewById<ImageView>(R.id.ImgUser_item)
    val nameStrdium = item.findViewById<TextView>(R.id.name_CheckStadium)
    val nameUser = item.findViewById<TextView>(R.id.name_useritemCheckStadium)
    val DayStadium = item.findViewById<TextView>(R.id.Day_CheckStadium)
    val TimeStadium = item.findViewById<TextView>(R.id.time_CheckStadium)
    val StausStadium = item.findViewById<TextView>(R.id.Staus_CheckStadium)
}