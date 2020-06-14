package com.example.projectmanager

import Msg
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_assign_project.*

class AssignProjectFragment : Fragment() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Retrofit", "AssignProjectFragment")
        val view = inflater.inflate(R.layout.fragment_assign_project, container, false)

        val button:Button = view.findViewById(R.id.button_assignProject)
        button.setOnClickListener {
            disposable =
                apiService.assignProject(
                    editText_projectId.text.toString()
                    ,editText_teamMemberUserId.text.toString()
                    ,editText_taskId.text.toString()
                    ,editText_subtaskId.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse, this::handleError)
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun handleError(t: Throwable) {
        Log.e("Retrofit", t.message.toString())
    }

    private fun handleResponse(response: Msg) {
        Log.d("Retrofit", "result: $response")
        Toast.makeText(activity, response.msg.toString(), Toast.LENGTH_SHORT).show()
        val intent = Intent(activity, AdminHomeActivity::class.java)
        startActivity(intent)
    }
}