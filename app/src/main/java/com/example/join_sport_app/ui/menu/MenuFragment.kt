package com.example.join_sport_app.ui.menu

import android.content.Intent
import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterMenu
import com.example.join_sport_app.model.DataMenu
import com.example.join_sport_app.model.ResponterMenu
import com.example.join_sport_app.rest.Utils
import com.example.join_sport_app.ui.login.LoginActivity
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_menu.view.*
import kotlinx.android.synthetic.main.fragment_notifications.view.*

class MenuFragment : Fragment() {

    lateinit var mPreferrences : Preferrences
    val data = ArrayList<ResponterMenu>()
    lateinit var mMenuAdapter:AdapterMenu
    private var Image = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_menu, container, false)
        mPreferrences = Preferrences(context!!)
        root.ID_user_Menu.setText("ID : "+mPreferrences.getusername())
        root.tv_username_Menu.setText("คุณ : "+mPreferrences.getName_lname())
        Image = mPreferrences.getImage()
        Picasso.get().load(Utils.host+"/imageregister/"+ Image).into(root.img_user_Menu)
        initView(root)
        return root
    }

    private fun initView(root: View) {
        for (i in DataMenu.datamenu.indices){
            data.add(ResponterMenu(DataMenu.datamenu[i],DataMenu.Imgmenu[i]))
        }
        mMenuAdapter = AdapterMenu(context!!,data)
        root.recyclerMenu.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mMenuAdapter
            mMenuAdapter.notifyDataSetChanged()
        }
    }


}