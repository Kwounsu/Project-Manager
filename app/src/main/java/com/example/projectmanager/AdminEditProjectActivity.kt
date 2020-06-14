package com.example.projectmanager

import Msg
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_admin_edit_project.*

class AdminEditProjectActivity : AppCompatActivity() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_edit_project)

        val bundle: Bundle? = intent.extras
        val projectId = bundle?.get("projectId")

        Toast.makeText(this, projectId.toString(), Toast.LENGTH_SHORT).show()
        Log.e("Retrofit", projectId.toString())

        button_editProject.setOnClickListener {
            disposable =
                apiService.editProject(
                    editText_projectIdToEdit.text.toString().toInt()
                    ,editText_projectNameToEdit.text.toString()
                    ,editText_projectStatusToEdit.text.toString().toInt()
                    ,editText_projectDescToEdit.text.toString()
                    ,editText_projectStartDateToEdit.text.toString()
                    ,editText_projectEndDateToEdit.text.toString())
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
