package com.example.projectmanager

import ForgotPassword
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        button_findPassword.setOnClickListener {
            disposable =
                apiService.findPassword(editText_emailToFindPassword.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse, this::handleError)
        }

        button_goBackToLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun handleError(t: Throwable) {
        Log.e("Retrofit","handleError: "+t.message.toString())
        Toast.makeText(this, "Email is not registered", Toast.LENGTH_SHORT).show()
    }

    private fun handleResponse(response: ForgotPassword) {
        Log.d("Retrofit", "result: " + response.toString())
        Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
        textView_foundPassword.text = response.userpassword
    }
}
