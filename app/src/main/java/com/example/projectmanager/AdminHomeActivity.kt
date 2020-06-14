package com.example.projectmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_admin_employee.*
import kotlinx.android.synthetic.main.activity_admin_home.*

class AdminHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        val bundle: Bundle? = intent.extras
        val userId = bundle?.get("userId")
        Log.d("Retrofit","Admin Home: user ID = " + userId.toString())

        button_project.setOnClickListener {
            val intent = Intent (this, AdminProjectActivity::class.java)
            startActivity(intent)
        }
        button_task.setOnClickListener {
            val intent = Intent (this, AdminTaskActivity::class.java)
            startActivity(intent)
        }
        button_subtask.setOnClickListener {
            val intent = Intent (this, AdminSubtaskActivity::class.java)
            startActivity(intent)
        }
        button_employee.setOnClickListener {
            val intent = Intent (this, AdminEmployeeActivity::class.java)
            startActivity(intent)
        }
        button_userHome.setOnClickListener {
            val intent = Intent (this, UserHomeActivity::class.java)
            intent.putExtra("userId",userId.toString())
            startActivity(intent)
        }
        button_assignTabLayout.setOnClickListener {
            val intent = Intent (this, AdminTabAssignActivity::class.java)
            startActivity(intent)
        }
    }
}