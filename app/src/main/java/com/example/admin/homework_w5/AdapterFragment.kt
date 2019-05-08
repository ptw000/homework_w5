package com.example.admin.homework_w5

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
class AdapterFragment(fragmentManager: FragmentManager, private val arrFragment : ArrayList<Fragment>) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return arrFragment[position]
    }

    override fun getCount(): Int {
        return COUNT;
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if(position == 0) {
            return "Now Playing"
        }
        else {
            return "Top Rate"
        }
    }
    companion object {
        const  val COUNT = 2;
    }
}
