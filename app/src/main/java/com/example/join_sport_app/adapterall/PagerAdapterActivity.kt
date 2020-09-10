package com.example.join_sport_app.adapterall

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.join_sport_app.ui.menu.activity.Activity_Fragment
import com.example.join_sport_app.ui.menu.activity.Join_Activity_Fragment

class PagerAdapterActivity (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    var fragments:ArrayList<Fragment> = arrayListOf(
        Join_Activity_Fragment(),Activity_Fragment()
    )
    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}