package com.example.projectmanager

import ProjectTask
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_task_detail.*


class UserTaskDetailActivity : AppCompatActivity() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_task_detail)

        val bundle: Bundle? = intent.extras
        val taskId = bundle?.get("taskId") as String
        val projectId = bundle.get("projectId") as String
        val userId = bundle.get("userId") as String
        Log.d("Retrofit", "Task Detail - Retrieve bundle data= userId: $userId, taskId: $taskId and projectId: $projectId")

        disposable =
            apiService.taskDetail(taskId, projectId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)

        var status: Int = 1
        val stringArrayStatus = resources.getStringArray(R.array.status)
        val spinner = findViewById<Spinner>(R.id.spinner_editTaskStatus)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, stringArrayStatus)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    status = if (position == 0) 1
                    else if (position == 1) 2
                    else 3
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    status = 1
                }
            }
        }
        button_editTaskStatus.setOnClickListener {
            val intent = Intent(this, EditTaskStatusActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("taskId", taskId)
            intent.putExtra("projectId", projectId)
            intent.putExtra("status", status.toString())
            startActivity(intent)
        }

        button_taskMembers.setOnClickListener {
            val intent = Intent(this, UserMemberTaskActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("taskId",taskId)
            intent.putExtra("projectId",projectId)
            startActivity(intent)
            finish()
        }

        button_taskPriority.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_editStatus) as PriorityTaskFragment
            fragment.updateValue(taskId, projectId, userId)
        }

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
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
        Log.e("Retrofit", t.message.toString())
    }

    private fun handleResponse(response: ProjectTask) {
        Log.d("Retrofit", "result: $response")
        testView_detailTaskId.text = response.taskid.toString()
        testView_detailProjectId.text = response.projectid.toString()
        testView_detailTaskName.text = response.taskname

        val statusNumber = response.taskstatus.toString()
        if (statusNumber == "1") testView_detailTaskStatus.text = "Start new project"
        else if (statusNumber == "2") testView_detailTaskStatus.text = "Not complete"
        else if (statusNumber == "3") testView_detailTaskStatus.text = "Complete"
        else testView_detailTaskStatus.text = "N/A"

        testView_detailTaskDesc.text = response.taskdesc
        testView_detailStartDate.text = response.startdate
        testView_detailEndDate.text = response.endstart
    }
}