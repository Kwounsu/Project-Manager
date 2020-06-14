package com.example.projectmanager

import ViewTaskList
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.adapter.UserTaskAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_task.*

class UserTaskActivity : AppCompatActivity() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null
    lateinit var recyclerView: RecyclerView
    lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_task)

        val bundle: Bundle? = intent.extras
        userId = bundle?.get("userId") as String
        Log.d("Retrofit","TaskActivity: user ID = " + userId)

        disposable =
            apiService.taskListByUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)

        recyclerView = findViewById(R.id.taskRecyclerView)

        button_toUserHome.setOnClickListener {
            val intent = Intent (this, UserHomeActivity::class.java)
            intent.putExtra("userId",userId.toString())
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun handleError(t: Throwable) {
        Log.e("Retrofit","handleError: "+t.message.toString())
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
    }

    private fun handleResponse(response: ViewTaskList) {
        Log.d("Retrofit", "result: " + response.toString())
        if (response.viewTasks != null) {
            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@UserTaskActivity)
                adapter =
                    UserTaskAdapter(response, userId)
            }
        } else {
            Toast.makeText(this,"no task assign",Toast.LENGTH_SHORT).show()
        }
    }
}