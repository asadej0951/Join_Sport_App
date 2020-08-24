package com.example.join_sport_app.adapterall

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.join_sport_app.R
import com.example.join_sport_app.model.ResponterMenu
import com.example.join_sport_app.ui.login.LoginActivity
import com.example.join_sport_app.ui.menu.ShowProfileActivity
import com.example.join_sport_app.ui.menu.activity.ActivityUser_Activity
import com.example.join_sport_app.ui.menu.Check_Status_JoinStadiumActivity
import com.example.join_sport_app.ui.menu.Post_ProfileActivity
import com.example.join_sport_app.uioperator.menu.Check_JoinStadiam_Activity
import com.example.join_sport_app.uioperator.menu.Manage_StadiumActivity
import com.example.join_sport_app.uioperator.menu.ShowProFileOPT_Activity
import com.example.myapplicationproject.rest.local.Preferrences

class AdapterMenu (val ct:Context, private var mDataMenu :ArrayList<ResponterMenu>):RecyclerView.Adapter<ViewHolberMenu>() {

    lateinit var mPreferrences : Preferrences

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolberMenu {
       return ViewHolberMenu(
           LayoutInflater.from(parent.context).inflate(
               R.layout.item_menu,parent,false
           )
       )
    }

    override fun getItemCount(): Int = mDataMenu.size

    override fun onBindViewHolder(holder: ViewHolberMenu, position: Int) {
        holder.menu.text = mDataMenu.get(position).data
        holder.Img.setImageResource(mDataMenu[position].Img)
        holder.itemView.setOnClickListener {
            mPreferrences = Preferrences(ct)
            if (mDataMenu[position].data == "ข้อมูลส่วนตัว"&&mPreferrences.getstatus() =="ผู้ใช้งานทั่วไป"){
                val i =Intent(ct, ShowProfileActivity::class.java)
                ct.startActivity(i)
            }
            else if (mDataMenu[position].data == "ข้อมูลส่วนตัว"&&mPreferrences.getstatus() =="ผู้ประกอบการ"){
                val i =Intent(ct, ShowProFileOPT_Activity::class.java)
                ct.startActivity(i)
            }
            else if (mDataMenu[position].data == "กิจกรรม"){
                ct.startActivity(
                Intent(ct, ActivityUser_Activity::class.java)
                )
            }
            else if (mDataMenu[position].data == "จัดการสนามกีฬา"){
                ct.startActivity(
                    Intent(ct, Manage_StadiumActivity::class.java)
                )
            }
            else if (mDataMenu[position].data == "ข้อความโพสต์"){
                ct.startActivity(
                    Intent(ct,Post_ProfileActivity::class.java)
                )
            }
            else if (mDataMenu[position].data == "ตรวจสอบข้อมูล\nการจองสนาม"){
                ct.startActivity(
                    Intent(ct,Check_JoinStadiam_Activity::class.java)
                )
            }
            else if (mDataMenu[position].data == "ตรวจสอบสถานะ\n"+"การจองสนาม"){
                ct.startActivity(
                    Intent(ct,Check_Status_JoinStadiumActivity::class.java)
                )
            }
            else if (mDataMenu[position].data == "ออกจากระบบ"){
                showMessage()
            }
        }
    }
    private fun showMessage() {

        val DialogMessage = LayoutInflater.from(ct).inflate(R.layout.dialog_message,null)
        val text = DialogMessage.findViewById<TextView>(R.id.Message_dialog)
        text.setText("คุณต้องการออกจากระบบใช่หรือไม่?")
        val Btn_OK = DialogMessage.findViewById<Button>(R.id.btn_OK)
        val Btn_cancel = DialogMessage.findViewById<Button>(R.id.btn_cancel_Dialog)
        val Dialog =  AlertDialog.Builder(ct).apply {
            setIcon(R.drawable.exit)
            setTitle("LogOut")
            setView(DialogMessage)
        }
        val DialogButton = Dialog.show()

        Btn_OK.setText("ใช่")
        Btn_cancel.setText("ยกเลิก")
        Btn_OK.setOnClickListener {
            mPreferrences = Preferrences(ct)
            mPreferrences.clear()
            ct.startActivity(
                Intent(ct, LoginActivity::class.java)
            )
            (ct as Activity).finish()
        }
        Btn_cancel.setOnClickListener {

            DialogButton.dismiss()
        }

    }
}

class ViewHolberMenu(item :View):RecyclerView.ViewHolder(item){
    val menu = item.findViewById<TextView>(R.id.tv_menu_item)
    val Img = item.findViewById<ImageView>(R.id.Img_Menu)
}