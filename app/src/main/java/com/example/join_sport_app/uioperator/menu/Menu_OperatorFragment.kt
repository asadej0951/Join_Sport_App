package com.example.join_sport_app.uioperator.menu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.join_sport_app.R
import com.example.join_sport_app.adapterall.AdapterMenu
import com.example.join_sport_app.model.DataMenu
import com.example.join_sport_app.model.ResponterMenu
import com.example.join_sport_app.model.operator.DataMenuOPT
import com.example.join_sport_app.rest.Utils
import com.example.join_sport_app.ui.login.LoginActivity
import com.example.myapplicationproject.rest.local.Preferrences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_dashboard__operator.view.*
import kotlinx.android.synthetic.main.fragment_menu.view.*
import kotlinx.android.synthetic.main.fragment_menu__operator.*
import kotlinx.android.synthetic.main.fragment_menu__operator.view.*

class Menu_OperatorFragment : Fragment() {
    lateinit var mPreferrences : Preferrences
    val data = ArrayList<ResponterMenu>()
    lateinit var mMenuAdapter:AdapterMenu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_menu__operator, container, false)
        initView(root)
        return root.rootView
    }
    private fun initView(root: View) {
        mPreferrences = Preferrences(context!!)
        root.ID_OPT_Menu.setText("ID : "+mPreferrences.getusername())
        root.tv_OPT_Menu.setText("คุณ : "+mPreferrences.getName_lname())
        Picasso.get().load(Utils.host+"/imageregister/"+mPreferrences.getImage()).into(root.img_OPT_Menu)
        for (i in DataMenuOPT.DataMenuOPT.indices){
            data.add(ResponterMenu(DataMenuOPT.DataMenuOPT[i],DataMenuOPT.Imgmenu[i]))
        }
        mMenuAdapter = AdapterMenu(context!!,data)
        root.recycler_menu_OPT.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mMenuAdapter
            mMenuAdapter.notifyDataSetChanged()
        }
    }
}