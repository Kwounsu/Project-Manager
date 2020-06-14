package com.example.projectmanager

import ViewsubtasksList
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.adapter.UserSubtaskAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_task.*

class UserSubtaskActivity : AppCompatActivity() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null
    lateinit var recyclerView: RecyclerView
    lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_subtask)

        val bundle: Bundle? = intent.extras
        userId = bundle?.get("userId") as String
        val taskId = bundle?.get("taskId") as String

        disposable =
            apiService.subtaskListByUser(userId, taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)

        recyclerView = findViewById(R.id.subtaskRecyclerView)

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

    private fun handleResponse(response: ViewsubtasksList) {
        Log.d("Retrofit", "result: " + response.toString())
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@UserSubtaskActivity)
            adapter =
                UserSubtaskAdapter(response, userId)
        }
    }
}