package com.example.projectmanager

import TaskPriority
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_priority_task.*


class PriorityTaskFragment : Fragment() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null

    lateinit var taskId : String
    lateinit var projectId : String
    lateinit var userId : String

    fun updateValue(taskId: String, projectId: String, userId: String) {
        this.taskId = taskId
        this.projectId = projectId
        this.userId = userId

        disposable =
            apiService.taskPriority(
                taskId
                ,projectId
                ,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_priority_task, container, false)
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun handleError(t: Throwable) {
        Log.e("Retrofit", t.message.toString())
    }

    private fun handleResponse(response: TaskPriority) {
        Log.d("Retrofit", "Show task priority")
        Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show()
        textView_taskPriority.text = response.priority.toString()

        when(response.priority.toString()) {
            "1" -> textView_taskPriority.text = "Higher Priority"
            "2" -> textView_taskPriority.text = "Normal Priority"
            "3" -> textView_taskPriority.text = "Low Priority"
            else -> textView_taskPriority.text = "Has no priority"
        }
    }
}