package com.example.projectmanager

import ProjectSubtask
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
import kotlinx.android.synthetic.main.activity_user_subtask_detail.*

class UserSubtaskDetailActivity : AppCompatActivity() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_subtask_detail)

        val bundle: Bundle? = intent.extras
        val taskId = bundle?.get("taskId") as String
        val subtaskId = bundle?.get("subtaskId") as String
        val projectId = bundle?.get("projectId") as String
        val userId = bundle.get("userId") as String
        Log.d("Retrofit", "UserSubtaskDetailActivity - Retrieve bundle data taskId: $taskId, subtaskId: $subtaskId and projectId: $projectId")

        disposable =
            apiService.subtaskDetail(taskId, subtaskId, projectId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)

        var status: Int = 1
        val stringArrayStatus = resources.getStringArray(R.array.status)
        val spinner = findViewById<Spinner>(R.id.spinner_editSubtaskStatus)
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
        button_editSubtaskStatus.setOnClickListener {
            val intent = Intent(this, EditSubtaskStatusActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("taskId", taskId)
            intent.putExtra("subtaskId", subtaskId)
            intent.putExtra("projectId", projectId)
            intent.putExtra("status", status.toString())
            startActivity(intent)
        }

        button_subtaskMembers.setOnClickListener {
            val intent = Intent(this, UserMemberSubtaskActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("taskId", taskId)
            intent.putExtra("subtaskId", subtaskId)
            intent.putExtra("projectId", projectId)
            startActivity(intent)
        }

        button_subtaskPriority.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_editStatus) as PrioritySubtaskFragment
            fragment.updateValue(taskId, subtaskId, projectId, userId)
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

    private fun handleResponse(response: ProjectSubtask) {
        Log.d("Retrofit", "result: $response")
        testView_detailTaskId.text = response.taskid.toString()
        testView_detailSubtaskId.text = response.taskid.toString()
        testView_detailProjectId.text = response.projectid.toString()
        testView_detailSubtaskName.text = response.subtaskname

        val statusNumber = response.subtaskstatus.toString()
        if (statusNumber == "1") testView_detailSubtaskStatus.text = "Start new project"
        else if (statusNumber == "2") testView_detailSubtaskStatus.text = "Not complete"
        else if (statusNumber == "3") testView_detailSubtaskStatus.text = "Complete"
        else testView_detailSubtaskStatus.text = "N/A"

        testView_detailSubtaskDesc.text = response.subtaskdesc
        testView_detailStartDate.text = response.startdate
        testView_detailEndDate.text = response.endstart
    }
}