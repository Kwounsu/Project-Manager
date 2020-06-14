package com.example.projectmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        button_signIn.setOnClickListener {
            disposable =
                apiService.register(editText_email.text.toString()
                        ,editText_password.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse, this::handleError)
        }

        textView_forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun handleError(t: Throwable) {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
        Log.e("Retrofit","handleError:"+t.message.toString())
    }
    private fun handleResponse(response: JsonObject) {
        Log.d("Retrofit", "result: $response")
        val userId = response["userid"].toString().substringAfter("\"").substringBefore("\"")

        if (response["userrole"].toString() == "\"admin\"") {
            Log.d("Retrofit", "Admin Login")
            val intent = Intent(this, AdminHomeActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        } else {
            Log.d("Retrofit", "User Login")
            val intent = Intent(this, UserHomeActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }
}
