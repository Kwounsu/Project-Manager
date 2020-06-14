package com.example.projectmanager

import User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_team_member_detail.*
import kotlinx.android.synthetic.main.activity_team_member_detail.button_toUserHome
import kotlinx.android.synthetic.main.activity_user_task.*

class TeamMemberDetailActivity : AppCompatActivity() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_member_detail)

        val bundle: Bundle? = intent.extras
        val userId = bundle?.get("userId") as String

        disposable =
            apiService.teamMemberDetail(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)

        button_toUserHome.setOnClickListener {
            val intent = Intent (this, UserHomeActivity::class.java)
            intent.putExtra("userId",userId.toString())
            startActivity(intent)
            finish()
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

    private fun handleResponse(response: User) {
        Log.d("Retrofit", "result: $response")
        testView_userId.text = response.userid.toString()
        testView_userFirstName.text = response.userfirstname
        testView_userLastName.text = response.userlastname
        testView_userEmail.text = response.useremail
        testView_userMobile.text = response.usermobile
    }
}