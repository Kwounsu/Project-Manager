package com.example.projectmanager

import ProjectList
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.adapter.ProjectAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_admin_project.*

class AdminProjectActivity : AppCompatActivity() {

    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_project)

        recyclerView = findViewById(R.id.recyclerView)

        disposable =
            apiService.projectList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)

        button_toCreateProject.setOnClickListener {
            val intent = Intent (this, AdminCreateProjectActivity::class.java)
            startActivity(intent)
            finish()
        }
        button_toEditProject.setOnClickListener {
            val intent = Intent (this, AdminEditProjectActivity::class.java)
            startActivity(intent)
            finish()
        }
        button_toAdminHome.setOnClickListener {
            val intent = Intent (this, AdminHomeActivity::class.java)
            startActivity(intent)
            finish()
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

    private fun handleResponse(response: ProjectList) {
        Log.d("Retrofit", "result: " + response.toString())
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@AdminProjectActivity)
            adapter = ProjectAdapter(response)
        }
    }
}