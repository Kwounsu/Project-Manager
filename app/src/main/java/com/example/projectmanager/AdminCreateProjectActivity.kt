package com.example.projectmanager

import Msg
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_admin_create_project.*

class AdminCreateProjectActivity : AppCompatActivity() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_create_project)

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

        button_createProject.setOnClickListener {
            disposable =
                apiService.createProject(
                    editText_projectName.text.toString()
                    ,status
                    ,editText_projectDesc.text.toString()
                    ,editText_projectStartDate.text.toString()
                    ,editText_projectEndDate.text.toString())
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
        val intent = Intent(this, AdminProjectActivity::class.java)
        startActivity(intent)
        finish()
    }
}
