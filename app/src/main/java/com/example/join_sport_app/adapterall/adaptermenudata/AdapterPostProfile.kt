package com.example.join_sport_app.adapterall.adaptermenudata

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.ViewHolderPost
import com.example.join_sport_app.model.ResponsePost
import com.example.join_sport_app.presenter.PresenterPost
import com.example.join_sport_app.rest.Utils
import com.example.join_sport_app.ui.menu.Post_ProfileActivity
import com.example.join_sport_app.ui.menu.Update_Post_Activity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post__profile.*

class AdapterPostProfile (val ct : Context, private var mDataPost: ArrayList<ResponsePost>):RecyclerView.Adapter<ViewHolderPostProfile>(){
    var mPresenterPost = PresenterPost()
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
                        val i = Intent(ct,Update_Post_Activity::class.java)
                        i.putExtra("p_id",mDataPost[position].p_id)
                        i.putExtra("p_message",mDataPost[position].p_message)
                        ct.startActivity(i)
                        (ct as Activity).finish()
                    }
                    1->{
                        showMessageDelete(mDataPost[position].p_id)
                    }
                }
            }
            builderSingle.show()
            true
        }
    }

    private fun showMessageDelete(acId: Int) {

        val DialogMessage = LayoutInflater.from(ct).inflate(R.layout.dialog_message,null)
        val text = DialogMessage.findViewById<TextView>(R.id.Message_dialog)
        text.setText("การลบข้อความโพสต์ไม่สามารถกู้คืนได้ \nคุณแน่ใจจะลบข้อความโพสต์แน่หรือไหม?")
        val Btn_OK = DialogMessage.findViewById<Button>(R.id.btn_OK)
        val Btn_cancel = DialogMessage.findViewById<Button>(R.id.btn_cancel_Dialog)
        val Dialog =  AlertDialog.Builder(ct).apply {
            setIcon(R.drawable.alert)
            setTitle("คำเตือน!!")
            setView(DialogMessage)
        }
        val DialogButton = Dialog.show()

        Btn_OK.setText("แน่ใจ")
        Btn_cancel.setText("ยกเลิก")
        Btn_OK.setOnClickListener {
            DeleteAPI(acId)
            (ct as Activity).finish()
            DialogButton.dismiss()
        }
        Btn_cancel.setOnClickListener {

            DialogButton.dismiss()
        }

    }

    private fun DeleteAPI(PID:Int){
        mPresenterPost.deletePostPresenterRX(PID,this::DeleteNext,this::DeleteError)
    }

    private fun DeleteNext(responsePost: ResponsePost) {
        Toast.makeText(ct, "ลบเสร็จสิ้น", Toast.LENGTH_SHORT).show()
        ct.startActivity(
            Intent(ct,Post_ProfileActivity::class.java)
        )
    }

    private fun DeleteError(message:String) {
        Toast.makeText(ct, "ลบไม่สำเร็จ", Toast.LENGTH_SHORT).show()
        ct.startActivity(
            Intent(ct,Post_ProfileActivity::class.java)
        )
    }

}

class ViewHolderPostProfile(item:View):RecyclerView.ViewHolder(item){
    val u_img : ImageView = item.findViewById(R.id.img_userItem_post)
    val usernamePost : TextView = item.findViewById(R.id.tv_username_PostItem)
    val messageitem : TextView = item.findViewById(R.id.tv_messageItem)
    val timePost : TextView = item.findViewById(R.id.timeItem_Post)
}