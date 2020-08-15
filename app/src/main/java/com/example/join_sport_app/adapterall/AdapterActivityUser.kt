package com.example.join_sport_app.adapterall

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.join_sport_app.R
import com.example.join_sport_app.rest.Utils
import com.example.join_sport_app.ui.menu.ActivityUser_Activity
import com.example.join_sport_app.ui.menu.UpDateActivityUser_Activity
import com.example.myapplicationproject.adapterall.ViewHolder
import com.example.myapplicationproject.model.ResponseActivity
import com.example.myapplicationproject.presenter.PresenterActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_.*
import java.util.ArrayList

class AdapterActivityUser(val ct : Context, private var mDataActivity : ArrayList<ResponseActivity>):RecyclerView.Adapter<ViewHolder>() {
    private var mPresenterActivity = PresenterActivity()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_activity_item,parent,false
            )
        )
    }

    override fun getItemCount()=mDataActivity.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Picasso.get().load(Utils.host+"/imageregister/"+ mDataActivity[position].u_img).into(holder.img)
        holder.usernameAC.text = mDataActivity.get(position).user_name
        holder.nameAC.text = mDataActivity.get(position).ac_name
        holder.numberAC.text = mDataActivity.get(position).ac_number.toString()
        holder.TimeAC.text = mDataActivity.get(position).ac_time
        if (mDataActivity.get(position).ac_type.toInt() == 0){holder.typeAC.text = "บาสเกตบอล" }
        else if (mDataActivity.get(position).ac_type.toInt() == 1){holder.typeAC.text = "ฟุตบอล"}
        else if (mDataActivity.get(position).ac_type.toInt() == 2){holder.typeAC.text = "ตะกร้อ"}
        else if (mDataActivity.get(position).ac_type.toInt() == 3){holder.typeAC.text = "วิ่ง"}
        else if (mDataActivity.get(position).ac_type.toInt() == 4){holder.typeAC.text = "เทนนิส"}
        else {holder.typeAC.text = "แบดมินตัน"}

        holder.numberjoinAC.text = mDataActivity.get(position).ac_numberjoin.toString()

        holder.itemView.setOnLongClickListener {
            val builderSingle = AlertDialog.Builder(ct)
            val animals = arrayOf("รายชื่อผู้เราเข้าร่วมกิจกรรม","แก้ไขกิจกรรม","ลบกิจกรรม")
            builderSingle.setItems(animals){
                    _,which ->
                when(which){
                    0->{

                    }
                    1->{
                        //แก้ไขกิจกรรม
                        val i =Intent(ct,UpDateActivityUser_Activity::class.java)
                        i.putExtra("ac_id",mDataActivity[position].ac_id)
                        i.putExtra("ac_name",mDataActivity[position].ac_name)
                        i.putExtra("ac_type",mDataActivity[position].ac_type)
                        i.putExtra("ac_time",mDataActivity[position].ac_time)
                        i.putExtra("ac_number",mDataActivity[position].ac_number)
                        i.putExtra("ac_long",mDataActivity[position].ac_long)
                        i.putExtra("ac_lat",mDataActivity[position].ac_lat)
                        i.putExtra("user_name",mDataActivity[position].user_name)
                        i.putExtra("user_id",mDataActivity[position].user_id)
                        i.putExtra("ac_numberjoin",mDataActivity[position].ac_numberjoin)
                        ct.startActivity(i)
                        (ct as Activity).finish()
                    }
                    2->{
                        //ลบกิจกรรม
                        setAPIDelete(mDataActivity[position].ac_id)

                    }
                }
            }
            builderSingle.show()
            true
        }

    }

    private fun setAPIDelete(acId: Int) {
        mPresenterActivity.DeletePresenterRX(acId,this::DeleteAcNext,this::DeleteError)
    }

    private fun DeleteError(message:String) {
    }

    private fun DeleteAcNext(responseActivity: ResponseActivity) {
        Toast.makeText(ct, "ลบเสร็จสิ้น", Toast.LENGTH_SHORT).show()
        ct.startActivity(Intent(ct,ActivityUser_Activity::class.java))
        (ct as Activity).finish()
    }
}
