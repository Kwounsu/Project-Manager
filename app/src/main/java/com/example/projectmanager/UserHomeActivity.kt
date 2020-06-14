package com.example.projectmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_user_home.*

class UserHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_home)

        val bundle: Bundle? = intent.extras
        val userId = bundle?.get("userId")
        Log.d("Retrofit","Home: user ID = " + userId.toString())

        button_taskByUser.setOnClickListener {
            val intent = Intent (this, UserTaskActivity::class.java)
            intent.putExtra("userId",userId.toString())
            startActivity(intent)
        }
        button_subtaskByUser.setOnClickListener {
            val intent = Intent (this, UserSubtaskActivity::class.java)
            intent.putExtra("userId",userId.toString())
            intent.putExtra("taskId",editText_taskIdToSearch.text.toString())
            startActivity(intent)
        }
    }
}