package com.example.projectmanager

import SubtaskPriority
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_priority_subtask.*

class PrioritySubtaskFragment : Fragment() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null

    lateinit var taskId : String
    lateinit var subtaskId : String
    lateinit var projectId : String
    lateinit var userId : String

    fun updateValue(taskId: String, subtaskId: String, projectId: String, userId: String) {
        this.taskId = taskId
        this.subtaskId = subtaskId
        this.projectId = projectId
        this.userId = userId

        disposable =
            apiService.subtaskPriority(
                taskId
                ,subtaskId
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
        return inflater.inflate(R.layout.fragment_priority_subtask, container, false)
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun handleError(t: Throwable) {
        Log.e("Retrofit", t.message.toString())
    }

    private fun handleResponse(response: SubtaskPriority) {
        Log.d("Retrofit", "Show subtask priority")
        Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show()
        textView_subtaskPriority.text = response.priority.toString()

        when(response.priority.toString()) {
            "1" -> textView_subtaskPriority.text = "Higher Priority"
            "2" -> textView_subtaskPriority.text = "Normal Priority"
            "3" -> textView_subtaskPriority.text = "Low Priority"
            else -> textView_subtaskPriority.text = "Has no priority"
        }
    }
}