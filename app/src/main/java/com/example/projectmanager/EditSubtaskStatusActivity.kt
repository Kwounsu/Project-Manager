package com.example.projectmanager

import Msg
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class EditSubtaskStatusActivity : AppCompatActivity() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_subtask_status)

        val bundle: Bundle? = intent.extras
        val taskId = bundle?.get("taskId") as String
        val subtaskId = bundle?.get("subtaskId") as String
        val projectId = bundle.get("projectId") as String
        val userId = bundle.get("userId") as String
        val status = bundle.get("status") as String

        disposable = apiService.editTaskStatus(taskId, projectId, userId, status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse, this::handleError)

        val intent = Intent(this, UserTaskDetailActivity::class.java)
        intent.putExtra("userId", userId)
        intent.putExtra("taskId", taskId)
        intent.putExtra("subtaskId", subtaskId)
        intent.putExtra("projectId", projectId)

        startActivity(intent)
        finish()
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun handleError(t: Throwable) {
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
        Log.e("Retrofit", t.message.toString())
    }

    private fun handleResponse(response: Msg) {
        Log.d("Retrofit", "EditTaskStatusActivity: $response")
        Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
    }
}