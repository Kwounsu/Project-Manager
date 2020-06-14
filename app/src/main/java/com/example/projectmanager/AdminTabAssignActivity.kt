package com.example.projectmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.projectmanager.adapter.TabLayoutAdapter
import com.google.android.material.tabs.TabLayout

class AdminTabAssignActivity : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_tab_assign)

        tabLayout = findViewById(R.id.assignTabLayout)
        viewPager = findViewById(R.id.assignViewPager)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Project"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Task"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Subtask"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = TabLayoutAdapter(
            this,
            supportFragmentManager,
            tabLayout!!.tabCount
        )
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
}