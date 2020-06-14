package com.example.projectmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    val apiService by lazy {
        ApiService.buildService()
    }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        button_signUp.setOnClickListener {
            if (editText_yourRole.text.toString() == "admin" || editText_yourRole.text.toString() == "user") {
                disposable =
                    apiService.register(editText_firstName.text.toString()
                            ,editText_lastName.text.toString()
                            ,editText_email.text.toString()
                            ,editText_mobile.text.toString()
                            ,editText_password.text.toString()
                            ,editText_companySize.text.toString()
                            ,editText_yourRole.text.toString())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleError)
            } else {
                Toast.makeText(this, "Unacceptable role", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun handleError(t: Throwable) {
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
        Log.e("Retrofit", t.message!!)
    }

    private fun handleResponse(response: JsonObject) {
        Log.d("Retrofit", "result: $response" + response["msg"].toString())
        if (response["msg"].toString() == "[\"successfully registered\"]") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, response["msg"].toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
