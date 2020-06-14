package com.example.projectmanager

import MembersList
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.adapter.UserMemberSubtaskAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_task.*

class UserMemberSubtaskActivity : AppCompatActivity() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_member_subtask)

        val bundle: Bundle? = intent.extras
        val taskId = bundle?.get("taskId") as String
        val subtaskId = bundle.get("subtaskId") as String
        val projectId = bundle.get("projectId") as String
        val userId = bundle.get("userId") as String
        Log.d("Retrofit", "UserMemberSubtaskActivity - Retrieve bundle data taskId: $taskId, subtaskId: $subtaskId and projectId: $projectId")

        disposable =
            apiService.subtaskTeamMember(taskId, subtaskId, projectId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)

        recyclerView = findViewById(R.id.subtaskMemberRecyclerView)

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
        Log.d("Retrofit", "UserMemberSubtaskActivity: " + response.toString())
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@UserMemberSubtaskActivity)
            adapter =
                UserMemberSubtaskAdapter(
                    response
                )
        }
    }
}