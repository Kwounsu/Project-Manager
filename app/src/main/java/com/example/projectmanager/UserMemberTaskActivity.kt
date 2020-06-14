package com.example.projectmanager

import MembersList
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.adapter.UserMemberTaskAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_task.*

class UserMemberTaskActivity : AppCompatActivity() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_member_task)

        val bundle: Bundle? = intent.extras
        val taskId = bundle?.get("taskId") as String
        val projectId = bundle.get("projectId") as String
        val userId = bundle.get("userId") as String
        Log.d("Retrofit", "Task Member - Retrieve bundle data taskId: $taskId and projectId: $projectId")

        disposable =
            apiService.taskTeamMember(taskId, projectId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)

        recyclerView = findViewById(R.id.taskMemberRecyclerView)

        button_toUserHome.setOnClickListener {
            val intent = Intent (this, UserHomeActivity::class.java)
            intent.putExtra("userId",userId.toString())
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun handleError(t: Throwable) {
        Log.e("Retrofit", "handleError: " + t.message.toString())
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
    }

    private fun handleResponse(response: MembersList) {
        Log.d("Retrofit", "result: " + response.toString())
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@UserMemberTaskActivity)
            adapter =
                UserMemberTaskAdapter(response)
        }
    }
}