package com.example.projectmanager

import Msg
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
import kotlinx.android.synthetic.main.activity_admin_create_task.*

class AdminCreateTaskActivity : AppCompatActivity() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_create_task)

        var status: Int = 1
        val stringArrayStatus = resources.getStringArray(R.array.status)
        val spinner = findViewById<Spinner>(R.id.spinner_projectStatus)
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

        button_createTask.setOnClickListener {
            disposable =
                apiService.createTask(
                    editText_projectId.text.toString().toInt()
                    ,editText_taskName.text.toString()
                    ,status
                    ,editText_taskDesc.text.toString()
                    ,editText_taskStartDate.text.toString()
                    ,editText_taskEndDate.text.toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError)
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

    private fun handleResponse(response: Msg) {
        Log.d("Retrofit", "result: $response")
        val intent = Intent(this, AdminTaskActivity::class.java)
        startActivity(intent)
        finish()
    }
}