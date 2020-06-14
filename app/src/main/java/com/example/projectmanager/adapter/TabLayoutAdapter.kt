package com.example.projectmanager.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.projectmanager.AssignProjectFragment
import com.example.projectmanager.AssignSubtaskFragment
import com.example.projectmanager.AssignTaskFragment

class TabLayoutAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return AssignProjectFragment()
            }
            1 -> {
                return AssignTaskFragment()
            }
            2 -> {
                return AssignSubtaskFragment()
            }
            else -> return AssignProjectFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}